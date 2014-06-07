package com.gwt.mvp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Presenter;

/**
 * Presenters can send this event to the {@link EventBus} to notify other
 * interested parties when the presenter has been 'revealed' on the screen. This
 * is particularly useful for situations where a presenter contains other
 * presenters and wants needs to reveal itself when a child presenter is
 * revealed.
 * 
 * 
 * @author jguibert
 * @author David Peterson
 */
public class PresenterRevealedEvent extends GwtEvent<PresenterRevealedHandler> {
    
    public static final GwtEvent.Type<PresenterRevealedHandler> TYPE = new GwtEvent.Type<PresenterRevealedHandler>();
    
    private final Presenter presenter;
    
    private final boolean originator;
    
    /**
     * Constructs a new revelation event, specifying that it is the originator.
     * 
     * @param presenter The presenter.
     */
    public PresenterRevealedEvent(final Presenter presenter) {
        this(presenter, true);
    }
    
    /**
     * Constructs a new revelation event, with the specified 'originator'
     * status.
     * 
     * @param presenter The presenter that has been revealed.
     * @param originator If <code>true</code>, the presenter is the originator of
     *            the revelation chain.
     */
    public PresenterRevealedEvent(final Presenter presenter, final boolean originator) {
        this.presenter = presenter;
        this.originator = originator;
    }
    
    public Presenter getPresenter() {
        return presenter;
    }
    
    /**
     * Returns <code>true</code> if the presenter in this event originated the
     * revelation, or <code>false</code> if it is a consequence of being
     * revealed by a child presenter.
     * 
     * @return <code>true</code> if the event was the originator.
     */
    public boolean isOriginator() {
        return originator;
    }
    
    @Override
    protected void dispatch(final PresenterRevealedHandler handler) {
        handler.onPresenterRevealed(this);
    }
    
    @Override
    public GwtEvent.Type<PresenterRevealedHandler> getAssociatedType() {
        return TYPE;
    }
    
}
