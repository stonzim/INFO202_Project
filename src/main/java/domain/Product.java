/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.util.Objects;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

/**
 *
 * @author dowwi431
 */
public class Product {
    @NotNull(message = "Product ID must be provided")
    @NotBlank(message = "Product ID must be provided")
    private Integer productID;
    @NotNull(message = "Name must be provided")
    @NotBlank(message = "Name must be provided")
    @Length(min = 2, message = "Name must contain at least two characters")
    private String name;
    private String description;
    @NotNull(message = "Category must be provided")
    @NotBlank(message = "Category must be provided")
    private String category;
    @NotNull(message = "Price must be provided")
    @NotNegative(message = "Price must be zero or greater")
    @Max(value = 10000, inclusive = false, message = "Please enter a price less than $10,000")
    private BigDecimal price;
    @NotNull(message = "Quantity must be provided")
    @NotNegative(message = "Quantity must be zero or greater")
    private Integer quantityInStock;
    
    public Product() {
     Integer productID;
     String name;
     String description;
     String category;
     BigDecimal price;
     Integer quantityInStock;
    }

    public Product(Integer productID, String name, String description, String category, BigDecimal price, Integer quantityInStock) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }
    
    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    
 

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

   
    
    

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }



    @Override
    public String toString() {
        return "Product{" + "productID=" + productID + ", name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.productID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productID, other.productID)) {
            return false;
        }
        return true;
    }

    
    
}
