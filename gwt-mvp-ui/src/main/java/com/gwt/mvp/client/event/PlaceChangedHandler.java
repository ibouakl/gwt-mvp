package com.gwt.mvp.client.event;
import com.google.gwt.event.shared.EventHandler;
import com.gwt.mvp.client.Place;

/**
 * <code>PlaceChangedHandler</code> interface declare method {@link PlaceChangedHandler#onPlaceChange(Place)} to handle
 * {@link PlaceChangedEvent}.
 * 
 * 
 * @author jguibert
 * @author ibouakl
 */
public interface PlaceChangedHandler extends EventHandler {
    /**
     * Called after the current place has already changed. Allows handlers to
     * update any internal tracking, etc.
     * 
     * @param place
     *            The place request.
     */
    public void onPlaceChange(final Place place);
}
