package com.gwt.mvp.ui.showcase.client.main.dashboard;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author ibouakl
 */
public class DashBoardDisplay implements DashboardPresenter.DashboardPresenterDisplay {
    
    private FlowPanel mainPanel;
    
    @Override
    public void init() {
        mainPanel = new FlowPanel();
        mainPanel.add(new HTML("<h1>Good, you success to run this application.... </h1> "));
        
    }
    
    @Override
    public void dispose() {
        mainPanel.removeFromParent();
        mainPanel.clear();
        mainPanel = null;
    }
    
    @Override
    public Widget asWidget() {
        return mainPanel;
    }
    
}
