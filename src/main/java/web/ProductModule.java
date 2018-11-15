/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import org.jooby.Jooby;
import dao.ProductDaoInterface;

/**
 *
 * @author dowwi431
 */
public class ProductModule extends Jooby {
    
    public ProductModule(ProductDaoInterface dao) {
        get("/api/products", () -> dao.getList());
        get("/api/products/:id", (req) -> {
            Integer id = req.param("id").intValue();
            return dao.findProd(id);
        });
    }
}
