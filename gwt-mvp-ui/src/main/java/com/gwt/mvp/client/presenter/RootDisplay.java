package com.gwt.mvp.client.presenter;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.mvp.client.Display;

/**
 * <code>RootDisplay</code> class.
 * 
 */
public class RootDisplay implements CompositeDisplay {
    
    private RootPanel rootPanel;
    
    public RootDisplay() {
        super();
    }
    
    @Override
    public Widget asWidget() {
        return rootPanel;
    }
    
    @Override
    public void init() {
        rootPanel = RootPanel.get();
    }
    
    @Override
    public void dispose() {
        for (int i = 0; i <= rootPanel.getWidgetCount(); i++)
            rootPanel.remove(i);
    }
    
    @Override
    public void addDisplay(final String label, final Display display) {
        addDisplay(display);
    }
    
    @Override
    public void show() {
    }
    
    public void addDisplay(final Display display) {
        rootPanel.add(display.asWidget());
    }
    
    public void removeDisplay(final Display display) {
        rootPanel.remove(display.asWidget());
        
    }
    
}
