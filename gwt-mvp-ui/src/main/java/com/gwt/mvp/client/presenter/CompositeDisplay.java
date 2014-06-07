package com.gwt.mvp.client.presenter;

import com.gwt.mvp.client.Display;

/**
 * <code>CompositeDisplay</code> interface declare methods to add display into labeled zone, and for shows them.
 * 
 * @see Display
 * @author jguibert
 */
public interface CompositeDisplay extends Display {
    /**
     * Adds a widget.
     * 
     * @param label labeled zone
     * @param display The display to add
     */
    public void addDisplay(final String label, final Display display);
    
    /**
     * Show display.
     */
    public void show();
}
