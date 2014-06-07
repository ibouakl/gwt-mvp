package com.gwt.ui.client;

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;

public class InfoHelp extends FlowPanel implements HasMouseDownHandlers, HasMouseOverHandlers, HasMouseOutHandlers, HasMouseMoveHandlers {
    
    public InfoHelp(String helpHtml) {
        super();
        addStyleName("infoHelp");
        if (helpHtml != null && !helpHtml.trim().equals("")) {
            MobileTooltip mobileTooltip = new MobileTooltip(helpHtml);
            final MobileTooltipMouseListener mobileTooltipMouseListener = new MobileTooltipMouseListener(mobileTooltip);
            addMouseMoveHandler(mobileTooltipMouseListener);
            addMouseOutHandler(mobileTooltipMouseListener);
            addMouseOverHandler(mobileTooltipMouseListener);
            addMouseDownHandler(new MouseDownHandler() {
                
                @Override
                public void onMouseDown(MouseDownEvent arg0) {
                    mobileTooltipMouseListener.getMobileTooltip().hide();
                }
            });
        }
    }
    
    @Override
    public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
        return addDomHandler(handler, MouseDownEvent.getType());
    }
    
    @Override
    public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
        return addDomHandler(handler, MouseOverEvent.getType());
    }
    
    @Override
    public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
        return addDomHandler(handler, MouseOutEvent.getType());
    }
    
    @Override
    public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
        return addDomHandler(handler, MouseMoveEvent.getType());
    }
    
}
