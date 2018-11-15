/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;

/**
 *
 * @author dowwi431
 */
public class SaleItem {
    private Integer quantityPurchased;
    private BigDecimal salePrice;
    private Product product;
    private Sale sale;

    public SaleItem(Integer quantityPurchased, BigDecimal salePrice, Product product, Sale sale) {
        this.quantityPurchased = quantityPurchased;
        this.salePrice = salePrice;
        this.product = product;
        this.sale = sale;
    }

    
    
    public Integer getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(Integer quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
    
    
    
    public BigDecimal getItemTotal() {
        return salePrice.multiply(new BigDecimal(quantityPurchased));
    }
}
