package com.gwt.ui.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author ibouakl
 */
public class AdvanceHorizontalPanel extends FlowPanel {
    
    public AdvanceHorizontalPanel() {
        super();
        addStyleName("advanceHorizontalPanel");
    }
    
    public void addLeft(Widget widget) {
        add(widget);
        widget.addStyleName("left");
    }
    
    public void addRight(Widget widget) {
        add(widget);
        widget.addStyleName("right");
    }
    
}
