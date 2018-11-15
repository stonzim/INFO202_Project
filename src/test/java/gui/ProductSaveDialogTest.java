package gui;

import dao.ProductDaoInterface;
import domain.Product;
import java.math.BigDecimal;
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

public class ProductSaveDialogTest {

	private ProductDaoInterface dao;
	private DialogFixture fixture;
	private Robot robot;      

	@Before
	public void setUp() {
		robot = BasicRobot.robotWithNewAwtHierarchy();

		// Slow down the robot a little bit - default is 30 (milliseconds).
		// Do NOT make it less than 5 or you will have thread-race problems.
		robot.settings().delayBetweenEvents(50);
                
                // create a mock for the DAO
		dao = mock(ProductDaoInterface.class);
	}

	@After
	public void tearDown() {
		// clean up fixture so that it is ready for the next test
		fixture.cleanUp();
	}

	@Test
	public void testSave() {
		// create the dialog passing in the mocked DAO
		DataEntryDialog dialog = new DataEntryDialog(null, true, dao);

		// use AssertJ to control the dialog
		fixture = new DialogFixture(robot, dialog);
		fixture.show().requireVisible();

		// enter some details into the UI components
		fixture.textBox("txtId").enterText("4");
		fixture.textBox("txtName").enterText("name4");
		fixture.textBox("txtDescription").enterText("desc4");
                fixture.comboBox("cmbCategory").enterText("cat4");
		fixture.textBox("txtPrice").enterText("12.00");
		fixture.textBox("txtQuantity").enterText("32");

		// click the save button
		fixture.button("buttonSave").click();

		// create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
		ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

		// verify that the DAO.save method was called, and capture the passed student
		verify(dao).addItem(argument.capture());

		// retrieve the passed student from the captor
		Product savedProduct = argument.getValue();

		// test that the product's details were properly saved
		assertEquals("Ensure the ID was saved", new Integer(4), savedProduct.getProductID());
		assertEquals("Ensure the name was saved", "name4", savedProduct.getName());
		assertEquals("Ensure the description was saved", "desc4", savedProduct.getDescription());
                assertEquals("Ensure the category was saved", "cat4", savedProduct.getCategory());
		assertEquals("Ensure the price was saved", new BigDecimal(12), savedProduct.getPrice());
		assertEquals("Ensure the quantity was saved", new Integer(32), savedProduct.getQuantityInStock());
	}

}
