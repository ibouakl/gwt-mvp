package com.gwt.mvp.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * <code>Display</code> interface applied to GWT system.
 * 
 * 
 * @author jguibert
 * @author ibouakl
 */
public interface Display {
    
    /**
     * Initialize the display.
     */
    public void init();
    
    /**
     * Dispose the display.
     */
    public void dispose();
    
    /**
     * Here come GWT specific.
     * Returns the display as a GWT {@link Widget}. This may be the same
     * Display instance, or another object completely.
     * 
     * @return widget representation of this display.
     */
    public Widget asWidget();
}
