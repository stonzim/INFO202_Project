/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author dowwi431
 */
public class Customer {
    private Integer personID;
    private String username;
    private String firstName;
    private String surname;
    private String password;
    private String emailAddress;
    private String shippingAddress;
    private String creditCardDetails;

    public Customer() {
        
    }
    
    public Customer(Integer personID, String username, String firstName, String surname, String password, String emailAddress, String shippingAddress, String creditCardDetails) {
        this.personID = personID;
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.emailAddress = emailAddress;
        this.shippingAddress = shippingAddress;
        this.creditCardDetails = creditCardDetails;
    }

    

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public String getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setCreditCardDetails(String creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }

    

    

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    
    
    
}
