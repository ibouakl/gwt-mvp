package com.gwt.mvp.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.mvp.client.presenter.loading.LoadingEvent;

/**
 * A callback class for our dispatch calls. This provides a convenient way to handle error cases and we can save some code as well. Inspired
 * by HupaCallback in the Apache HUPA project.
 * <p>
 * Adding loading display
 * </p>
 * 
 * @author Andreas Borglin
 */
public abstract class DispatchCallback<T> implements AsyncCallback<T> {
    
    private final EventBus eventBus;
    
    /**
     * Build a new instance of <code>DispatchCallback</code>.
     */
    public DispatchCallback() {
        this(null);
    }
    
    public DispatchCallback(EventBus eventBus) {
        super();
        this.eventBus = eventBus;
        
        if (eventBus != null) {
            eventBus.fireEvent(new LoadingEvent(false));
        }
    }
    
    @Override
    public void onFailure(final Throwable caught) {
        if (eventBus != null) {
            eventBus.fireEvent(new LoadingEvent(true));
        }
        
        callbackError(caught);
    }
    
    @Override
    public void onSuccess(final T result) {
        if (eventBus != null) {
            eventBus.fireEvent(new LoadingEvent(true));
        }
        callback(result);
    }
    
    /**
     * Must be overriden by clients to handle callbacks
     * 
     * @param result
     */
    public abstract void callback(final T result);
    
    /**
     * Should be overriden by clients who want to handle error cases themselves.
     */
    public void callbackError(final Throwable t) {
        Window.alert("RPC failed: " + t.toString());
    }
}
