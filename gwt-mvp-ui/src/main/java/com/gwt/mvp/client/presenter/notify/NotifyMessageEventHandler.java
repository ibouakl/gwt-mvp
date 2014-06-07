package com.gwt.mvp.client.presenter.notify;

import com.google.gwt.event.shared.EventHandler;

/**
 * <code>NotifyMessageEventHandler</code> declaration.
 * 
 */
public interface NotifyMessageEventHandler extends EventHandler {
    
    /**
     * Called when a <code>NotifyMessageEvent</code> is received.
     * 
     * @param message message to display
     * @param delayMillis time to live of this message
     */
    public void onMessage(final String message, final int delayMillis);
}
