package com.gwt.ui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;


/**
 * 
 * @author ibouakl
 *
 */
public class WindowTitleBar extends Composite {
    private FlexTable table;
    private String text;
    private Image image;
    SimpleWindow window;
    private int level = 1;
    
    /**
     * A constructor for this class.
     */
    
    public WindowTitleBar(String text, int level, SimpleWindow window) {
        super();
        this.text = text;
        this.level = level;
        this.window = window;
        table = new FlexTable();
        initWidget(table);
        
        init();
    }
    
    private void init() {   
        setStyleName("TitleBar");
        table.setBorderWidth(0);
        table.setCellPadding(0);
        table.setCellSpacing(0);
        
        if (text != null) {
            setText(text);
        }  
        table.getRowFormatter().setVerticalAlign(0, HasVerticalAlignment.ALIGN_MIDDLE);
        table.getCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_MIDDLE);
        setClosable();     
        table.getCellFormatter().addStyleName(0, 1, "TitleBar-CloseButton");
        table.getCellFormatter().setWidth(0, 1, "20px");
        table.getCellFormatter().setAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
        
    }
    
    /**
     * Returns the currently-set header text.
     * 
     * @return Returns the header text.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the header text.
     * 
     * @param text The text to set.
     */
    public void setText(String text) {
        this.text = text;
        String htmlText="";
        
        if(level<1 ||level >6)
            htmlText=text;
        else
            htmlText = "<h" + level + ">" + text + "</h" + level + ">";
        HTML html = new HTML(htmlText);
        html.addStyleName("TitleBar-Title");
        SimpleWindowMover simpleWindowMover = new SimpleWindowMover(window);
        html.addMouseDownHandler(simpleWindowMover);
        html.addMouseUpHandler(simpleWindowMover);
        html.addMouseOverHandler(simpleWindowMover);
        html.addMouseMoveHandler(simpleWindowMover);
        html.addMouseOutHandler(simpleWindowMover);
        
        table.setWidget(0, 0,html);
        if (image != null) {
            setCloseIconTitle();
        }
    }
    
    /**
     * Add the close 
     */
    public void setClosable() {
        image = new Image(GWT.getModuleBaseURL() + "images/close_icon.png");
        setCloseIconTitle();
        image.setStyleName("TitleBar-CloseButton");
        table.setWidget(0, 1, image);
    }
    
    
    private void setCloseIconTitle() {
        
        image.setTitle("Fermer");
        
    }
    
    public HasClickHandlers getClose(){
        return image;
    }
    
}
