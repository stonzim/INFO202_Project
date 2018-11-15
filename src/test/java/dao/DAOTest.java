/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dowwi431
 */
public class DAOTest {
    private Product prodOne;
    private Product prodTwo;
    private Product prodThree;
    private Product prodFour;
    ProductDaoInterface dao =
        new ProductDAODatabase("jdbc:h2:tcp://localhost:9035/project-testing");
    
    public DAOTest() {
    }
    
    @Before
    public void setUp() {
        this.prodOne = new Product(1, "name1", "desc1", "cat1",
            new BigDecimal("11.000"), new Integer(22));
        this.prodTwo = new Product(2, "name2", "desc2", "cat2",
            new BigDecimal("33.000"), new Integer(44));
        this.prodThree = new Product(3, "name3", "desc3", "cat3",
            new BigDecimal("55.00"), new Integer(66));
         this.prodFour = new Product(4, "name4", "desc4", "cat2",
            new BigDecimal("77.00"), new Integer(88));
        // save the products
        dao.addItem(prodOne);
        dao.addItem(prodTwo);
        dao.addItem(prodFour);
        
        // Note: Intentionally not saving prodThree
    }
    
    @Test
    public void testDaoSave() {
        // save the product using DAO
        dao.addItem(prodThree);
        // retrieve the same product via DAO
        Product retrieved = dao.findProd(3);
        // ensure that the product we saved is the one we got back
        assertEquals("Retrieved product should be the same",
        prodThree, retrieved);
    }
    
    @Test
    public void testDaoSaveEdit() {
        // rename prodOne from name1 to newName
        prodOne.setName("newName");
        // save again
        dao.addItem(prodOne);
        // retrieve the same product via DAO
        Product retrieved = dao.findProd(1);
        // ensure that the product we saved is the one we got back
        assertEquals("Retrieved product should have the new name",
        retrieved.getName(), "newName");
    }
    
    @Test
    public void testGetCat() {
        Collection<String> categories = dao.getCat();
       // ensure the result includes the two saved products
        assertTrue("prodOne should exist", categories.contains("cat1"));
        assertTrue("prodTwo should exist", categories.contains("cat2"));
        // ensure the result ONLY includes 2 items, prodFour has same
        // category as prodTwo, so shouldn't be in the list
        assertEquals("Only 2 product in result", 2, categories.size());
    }
    
    @Test
    public void testDaoDelete() {
        // delete the product via the DAO
        dao.delItem(prodOne);
        // try to retrieve the deleted product
        Product retrieved = dao.findProd(1);
        // ensure that the product was not retrieved (should be null)
        assertNull("Product should no longer exist", retrieved);
    }
    
    @Test
    public void testDaoGetAll() {
        Collection<Product> products = dao.getList();
        // ensure the result includes the two saved products
        assertTrue("prodOne should exist", products.contains(prodOne));
        assertTrue("prodTwo should exist", products.contains(prodTwo));
        // ensure the result ONLY includes the three saved products
        assertEquals("Only 3 products in result", 3, products.size());
        // find prodOne - result is not a map, so we have to scan for it
        for (Product p : products) {
            if (p.equals(prodOne)) {
                // ensure that all of the details were correctly retrieved
                assertEquals(prodOne.getProductID(), p.getProductID());
                assertEquals(prodOne.getName(), p.getName());
                assertEquals(prodOne.getDescription(), p.getDescription());
                assertEquals(prodOne.getCategory(), p.getCategory());
                assertEquals(prodOne.getPrice(), p.getPrice());
                assertEquals(prodOne.getQuantityInStock(), p.getQuantityInStock());
            }
        }
    }
    
    @Test
    public void filter() {
        Collection<Product> products = dao.filter("cat2");
        // ensure the result includes the two products of category cat2 
        assertTrue("prodTwo should exist", products.contains(prodTwo));
        assertTrue("prodFour should exist", products.contains(prodFour));
        // ensure the result ONLY includes these two products
        assertEquals("Only 2 products in result", 2, products.size());
        // find prodTwo - result is not a map, so we have to scan for it
        for (Product p : products) {
            if (p.equals(prodTwo)) {
                // ensure that all of the details were correctly retrieved
                assertEquals(prodTwo.getProductID(), p.getProductID());
                assertEquals(prodTwo.getName(), p.getName());
                assertEquals(prodTwo.getDescription(), p.getDescription());
                assertEquals(prodTwo.getCategory(), p.getCategory());
                assertEquals(prodTwo.getPrice(), p.getPrice());
                assertEquals(prodTwo.getQuantityInStock(), p.getQuantityInStock());
            }
        }
    }
    
    @Test
    public void testDaoFindById() {
        // get prodOne using findById method
        Product retrieved = dao.findProd(1);
        // assert that you got back prodOne, and not another product
        assertEquals("Retrieved product should be the same as prodOne",
        prodOne, retrieved);
        // assert that prodOne's details were properly retrieved
        assertEquals(prodOne.getProductID(), retrieved.getProductID());
        assertEquals(prodOne.getName(), retrieved.getName());
        assertEquals(prodOne.getDescription(), retrieved.getDescription());
        assertEquals(prodOne.getCategory(), retrieved.getCategory());
        assertEquals(prodOne.getPrice(), retrieved.getPrice());
        assertEquals(prodOne.getQuantityInStock(), retrieved.getQuantityInStock());
        // call getById using a non-existent ID
        Product empty = dao.findProd(101010);
        // assert that the result is null
        assertNull("Product should not exist", empty);
    }
    
    @After
    public void tearDown() {
        dao.delItem(prodOne);
        dao.delItem(prodTwo);
        dao.delItem(prodThree);
        dao.delItem(prodFour);
    }
    
}
