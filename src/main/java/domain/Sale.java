/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author dowwi431
 */
public class Sale {
    private Integer saleID;
    private Date date;
    private String status;
    private Customer customer;
    private final Collection<SaleItem> items = new ArrayList<>();

    public Sale(Integer saleID, Date date, String status, Customer customer) {
        this.saleID = saleID;
        this.date = date;
        this.status = status;
        this.customer = customer;
    }

    public Integer getSaleID() {
        return saleID;
    }

    public void setSaleID(Integer saleID) {
        this.saleID = saleID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Collection<SaleItem> getItems() {
        return items;
    }
    
    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        for (SaleItem item: items) {
            total = total.add(item.getItemTotal());
        }
        return total;
    }
    
    public void addItem(SaleItem i) {
        items.add(i);
    } 

    @Override
    public String toString() {
        return "Sale{" + "saleID=" + saleID + ", date=" + date + ", status=" + status + ", customer=" + customer + ", items=" + items + '}';
    }
    
    
}
