package com.gwt.mvp.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author jguibert
 */
public class SessionAttributeChangedEvent extends GwtEvent<SessionAttributeChangedEventHandler> {
    
    public static final Type<SessionAttributeChangedEventHandler> TYPE = new Type<SessionAttributeChangedEventHandler>();
    private final String key;
    private final Object value;
    
    public SessionAttributeChangedEvent(String key, Object value) {
        this.key = key;
        this.value = value;
    }
    
    @Override
    protected void dispatch(final SessionAttributeChangedEventHandler handler) {
        handler.onAttributChanged(key, value);
    }
    
    @Override
    public Type<SessionAttributeChangedEventHandler> getAssociatedType() {
        return TYPE;
    }
}
