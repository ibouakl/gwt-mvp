package com.gwt.mvp.ui.showcase.client;

import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.ModuleEntryPoint;
import com.gwt.mvp.client.Place;
import com.gwt.mvp.client.presenter.RootPresenter;

/**
 * 
 * @author ibouakl
 *
 */
public class MainModule extends ModuleEntryPoint  {

    @Override
    protected void addPresenters(RootPresenter rootPresenter, EventBus eventBus) {
        
    }

    @Override
    protected Place getDefaultPlace() {
        return null;
    }
    
}
