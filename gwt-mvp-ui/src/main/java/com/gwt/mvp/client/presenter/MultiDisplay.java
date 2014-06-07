package com.gwt.mvp.client.presenter;

import com.gwt.mvp.client.Display;

/**
 * <code>MultiDisplay</code> interface declare methods to add/remove display instance.
 * 
 * @see Display
 * @author jguibert
 */
public interface MultiDisplay extends Display {
    
    /**
     * Add this display.
     * 
     * @param display
     */
    public void add(final Display display);
    
    /**
     * Remove this display.
     * 
     * @param display
     */
    public void remove(final Display display);
}
