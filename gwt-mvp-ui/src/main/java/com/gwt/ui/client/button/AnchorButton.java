package com.gwt.ui.client.button;

import com.google.gwt.user.client.ui.Anchor;
/**
 * A widget that represents a  <a> element.
 * @author ibouakl
 *
 */
public class AnchorButton extends Anchor {

    public AnchorButton(String label){
        
        this.setHTML("<span >"+label+"</span>");
        
    }
    
    
    public void setText(String text){
        this.setHTML("<span >"+text+"</span>");
    }
    
}
