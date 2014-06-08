package com.gwt.mvp.ui.showcase.client.main.dashboard;

import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Place;
import com.gwt.mvp.client.event.PlaceRequestEvent;
import com.gwt.mvp.client.event.PlaceRequestHandler;
import com.gwt.mvp.client.presenter.BasePresenter;

/**
 * 
 * @author ibouakl
 *
 */
public class DashboardPresenter extends BasePresenter<DashboardPresenter.DashboardPresenterDisplay> {
    
    public final static Place PLACE = new Place("dashboard");
    
    
    public DashboardPresenter(DashboardPresenterDisplay display, EventBus eventBus) {
        super(display, eventBus);
    }

    public interface DashboardPresenterDisplay extends Display{
        
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
