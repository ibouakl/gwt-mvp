package com.gwt.mvp.client.presenter;

import com.gwt.mvp.client.event.PresenterRevealedEvent;

/**
 * @author jguibert
 */
public interface DisplayListener {
    
    /**
     * Called before firing a {@link PresenterRevealedEvent}. Add all handler associated with the display here.
     */
    public void onRevealDisplay();
    
    /**
     * This method is called after display disposed. You could remove all display handler here.
     */
    public void onDisposeDisplay();
}
