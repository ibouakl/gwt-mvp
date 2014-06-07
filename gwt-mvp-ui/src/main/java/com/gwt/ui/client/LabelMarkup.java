package com.gwt.ui.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * use this class to create a simple label element. the result should look like this 
 * <label> text </label>
 * 
 * @author ibouakl
 */
public class LabelMarkup extends ComplexPanel {
    
    public LabelMarkup() {
        setElement(DOM.createElement("label"));
    }
    
    /**
     * create the label element and initialize his text
     * @param label
     */
    public LabelMarkup(String label) {
        setElement(DOM.createElement("label"));
        setText(label);
    }
    
    public void add(Widget w) {
        super.add(w, getElement());
    }
    
    public void insert(Widget w, int beforeIndex) {
        super.insert(w, getElement(), beforeIndex, true);
    }
    
    public String getText() {
        return DOM.getInnerText(getElement());
    }
    
    public void setText(String text) {
        DOM.setInnerText(getElement(), (text == null) ? "" : text);
    }
    
}
