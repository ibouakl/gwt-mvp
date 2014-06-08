package com.gwt.mvp.ui.showcase.client.main;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * 
 * @author ibouakl
 *
 */
public class FooterDisplay implements FooterPresenter.FooterPresenterDisplay {

    private FlowPanel mainPanel;
    
    public void init() {
        mainPanel  = new FlowPanel();
        mainPanel.add(new HTML("<h1> this is my footer  </h1>"));
    }

    public void dispose() {
        mainPanel.clear();
        mainPanel.removeFromParent();
        mainPanel = null;
        
    }

    public Widget asWidget() {
        return mainPanel;
    }
    
}
