package com.gwt.mvp.ui.showcase.client.main.content;

import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Place;
import com.gwt.mvp.client.event.PlaceRequestEvent;
import com.gwt.mvp.client.event.PlaceRequestHandler;
import com.gwt.mvp.client.presenter.BasePresenter;

public class ContentPresenter extends BasePresenter<ContentPresenter.ContentPresenterDisplay> {
    
    public final static Place PLACE = new Place("content");
    
    public ContentPresenter(ContentPresenterDisplay display, EventBus eventBus) {
        super(display, eventBus);
    }

    public interface ContentPresenterDisplay extends Display{
        
    }

    @Override
    protected void onBind() {
        eventBus.addHandler(PlaceRequestEvent.TYPE, new PlaceRequestHandler() {
            @Override
            public void onPlaceRequest(Place place) {
                if (place.equals(PLACE)) {
                    revealDisplay();
                }
            }
        });
    }

    @Override
    protected void onUnbind() {
        
    }

    @Override
    protected void onRevealDisplay() {
     
    }

    @Override
    protected void onDisposeDisplay() {
        
    }
}
