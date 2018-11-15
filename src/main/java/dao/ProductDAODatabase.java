/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.*;

/**
 *
 * @author dowwi431
 */
public class ProductDAODatabase implements ProductDaoInterface {

    String URL = "jdbc:h2:tcp://localhost:9035/project;IFEXISTS=TRUE";

    public ProductDAODatabase() {
    }

    public ProductDAODatabase(String URL) {
        this.URL = URL;
    }

    @Override
    public void addItem(Product p) {

        String sql = "merge into product (product_ID, product_name, description, category, price, quantity_In_Stock) values (?,?,?,?,?,?)";

        try (
                // get connection to database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            // copy the data from the product domain object into the SQL parameters
            stmt.setInt(1, p.getProductID());
            stmt.setString(2, p.getName());
            stmt.setString(3, p.getDescription());
            stmt.setString(4, p.getCategory());
            stmt.setBigDecimal(5, p.getPrice());
            stmt.setInt(6, p.getQuantityInStock());

            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delItem(Product p) {
        String sql = "delete from Product where product_ID = ?";

        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {

            // execute the query
            stmt.setInt(1, p.getProductID());
            stmt.executeUpdate();

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection filter(String Category) {
         String sql = "select * from product where category = ? order by product_id";

        try (
                // get a connection to the database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
                // execute the query
                stmt.setString(1, Category);
                ResultSet rs = stmt.executeQuery();

                // Using a List to preserve the order in which the data was returned from the query.
                Collection<Product> products = new ArrayList<>();

                // iterate through the query results
                while (rs.next()) {

                    // get the data out of the query
                    Integer id = rs.getInt("product_ID");
                    String name = rs.getString("product_name");
                    String description = rs.getString("description");
                    String category = rs.getString("category");
                    BigDecimal price = rs.getBigDecimal("price");
                    Integer quantityInStock = rs.getInt("quantity_In_Stock");

                    // use the data to create a student object
                    Product p = new Product(id, name, description, category, price, quantityInStock);

                    // and put it in the collection
                    products.add(p);
            }

            return products;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Product findProd(Integer id) {
        String sql = "select * from product where product_ID = ?";

        try (
            // get connection to database
            Connection connection = JdbcConnection.getConnection(URL);
            // create the statement
            PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet p = stmt.executeQuery();
            if (p.first()) {
                // get the data out of the query
               Integer ID = p.getInt("product_ID");
               String name = p.getString("product_name");
               String description = p.getString("description");
               String category = p.getString("category");
               BigDecimal price = p.getBigDecimal("price");
               Integer quantityInStock = p.getInt("quantity_In_Stock");

               // use the data to create a student object
               Product prod = new Product(id, name, description, category, price, quantityInStock);
               return prod;
            } else {
                return null;
            }
        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection getCat() {
        String sql = "select distinct category from Product order by category";

        try (
                // get connection to database
                Connection connection = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = connection.prepareStatement(sql);) {

            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            Collection<String> categories = new ArrayList<>();

            // query only returns a single result, so use 'if' instead of 'while'
            while (rs.next()) {
                String category = rs.getString("category");
                categories.add(category);

            }
            return categories;

        } catch (SQLException ex) {  // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Collection getList() {
        String sql = "select * from product order by product_id";

        try (
                // get a connection to the database
                Connection dbCon = JdbcConnection.getConnection(URL);
                // create the statement
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {
            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            Collection<Product> products = new ArrayList<>();

            // iterate through the query results
            while (rs.next()) {

                // get the data out of the query
                Integer id = rs.getInt("product_ID");
                String name = rs.getString("product_name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal price = rs.getBigDecimal("price");
                Integer quantityInStock = rs.getInt("quantity_In_Stock");

                // use the data to create a student object
                Product p = new Product(id, name, description, category, price, quantityInStock);

                // and put it in the collection
                products.add(p);
            }

            return products;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
