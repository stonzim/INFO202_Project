package gui;

import dao.ProductDaoInterface;
import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductEditDialogTest {

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
		// a product to edit
		Product prod = new Product(4, "name4", "desc4", "cat4",
                    new BigDecimal("77.00"), new Integer(88));

		// create dialog passing in student and mocked DAO
		DataEntryDialog dialog = new DataEntryDialog(null, true, dao, prod);

		// use AssertJ to control the dialog
		fixture = new DialogFixture(robot, dialog);

		// show the dialog on the screen, and ensure it is visible
		fixture.show().requireVisible();

		// verify that the UI componenents contains the student's details
                fixture.textBox("txtId").requireText("4");
		fixture.textBox("txtName").requireText("name4");
		fixture.textBox("txtDescription").requireText("desc4");
                fixture.comboBox("cmbCategory").requireSelection("cat4");
		fixture.textBox("txtPrice").requireText("77.00");
		fixture.textBox("txtQuantity").requireText("88");
		
		// edit some details
		fixture.textBox("txtName").selectAll().deleteText().enterText("newName");
		fixture.textBox("txtDescription").selectAll().deleteText().enterText("newDesc");
                
		// click the save button
		fixture.button("buttonSave").click();

		// create a Mockito argument captor to use to retrieve the passed product from the mocked DAO
		ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

		// verify that the DAO.save method was called, and capture the passed product
		verify(dao).addItem(argument.capture());

		// retrieve the passed product from the captor
		Product editedProduct = argument.getValue();

                // check what wasn't changed is the same
                assertEquals("Ensure the id was not changed", "4", editedProduct.getProductID().toString());
                assertEquals("Ensure the category was not changed", "cat4", editedProduct.getCategory());
                assertEquals("Ensure the price was not changed", "77.00", editedProduct.getPrice().toString());
                assertEquals("Ensure the quantity was not changed", "88", editedProduct.getQuantityInStock().toString());


		// check that the changes were saved
		assertEquals("Ensure the name was changed", "newName", editedProduct.getName());
		assertEquals("Ensure the major was changed", "newDesc", editedProduct.getDescription());
	}
}

