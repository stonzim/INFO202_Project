/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import static javax.swing.UIManager.get;
import org.jooby.Jooby;
import dao.ProductDaoInterface;

/**
 *
 * @author dowwi431
 */
public class CategoryModule extends Jooby {
    public CategoryModule(ProductDaoInterface dao) {
        get("/api/categories", () -> dao.getCat());
        get("/api/categories/:cat", (req) -> {
        String cat = req.param("cat").value();
        return dao.filter(cat);
        });
    }
}
