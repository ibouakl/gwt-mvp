package com.gwt.ui.client;

/**
 * Use this class to create many <div> on the same line. Use new Label() to create a div element and put it into the HorizontalFlowPanel
 * 
 * @author ibouakl
 */
public class HorizontalFlowPanel extends CustomFlowPanel {
    
    @Override
    protected String getFlowStyle() {
        return "inline";
    }
    
}
