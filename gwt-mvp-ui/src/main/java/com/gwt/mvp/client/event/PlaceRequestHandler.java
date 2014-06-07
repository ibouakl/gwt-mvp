package com.gwt.mvp.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.gwt.mvp.client.Place;

/**
 * <code>PlaceRequestHandler</code> interface declare method {@link PlaceRequestHandler#onPlaceRequest(Place)} to handle
 * {@link PlaceRequestEvent}.
 * 
 */
public interface PlaceRequestHandler extends EventHandler {
    /**
     * Called when something has requested a new place. Should be implemented by
     * instances which can show the place.
     * 
     * @param place
     *            The place request.
     */
    public void onPlaceRequest(final Place place);
}
