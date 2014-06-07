package com.gwt.ui.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class CustomFlowPanel extends FlowPanel {
    protected abstract String getFlowStyle();
    
    @Override
    public void add(Widget w) {
        w.getElement().getStyle().setProperty("display", getFlowStyle());
        super.add(w);
    }
    
}
