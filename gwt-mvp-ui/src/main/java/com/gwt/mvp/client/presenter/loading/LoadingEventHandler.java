package com.gwt.mvp.client.presenter.loading;

import com.google.gwt.event.shared.EventHandler;

/**
 * <code>LoadingEventHandler</code> class. From David Chandler Blog @see
 * http://turbomanage.wordpress.com/category/google-web-toolkit/
 * 
 */
public interface LoadingEventHandler extends EventHandler {
    public void onLoadingEvent(boolean isComplete);
}
