/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Product;
import java.util.*;
/**
 *
 * @author dowwi431
 */
public class ProductDAOCollection implements ProductDaoInterface {
    int i;
//    private static ArrayList<Product> list = new ArrayList<>();
//    private static Collection<Product> list = new HashSet<>();
//    private static Collection<String> cats = new HashSet<>();
    private static Map<Integer, Product> IDs = new HashMap<>();
    private static Multimap<String, Product> catFilt = HashMultimap.create();
    
    @Override
    public void addItem(Product p) {
//        list.add(p);
        IDs.put(p.getProductID(), p);
        catFilt.put(p.getCategory(), p);
    } 
    
    @Override
    public void delItem(Product p) {
//        list.remove(p);
        IDs.remove(p.getProductID());
        catFilt.remove(p.getCategory(), p);
    }
    
//    @Override
//    public void addCat(String s) {
//        cats.add(s);
//    } 
    
    @Override
    public Collection getList() {
    return IDs.values();
    }  
    
    @Override
    public Collection getCat() {
    return catFilt.keySet();
    } 
    
    @Override
    public Product findProd(Integer id) {
        if (IDs.get(id) != null) {
        return IDs.get(id);
        } else {
            return null;
        }
    } 
    
    @Override
    public Collection filter(String category) {
        if (catFilt.get(category) != null) {
        Collection<Product> prods = catFilt.get(category);
        return prods;
        } else {
            return null;
        }
    }   
}
