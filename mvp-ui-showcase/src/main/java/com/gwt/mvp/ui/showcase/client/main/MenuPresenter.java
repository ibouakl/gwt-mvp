package com.gwt.mvp.ui.showcase.client.main;

import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.presenter.BasePresenter;

/**
 * 
 * @author ibouakl
 *
 */
public class MenuPresenter extends BasePresenter<MenuPresenter.MenuPresenterDisplay> {
 
    public MenuPresenter(MenuPresenterDisplay display, EventBus eventBus) {
        super(display, eventBus);
    }

    public interface MenuPresenterDisplay extends Display{
        
    }

    @Override
    protected void onBind() {
        
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
