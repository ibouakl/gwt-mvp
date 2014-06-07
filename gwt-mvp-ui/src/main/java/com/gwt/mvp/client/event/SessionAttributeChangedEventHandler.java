package com.gwt.mvp.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author jguibert
 */
public interface SessionAttributeChangedEventHandler extends EventHandler {
    
    public void onAttributChanged(final String key, final Object value);
}
