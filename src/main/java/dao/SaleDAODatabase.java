/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
 
import domain.Customer;
import domain.Product;
import domain.Sale;
import domain.SaleItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dowwi431
 */
public class SaleDAODatabase implements SaleDAOInterface {
    private static final String url = "jdbc:h2:tcp://localhost:9035/project;IFEXISTS=TRUE";
 
	@Override
	public void save(Sale sale) {
            
            sale.setStatus("new");
 
		Connection con = JdbcConnection.getConnection(url);
		try {
			try (
					PreparedStatement insertSaleStmt = con.prepareStatement(
                                                "insert into Sale (Sale_Date, Person_ID, Status) values (?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
 
					PreparedStatement insertSaleItemStmt = con.prepareStatement(
                                                "insert into SaleItem (Sale_ID, Quantity_Purchased, Price, Product_ID) values (?,?,?,?)");

					PreparedStatement updateProductStmt = con.prepareStatement(
                                                "update product set Quantity_In_Stock = Quantity_In_Stock - ? where Product_ID = ?");
					) {
 
				// Since saving and sale involves multiple statements across
				// multiple tables we need to control the transaction ourselves
				// to ensure our DB remains consistent.
				//
				// Turn off auto-commit which effectively starts a new transaction.
				con.setAutoCommit(false);
 
				Customer customer = sale.getCustomer();
 
				// #### save the sale ### //
 
				// add a date to the sale if one doesn't already exist
				if(sale.getDate() == null) {
					sale.setDate(new Date());
				}
 
				// convert sale date into to java.sql.Timestamp
				Date date = sale.getDate();
				Timestamp timestamp = new Timestamp(date.getTime());
 
                                insertSaleStmt.setTimestamp(1, timestamp);
                                insertSaleStmt.setInt(2, sale.getCustomer().getPersonID());
                                insertSaleStmt.setString(3, sale.getStatus());

                                insertSaleStmt.executeUpdate();
 
				// get the auto-generated sale ID from the database
				ResultSet rs = insertSaleStmt.getGeneratedKeys();
 
				Integer saleId = null;
 
				if (rs.next()) {
					saleId = rs.getInt(1);
				} else {
					throw new DAOException("Problem getting generated Sale ID");
				}
 
 
				Collection<SaleItem> items = sale.getItems();
 
				for (SaleItem item : items) {
 
					Product product = item.getProduct();
                                        insertSaleItemStmt.setInt(1, saleId);
                                        insertSaleItemStmt.setInt(2, item.getQuantityPurchased());
                                        insertSaleItemStmt.setBigDecimal(3, product.getPrice());
                                        insertSaleItemStmt.setInt(4, product.getProductID());
                                        
                                        insertSaleItemStmt.executeUpdate();

                                        updateProductStmt.setInt(1, item.getQuantityPurchased());
                                        updateProductStmt.setInt(2, product.getProductID());
                                        
                                        updateProductStmt.executeUpdate();
				}
 
				// commit the transaction
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
 
			Logger.getLogger(SaleDAODatabase.class.getName()).log(Level.SEVERE, null, ex);
 
			try {
				// something went wrong so rollback
				con.rollback();
 
				// turn auto-commit back on
				con.setAutoCommit(true);
 
				// and throw an exception to tell the user something bad happened
				throw new DAOException(ex.getMessage(), ex);
			} catch (SQLException ex1) {
				throw new DAOException(ex1.getMessage(), ex1);
			}
 
		} finally {
			try {
				con.close();
			} catch (SQLException ex) {
				Logger.getLogger(SaleDAODatabase.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
