package com.gwt.ui.client;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class MobileTooltipMouseListener implements MouseDownHandler,MouseMoveHandler, MouseOutHandler,MouseOverHandler  {

    public static final int ST_NORMAL = 0;
    public static final int ST_STICKY = 1;
    public static final int ST_FORCE_HIDE = 2;
    
    private int state;
    private MobileTooltip mobileTooltip;
    
    public MobileTooltipMouseListener(MobileTooltip mobileTooltip){
        this.mobileTooltip = mobileTooltip;
        this.state = ST_NORMAL;
    }
    
    
    @Override
    public void onMouseOver(MouseOverEvent arg0) {
        if(this.state != ST_STICKY && this.state != ST_FORCE_HIDE){
            this.state = ST_NORMAL;
            mobileTooltip.show();
        }
        
    }
    

    
    @Override
    public void onMouseMove(MouseMoveEvent event) {
        
        Widget sender = (Widget)event.getSource();
        int x = event.getRelativeX(sender.getElement());
        int y = event.getRelativeY(sender.getElement());
        
        //only update the position of the tooltip in NORMAL state
        if(this.state == ST_FORCE_HIDE || this.state == ST_STICKY){
            return;
        }
        
        //calculate the posistion of the mouse pointer
        //relative to the client window
        mobileTooltip.setPopupPosition(
                this.getDisplayLocationX(sender, x),
                this.getDisplayLocationY(sender, y));
        
    }

    
     
    private int getDisplayLocationX(Widget sender, int x){
        return sender.getAbsoluteLeft() + x +
                getPageScrollLeft() + mobileTooltip.getOffsetX();
    }
    
    private int getDisplayLocationY(Widget sender, int y){
        return sender.getAbsoluteTop() + y +
                getPageScrollTop() + mobileTooltip.getOffsetY();
    }
    
 
    
    @Override
    public void onMouseDown(MouseDownEvent event) {      
        Widget sender = (Widget)event.getSource();
        int x = event.getRelativeX(sender.getElement());
        int y = event.getRelativeY(sender.getElement());
        if(this.state == ST_STICKY){
            mobileTooltip.hide();
            this.state = ST_FORCE_HIDE;
            return;
        }
        mobileTooltip.setPopupPosition(
                this.getDisplayLocationX(sender, x),
                this.getDisplayLocationY(sender, y));
        if(this.state == ST_FORCE_HIDE){
            mobileTooltip.show();
            this.state = ST_NORMAL;
        }else if(this.state == ST_NORMAL){
            mobileTooltip.show();
            this.state = ST_STICKY;
        }
        
    }


    
    @Override
    public void onMouseOut(MouseOutEvent arg0) {
        if(this.state != ST_STICKY){
            mobileTooltip.hide();
        }
        
    }
    
    
    private int getPageScrollTop() {
        return 0;
    }
    
    /**
     * Get the offset for the vertical scroll
     * Again.
     * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/220a035f47b5ac66/dcfc19a3534f7715?lnk=gst&q=tooltip+listener&rnum=1#dcfc19a3534f7715
     */
    private int getPageScrollLeft() {
        return 0;
    }

    public MobileTooltip getMobileTooltip() {
        return mobileTooltip;
    }
 


 
}