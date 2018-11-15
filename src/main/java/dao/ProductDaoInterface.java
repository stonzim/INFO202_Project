/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.util.Collection;

/**
 *
 * @author dowwi431
 */
public interface ProductDaoInterface {

    void addItem(Product p);

    void delItem(Product p);

    Collection filter(String category);

    Product findProd(Integer id);

    Collection getCat();

    Collection getList();
    
}
