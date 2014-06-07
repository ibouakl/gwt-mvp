package com.gwt.mvp.client.presenter.notify;
import com.google.gwt.event.shared.GwtEvent;

/**
 * <code>NotifyMessageEvent</code> implementation.
 * 
 */
public class NotifyMessageEvent extends GwtEvent<NotifyMessageEventHandler> {
    public final static Type<NotifyMessageEventHandler> TYPE = new Type<NotifyMessageEventHandler>();
    private final String message;
    private final int delayMillis;
    
    /**
     * Build a new instance of <code>NotifyMessageEvent</code> with a default delay (4000 milliseconds).
     * 
     * @param message
     */
    public NotifyMessageEvent(final String message) {
        this(message, 4000);
    }
    
    /**
     * Build a new instance of <code>NotifyMessageEvent</code>.
     * 
     * @param message
     * @param delayMillis delay in milliseconds
     */
    public NotifyMessageEvent(final String message, final int delayMillis) {
        super();
        this.message = message;
        this.delayMillis = delayMillis;
    }
    
    @Override
    protected void dispatch(NotifyMessageEventHandler handler) {
        handler.onMessage(message, delayMillis);
    }
    
    @Override
    public Type<NotifyMessageEventHandler> getAssociatedType() {
        return TYPE;
    }
    
}
