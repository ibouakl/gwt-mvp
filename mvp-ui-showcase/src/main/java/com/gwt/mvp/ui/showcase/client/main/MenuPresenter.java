package com.gwt.mvp.ui.showcase.client.main;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.event.PlaceRequestEvent;
import com.gwt.mvp.client.presenter.BasePresenter;
import com.gwt.mvp.ui.showcase.client.main.content.ContentPresenter;
import com.gwt.mvp.ui.showcase.client.main.dashboard.DashboardPresenter;

/**
 * @author ibouakl
 */
public class MenuPresenter extends BasePresenter<MenuPresenter.MenuPresenterDisplay> {
    
    public MenuPresenter(MenuPresenterDisplay display, EventBus eventBus) {
        super(display, eventBus);
    }
    
    public interface MenuPresenterDisplay extends Display {
        public void addDashboardClickHandler(ClickHandler clickHandler);
        
        public void addContentClickHandler(ClickHandler clickHandler);
    }
    
    @Override
    protected void onBind() {
        
    }
    
    @Override
    protected void onUnbind() {
        
    }
    
    @Override
    protected void onRevealDisplay() {
        display.addContentClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new PlaceRequestEvent(ContentPresenter.PLACE));
                
            }
        });
        
        display.addDashboardClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new PlaceRequestEvent(DashboardPresenter.PLACE));
                
            }
        });
    }
    
    @Override
    protected void onDisposeDisplay() {
        
    }
}
