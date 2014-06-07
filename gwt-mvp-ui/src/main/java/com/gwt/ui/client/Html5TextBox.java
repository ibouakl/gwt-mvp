package com.gwt.ui.client;

import com.google.gwt.user.client.ui.TextBox;

/**
 * 
 * @author ibouakl
 *
 */
public class Html5TextBox extends TextBox {
    
    private String placeHolder;
    
    public Html5TextBox() {
        super();
    }
    
    public Html5TextBox(String placeHolder) {
        this.placeHolder = placeHolder;
        if (placeHolder != null && !placeHolder.trim().equals("")) {
            this.getElement().setAttribute("placeholder", placeHolder);
        }
        
    }
    
    /**
     * @return the placeHolder
     */
    public String getPlaceHolder() {
        return placeHolder;
    }
    
    /**
     * @param placeHolder the placeHolder to set
     */
    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
        if (placeHolder != null && !placeHolder.trim().equals("")) {
            this.getElement().setAttribute("placeholder", placeHolder);
        }
    }
    
}
