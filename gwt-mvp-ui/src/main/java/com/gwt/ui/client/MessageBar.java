package com.gwt.ui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.gwt.ui.client.supertable.HTMLHelper;

public class MessageBar extends Composite {
    
    public static final int NONE = 0;
    public static final int INFORMATION = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;
    
    private int severity = INFORMATION;
    
    private FlowPanel panel;
    
    private HTML message;
    
    private String text = "";
    
    /**
     * A Constructor for this class
     */
    public MessageBar() {
        
        super();
        panel = new FlowPanel();
        initWidget(panel);
        init();
    }
    
    private void init() {
        setStyleName("gwtcomp-MessageBar");
        setWidth("100%");
        message = new HTML("&nbsp;");
        message.setWidth("100%");
        panel.add(message);
        panel.add(new HTML(HTMLHelper.hr("#AAAAAA")));
    }
    
    /**
     * Display a Message
     * 
     * @param text
     */
    public void setMessage(String text) {
        setMessage(text, INFORMATION);
        
    }
    
    /**
     * Prints a message with a given severity.
     * 
     * @param text message to print.
     * @param severity of the message
     */
    public void setMessage(String text, int severity) {
        this.text = text;
        this.severity = severity;
        
        String color = null;
        String icon = null;
        
        switch (severity) {
            case NONE:
                clearMessage();
                return;
                
            case INFORMATION:
                icon = GWT.getModuleBaseURL() + "gwtcomp-icons/info.png";
                color = "blue";
                break;
            
            case WARNING:
                icon = GWT.getModuleBaseURL() + "gwtcomp-icons/bell.png";
                color = "#FF9900";
                
                break;
            
            case ERROR:
                icon = GWT.getModuleBaseURL() + "gwtcomp-icons/no.png";
                color = "red";
                
                break;
        }
        
        message.setHTML("<img border='0' align='top' src='" + icon + "'/>" + "&nbsp;<span style=\"color: " + color + ";\">" + text + "</span>");
    }
    
    /**
     * Clears the message
     */
    public void clearMessage() {
        message.setHTML("&nbsp;");
        text = null;
        severity = INFORMATION;
    }
    
    public int getSeverity() {
        return severity;
    }
    
    public void setSeverity(int severity) {
        this.severity = severity;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
}
