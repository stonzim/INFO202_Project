/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.SaleDAOInterface;
import domain.Sale;
import domain.SaleItem;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author dowwi431
 */
public class SaleModule extends Jooby {

    public SaleModule(SaleDAOInterface sdao) {
        StringBuilder message = new StringBuilder();
        post("/api/sales", (req, rsp) -> {
            Sale sale = req.body().to(Sale.class);
            sale.toString();
            sdao.save(sale);
            rsp.status(Status.CREATED);
            
            CompletableFuture.runAsync(() -> {
                Email email = new SimpleEmail();
                email.setHostName("localhost");
                email.setSmtpPort(2525);

                try {
                    email.setFrom("winnie@gmail.com");
                    email.setSubject("TestMail");
                    message.append("Thank you for your order.\nYou got:\n");
                            for(SaleItem item : sale.getItems()){
                               message.append(item.getProduct().getName()+" * "+item.getQuantityPurchased()+", which comes to $" + item.getItemTotal()+".\n");
                            }
                            message.append("Your order came to: $" + sale.getTotal()+ ".\nThank you for shopping with us.");
                    email.setMsg(message.toString());
                    email.addTo("win@nie.com");
                    email.send();
                } catch (EmailException ex) {
                    Logger.getLogger(SaleModule.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        });
    }
}
