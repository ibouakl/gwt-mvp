package com.gwt.mvp.client.presenter.loading;

import com.google.gwt.event.shared.GwtEvent;

/**
 * <code>LoadingEvent</code> class. From David Chandler Blog @see http://turbomanage.wordpress.com/category/google-web-toolkit/
 * 
 *  @author jguibert
 *  @author ibouakl
 * 
 */

public class LoadingEvent extends GwtEvent<LoadingEventHandler> {
    public final static Type<LoadingEventHandler> TYPE = new Type<LoadingEventHandler>();
    
    protected final boolean isComplete;
    
    /**
     * Build a new instance of <code>LoadingEvent</code> with uncompleted state.
     */
    public LoadingEvent() {
        this(false);
    }
    
    /**
     * Build a new instance of <code>LoadingEvent</code>.
     * 
     * @param isComplete
     */
    public LoadingEvent(final boolean isComplete) {
        super();
        this.isComplete = isComplete;
    }
    
    @Override
    protected void dispatch(LoadingEventHandler handler) {
        handler.onLoadingEvent(isComplete);
    }
    
    @Override
    public Type<LoadingEventHandler> getAssociatedType() {
        return TYPE;
    }
    
}
