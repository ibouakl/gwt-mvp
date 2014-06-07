package com.gwt.ui.client;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Class to create a window (Pop-up panel)
 * 
 * @author ibouakl
 */
public class SimpleWindow extends PopupPanel {
    
    private static final int DEFAULT_WIDTH = 500;
    
    private static final int DEFAULT_HEIGHT = 300;
    
    protected static final int SIDE_FRAME_WIDTH = 8;
    
    protected static final int TOP_FRAME_HEIGHT = 22;
    
    protected static final int BOTTOM_FRAME_HEIGHT = 16;
    
    // Z index of top-most window. this value is incremented
    // relentlessly in incrementZIndex();
    private static int topZIndex = 0;
    private WindowTitleBar titleBar;
    private Widget contentWidget; // widget displayed in window
    private SimpleFlexTable table; // the popup panel's widget
    
    /**
     * this constructor is for subclasses that need to instantiate a window without content. to set content later, call
     * setContentWidget(Widget)
     */
    public SimpleWindow() {
        this(null);
    }
    
    public SimpleWindow(Widget widget) {
        this(widget, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public SimpleWindow(Widget widget, int width, int height) {
        
        setPixelSize(width, height);
        if (widget != null) // If no widget is supplied here
            setContent(widget);// you must call setContent() later
            
    }
    
    public void show() {
        show(true);
        
    }
    
    public void show(boolean moveToFront) {
        super.show();
        if (moveToFront)
            moveToFront();
        
    }
    
    public void setContent(Widget widget) {
        contentWidget = widget;
        contentWidget.addStyleName("SimpleWindow-content");
        createAndPopulateTable();
        
    }
    
    private void createAndPopulateTable() {
        table = new SimpleFlexTable();
        table.setWidth("100%"); // Make Sure the table fills
        table.setHeight("100%"); // the popup window
        table.setBorderWidth(0); // Make all cells in the table
        table.setCellPadding(0);// fit together, with no gaps
        table.setCellPadding(0);
        
        // create the pieces of the table
        
        createTop();
        createContent();
        
        // The popup panel's lone widget is the flex table
        setWidget(table);
        
        // For our simple windows, we want to handle mouse clicks so that a mouse click anywhere in a window brings the window to the front
        // of all other windows. But pop-up panels do not source mouse events, so we cannot attach a mouse listener to a pop-up panel and
        // handle mouse clicks. So we use the sinking events. When you sink events, GWT call the widget method onBroweEvent for the type
        // event(s) that you sank. In that method, we move the window to the front by incrementing the Z index for the widget's DOM element.
        
        sinkEvents(Event.ONCLICK);
        
    }
    
    public void onBrowseEvent(Event event) {
        moveToFront();
    }
    
    /**
     * move the window to the front by incrementing the Z index for the widget's DOM element.
     */
    public void moveToFront() {
        incrementZIndex(getElement());
    }
    
    /**
     * By default, PopupPanel eats mouse events outside of the popup ( unless it’ s a mouse click and autohide enabled) by returning false
     * from onEventPreview. That’ s fine for a popup, but for our window, we don't want to disallow mouse events outside of the window, so
     * we override onEventpreview to return true. That simple override cancels the inherited event previewing and simply allows all mouse
     * events to pass through to other widgets in the application.
     */
    public boolean onEventPreview(Event event) {
        return true;
        
    }
    
    /**
     * Convenience method: even thought we extend popup, this class conceptually represents window
     * 
     * @param left
     * @param top
     */
    public void setWindowPosition(int left, int top) {
        setPopupPosition(left, top);
    }
    
    /**
     * delegate setting of the title to the window bar
     */
    public void setTitle(String title) {
        titleBar.setText(title);
    }
    
    // PRIVATE HELPER METHODS FOR CONSTRUCTING THE INDIVIDUAL
    // PIECES OF THE POPUP's FLEX TABLE FOLLOW
    
    private void createTop() {
        titleBar = new WindowTitleBar("", 0, this);
        table.setWidget(0, 0, titleBar);
        table.getFlexCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
        
    }
    
    private void createContent() {
        table.setWidget(1, 0, contentWidget);
        table.getCellFormatter().setWidth(1, 0, "100%");
        table.getCellFormatter().setHeight(1, 0, "100%");
    }
    

    /**
     * Relentlessly increment the Z index Called by moveToFront() only
     * 
     * @param element
     */
    private void incrementZIndex(Element element) {
        DOM.setIntStyleAttribute(element, "zIndex", ++topZIndex);
    }
    
    public HasClickHandlers getClose() {
        return titleBar.getClose();
    }
    
}
