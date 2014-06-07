package com.gwt.mvp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.gwt.mvp.client.Place;

/**
 * This event is triggered when the request has changed manually (ie, not due to a {@link PlaceRequestEvent}). This allows the
 * {@link PlaceManager} to keep
 * track of the current location. Other classes may, but will typically not need
 * to, implement {@link PlaceChangedHandler} to be informed of manual changes.
 * 
 * 
 * @author jguibert
 * @author ibouakl
 * @author David Peterson
 */
public class PlaceChangedEvent extends GwtEvent<PlaceChangedHandler> {
    
    public static final Type<PlaceChangedHandler> TYPE = new Type<PlaceChangedHandler>();
    
    private final Place place;
    
    /**
     * Build a new instance of <code>PlaceChangedEvent</code>.
     * 
     * @param place
     */
    public PlaceChangedEvent(final Place place) {
        this.place = place;
    }
    
    @Override
    protected void dispatch(final PlaceChangedHandler handler) {
        handler.onPlaceChange(place);
    }
    
    @Override
    public Type<PlaceChangedHandler> getAssociatedType() {
        return TYPE;
    }
    
}
