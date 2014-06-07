package com.gwt.ui.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;

public class HtmlTextBox extends HorizontalFlowPanel {
    private final TextBox input;
    
    /**
     * 
     */
    public HtmlTextBox(String labelText) {
        super();
        LabelMarkup label = new LabelMarkup(labelText);
        this.input = new TextBox();
        this.add(label);
        this.add(this.input);
    }
      
    public void setText(String htmlText) {
        this.input.setValue(new HTML(htmlText).getText());
    }
    
}
