package com.gwt.mvp.ui.showcase.client.main;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.mvp.client.Display;

/**
 * @author ibouakl
 */
public class MainDisplay implements MainPresenter.MainPresenterDisplay {
    
    /**
     * Label declared.
     */
    public enum Label {
        MENU, CENTER, FOOTER
    }
    
    private FlowPanel mainPanel;
    private FlowPanel menuPanel;
    private FlowPanel footer;
    private FlowPanel centerPanel;
    
    public void addDisplay(String label, Display display) {
        Label l = Label.valueOf(label);
        
        /** header label */
        if (Label.MENU.equals(l)) {
            menuPanel.add(display.asWidget());
        }
        
        /** center label */
        if (Label.CENTER.equals(l)) {
            centerPanel.add(display.asWidget());
        }
        
        if (Label.FOOTER.equals(l)) {
            footer.add(display.asWidget());
        }
        
    }
    
    public void show() {
        
    }
    
    public void init() {
        mainPanel = new FlowPanel();
        mainPanel.addStyleName("masterPanel");
        mainPanel.addStyleName("container_16");
        // add the menu panel
        menuPanel = new FlowPanel();
        mainPanel.add(menuPanel);
        menuPanel.addStyleName("headerMasterPanel");
        menuPanel.addStyleName("grid_16");
        // center panel
        FlowPanel panel = new FlowPanel();
        mainPanel.add(panel);
        panel.addStyleName("grid_16");
        panel.addStyleName("centerMasterPanel");
        
        centerPanel = new FlowPanel();
        panel.add(centerPanel);
        
        footer = new FlowPanel();
        panel.add(footer);
    }
    
    public void dispose() {
        mainPanel.clear();
        mainPanel.removeFromParent();
        menuPanel = null;
        centerPanel = null;
        mainPanel = null;
        footer = null;
    }
    
    public Widget asWidget() {
        return mainPanel;
    }
    
}
