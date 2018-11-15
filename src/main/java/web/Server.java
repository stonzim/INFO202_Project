/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.ProductDAODatabase;
import dao.CustomerDAODatabase;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;
import dao.CustomerDAOInterface;
import dao.ProductDaoInterface;
import dao.SaleDAODatabase;
import dao.SaleDAOInterface;

/**
 *
 * @author dowwi431
 */
public class Server extends Jooby {
    ProductDaoInterface dao = new ProductDAODatabase();
    CustomerDAOInterface cdao = new CustomerDAODatabase();
    SaleDAOInterface sdao = new SaleDAODatabase();
    
    public Server() {
        port(8075);
        use(new Gzon());
        use(new ProductModule(dao));
        use(new CategoryModule(dao));
        use(new CustomerModule(cdao));
        use(new SaleModule(sdao));
        use(new AssetModule());
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("\nStarting Server.");
        Server server = new Server();
        
        CompletableFuture.runAsync(() -> {
        server.start();
        });
        
        server.onStarted(() -> {
            
        System.out.println("\nPress Enter to stop the server.");
        });
        
        // wait for user to hit the Enter key
        System.in.read();
        System.exit(0);
    }
}
