package com.gwt.mvp.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author jguibert
 * @author ibouakl
 *
 */
public interface PresenterChangedHandler extends EventHandler {
    
    public void onPresenterChanged(final PresenterChangedEvent event);
    
}
