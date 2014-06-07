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

/**
 * 
 * @author ibouakl
 *
 */
public class SimpleWindowMover implements MouseDownHandler, MouseUpHandler, MouseOverHandler, MouseMoveHandler, MouseOutHandler {
    
    private SimpleWindow window;
    private boolean isMoving = false;
    private int moveStartX;
    private int moveStartY;
    
    public SimpleWindowMover(SimpleWindow window) {
        this.window = window;
    }
    
    @Override
    public void onMouseDown(MouseDownEvent event) {
        window.removeStyleName("SimpleWindow-MoveCursor");
        window.addStyleName("SimpleWindow-Pointer");
        moveStartX = event.getRelativeX(event.getRelativeElement());
        moveStartY = event.getRelativeY(event.getRelativeElement());
        isMoving = true;
        
        // When the user pressess the mouse in the window bar outside of the close button, we capture events, meaning that until we call
        // DOM.realaseCapture in onMouseUp, the window bar will be the only widget that is notified of events. That will make deagging the
        // window more efficient
        DOM.setCapture((Element)event.getRelativeElement());
        
    }
    
    @Override
    public void onMouseUp(MouseUpEvent event) {
        if (isMoving) {
            window.removeStyleName("SimpleWindow-Pointer");
            isMoving = false;
            
        }
        // if the user releases the mouse in the window bar outside of the close button, we release the capture tjat we set in onMouseDown()
        // by calling DOM.setCapture(). that returns event handling to normal and ends the window bar's exclusive rights to all mouse events
        DOM.releaseCapture((Element)event.getRelativeElement());
    }
    
    @Override
    public void onMouseOver(MouseOverEvent arg0) {
        window.addStyleName("SimpleWindow-MoveCursor");
    }
    
    @Override
    public void onMouseMove(MouseMoveEvent event) {
        if (isMoving) {  
            // move the window as the use is draging the mouse
            int deltaX = event.getRelativeX(event.getRelativeElement())- moveStartX;
            int deltaY = event.getRelativeY(event.getRelativeElement()) -moveStartY ;
            window.setWindowPosition(window.getAbsoluteLeft()+deltaX, window.getAbsoluteTop()+deltaY);     
        }  
    }
    
    @Override
    public void onMouseOut(MouseOutEvent arg0) {
        window.removeStyleName("SimpleWindow-MoveCursor");
    }
    
}
