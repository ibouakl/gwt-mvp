package com.gwt.mvp.client.event;
import com.google.gwt.event.shared.GwtEvent;
import com.gwt.mvp.client.Place;
/**
 * 
 * 
 * @author jguibert
 * @author ibouakl
 *
 */
public class PlaceRequestEvent extends GwtEvent<PlaceRequestHandler> {
    
    public static final Type<PlaceRequestHandler> TYPE = new Type<PlaceRequestHandler>();
    
    private final Place place;
    
    public PlaceRequestEvent(final Place place) {
        super();
        this.place = place;
    }
    
    @Override
    protected void dispatch(final PlaceRequestHandler handler) {
        handler.onPlaceRequest(place);
    }
    
    @Override
    public Type<PlaceRequestHandler> getAssociatedType() {
        return TYPE;
    }
    
}
