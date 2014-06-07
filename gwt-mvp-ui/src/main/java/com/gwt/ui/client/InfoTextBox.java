package com.gwt.ui.client;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * @author ibouakl
 */
public class InfoTextBox extends FlowPanel {
    
    private InfoHelp infoHelp;
    private TextBox input;
    
    public InfoTextBox(String helpHtml) {
        super();
        addStyleName("infoTextBox");
        infoHelp = new InfoHelp(helpHtml);
        add(infoHelp);
        input = new TextBox();
        add(input);
        
    }
    
    public void setValue(String value) {
        input.setValue(value);
    }
    
    public String getValue() {
        return input.getValue();
    }
    
    /**
     * @return the input
     */
    public TextBox getInput() {
        return input;
    }
    
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> valueChangeHandler) {
        return input.addValueChangeHandler(valueChangeHandler);
    }
    
    public void setReadOnly(boolean readOnly) {
        input.setReadOnly(readOnly);
    }
    
    }
