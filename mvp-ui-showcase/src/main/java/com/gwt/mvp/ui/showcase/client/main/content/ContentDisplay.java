package com.gwt.mvp.ui.showcase.client.main.content;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ContentDisplay implements ContentPresenter.ContentPresenterDisplay {
    
    private FlowPanel mainPanel;
    
    @Override
    public void init() {
        mainPanel = new FlowPanel();
        mainPanel.add(new HTML("<h1>put your content here</h1> "));
        
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
