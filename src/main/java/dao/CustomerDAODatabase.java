/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author dowwi431
 */
public class CustomerDAODatabase implements CustomerDAOInterface {

    String URL = "jdbc:h2:tcp://localhost:9035/project;IFEXISTS=TRUE";

    public CustomerDAODatabase() {
    }

    public CustomerDAODatabase(String URL) {
        this.URL = URL;
    }
    
    @Override
    public void save(Customer customer) {
        
        String sql = "insert into customer (Username, First_Name, Surname, Password, Email_Address, Shipping_Address, Credit_Card_Details) values (?,?,?,?,?,?,?)";

        try (// get connection to database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            
            // copy the data from the customer domain object into the SQL parameters
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getSurname());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getEmailAddress());
            stmt.setString(6, customer.getShippingAddress());
            stmt.setString(7, customer.getCreditCardDetails());

            stmt.executeUpdate();  // execute the statement
                 
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public Customer getCustomer(String username) {
        
        String sql = "select * from Customer where Username = ?";
                
        try (// get connection to database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            
            stmt.setString(1, username);
            ResultSet customer = stmt.executeQuery();
            if (customer.first()) {
                // get the data out of the query
                Integer id = customer.getInt("Person_ID");
                String user = customer.getString("Username");
                String fname = customer.getString("First_Name");
                String lname = customer.getString("Surname");
                String pass = customer.getString("Password");
                String email = customer.getString("Email_Address");
                String shipping = customer.getString("Shipping_Address");
                String credit = customer.getString("Credit_Card_Details");

                // use the data to create a customer object
                Customer cust = new Customer(id, user, fname, lname, pass, email, shipping, credit);
               
               return cust;
            } else {
                return null;
            }
                 
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public Boolean validateCredentials(String username, String password) {
        
        String sql = "select * from Customer where Username = ? and Password = ?";

        try (// get connection to database
            Connection dbCon = JdbcConnection.getConnection(URL);
            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            stmt.setString(1, username);
            stmt.setString(2, password);
                       
            ResultSet customer = stmt.executeQuery();
            
            if (customer.next()) {
                return true;
            } else {
                return false;
            }
            
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }         
}
