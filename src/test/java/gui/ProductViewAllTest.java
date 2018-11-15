/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ProductDaoInterface;
import domain.Product;
import gui.helpers.SimpleListModel;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductViewAllTest {

	private ProductDaoInterface dao;
	private DialogFixture fixture;
	private Robot robot;
        private Product prodOne;
        private Product prodTwo;
        private Product prodThree;

	@Before
	public void setUp() {
		robot = BasicRobot.robotWithNewAwtHierarchy();

		// Slow down the robot a little bit - default is 30 (milliseconds).
		// Do NOT make it less than 5 or you will have thread-race problems.
		robot.settings().delayBetweenEvents(50);
                
                // create a mock for the DAO
		dao = mock(ProductDaoInterface.class);

		// add products and details 
                this.prodOne = new Product(1, "name1", "desc1", "cat1",
                    new BigDecimal("11.000"), new Integer(22));
                this.prodTwo = new Product(2, "name2", "desc2", "cat2",
                    new BigDecimal("33.00"), new Integer(44));
                this.prodThree = new Product(3, "name3", "desc3", "cat3",
                    new BigDecimal("55.00"), new Integer(66));
		
                Collection<Product> products = new HashSet<>();
		products.add(prodOne);
                products.add(prodTwo);
		products.add(prodThree);

		// stub the getMajors method to return the test majors
		when(dao.getList()).thenReturn(products);
	}

	@After
	public void tearDown() {
		// clean up fixture so that it is ready for the next test
		fixture.cleanUp();
	}

	@Test
	public void testEdit() {

		// create dialog 
		ShowProducts dialog = new ShowProducts(null, true, dao);

		// use AssertJ to control the dialog
		fixture = new DialogFixture(robot, dialog);

		// show the dialog on the screen, and ensure it is visible
		fixture.show().requireVisible();
                
                verify(dao).getList();
                
                // get the model
                SimpleListModel model = (SimpleListModel) fixture.list("jList").target().getModel();

                // check the contents
                assertTrue("list contains the prodOne", model.contains(prodOne));
                assertTrue("list contains the prodTwo", model.contains(prodTwo));
                assertTrue("list contains the prodThree", model.contains(prodThree));
                assertEquals("list contains the correct number of products", 3, model.getSize());            
	}
}
