/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import domain.Customer;
import org.jooby.Err;
import org.jooby.Jooby;
import org.jooby.Status;
import dao.CustomerDAOInterface;

/**
 *
 * @author dowwi431
 */
public class CustomerModule extends Jooby {
    
    public CustomerModule(CustomerDAOInterface cdao) {
        
            get("/api/customers/:username", (req) -> {
                String username = req.param("username").value();
                if (cdao.getCustomer(username) != null) {
                    return cdao.getCustomer(username); 
                } else {
                    throw new Err(Status.NOT_FOUND);
                }
            });

            post("/api/register", (req, rsp) -> {
            Customer customer = req.body().to(Customer.class);
            cdao.save(customer);
            rsp.status(Status.CREATED);
            });
    }
    
}
