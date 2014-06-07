package com.gwt.ui.client;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class SimpleWindowResizer implements MouseDownHandler, MouseUpHandler, MouseOverHandler, MouseMoveHandler, MouseOutHandler {
    
    private SimpleWindow simpleWindow;
    private int xoffset;
    private int yoffset;
    private boolean isResizing;
    
    public SimpleWindowResizer(SimpleWindow simpleWindow) {
        this.simpleWindow = simpleWindow;
    }
    
    @Override
    public void onMouseDown(MouseDownEvent event) {
        xoffset = event.getRelativeX(event.getRelativeElement());
        yoffset =  event.getRelativeY(event.getRelativeElement());
        isResizing = true;
        
    }
    
    @Override
    public void onMouseUp(MouseUpEvent event) {
        // turn off resizing, and release event capturing
        isResizing = false;
        DOM.releaseCapture((Element)event.getRelativeElement());
    }
    
    @Override
    public void onMouseOver(MouseOverEvent event) {
        // add a css style to the window that changes the cursor
        // turn on event capturing
        
        simpleWindow.addStyleName("simpleWindow-seResizeCursor");
        DOM.setCapture((Element)event.getRelativeElement());
    }
    
    @Override
    public void onMouseMove(MouseMoveEvent event) {
        if (isResizing) {
            // calculate the new width and height of the window
            // and call the window's setPixelSize method. Also
            // call the windowResized to notify the window
            // that its size has changed
            int newW = simpleWindow.getOffsetWidth() + event.getRelativeX(event.getRelativeElement()) - xoffset;
            int newH = simpleWindow.getOffsetHeight() + event.getRelativeY(event.getRelativeElement()) - yoffset;
            simpleWindow.setPixelSize(newW, newH);
            
        }
        
    }
    
    @Override
    public void onMouseOut(MouseOutEvent arg0) {
        // Return the Window's CSS styles to what they were 
        // before the resize started
        simpleWindow.removeStyleName("simpleWindow-seResizeCursors");
    }
    
}
