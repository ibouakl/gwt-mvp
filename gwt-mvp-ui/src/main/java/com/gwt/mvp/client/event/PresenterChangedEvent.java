package com.gwt.mvp.client.event;
import com.google.gwt.event.shared.GwtEvent;
import com.gwt.mvp.client.Presenter;
 
/**
 * @author jguibert
 * @author ibouakl
 *
 */
public class PresenterChangedEvent extends GwtEvent<PresenterChangedHandler> {
    
    public static final Type<PresenterChangedHandler> TYPE = new Type<PresenterChangedHandler>();
    
    private final Presenter presenter;
    
    public PresenterChangedEvent(final Presenter presenter) {
        this.presenter = presenter;
    }
    
    public Presenter getPresenter() {
        return presenter;
    }
    
    @Override
    protected void dispatch(final PresenterChangedHandler handler) {
        handler.onPresenterChanged(this);
    }
    
    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<PresenterChangedHandler> getAssociatedType() {
        return TYPE;
    }
}
