package com.gwt.ui.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author ibouakl
 *
 */
public class AdvanceVerticalPanel extends FlowPanel {
    public AdvanceVerticalPanel(){
        super();
        addStyleName("advanceVerticalPanel");
    }
    
    public void add(Widget widget){
        super.add(widget);
        widget.addStyleName("vertical");
    }
    
}
