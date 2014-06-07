package com.gwt.ui.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Widget;

public class GroupBoxPanel extends ComplexPanel implements InsertPanel {
    private Element legend;
    
    public GroupBoxPanel() {
        Element fieldset = DOM.createFieldSet();
        this.legend = DOM.createLegend();
        DOM.appendChild(fieldset, legend);
        setElement(fieldset);
    }
    
    @Override
    public void add(Widget w) {
        add(w, getElement());
    }
    
    public void insert(Widget w, int beforeIndex) {
        insert(w, getElement(), beforeIndex, true);
    }
    
    public String getText() {
        return DOM.getInnerText(this.legend);
    }
    
    public void setText(String text) {
        DOM.setInnerText(this.legend, text);
    }
    
    public void setHtml(String html) {
        DOM.setInnerHTML(this.legend, html);
    }
    
    public String getHtml() {
        return DOM.getInnerHTML(this.legend);
    }
    
}
