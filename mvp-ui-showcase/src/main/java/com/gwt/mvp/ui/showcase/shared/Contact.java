package com.gwt.mvp.ui.showcase.shared;

import java.io.Serializable;

/**
 * @author ibouakl
 */
public class Contact implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 5320121822436440143L;
    
    private String firstName;
    private String lastName;
    private String email;
    
    /**
     * default constructor for gwt
     */
    public Contact() {
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
}
