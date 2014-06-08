package com.gwt.mvp.ui.showcase.client.main;

import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.presenter.CompositeDisplay;
import com.gwt.mvp.client.presenter.CompositePresenter;

/**
 * @author ibouakl
 */
public class MainPresenter extends CompositePresenter<MainPresenter.MainPresenterDisplay> {
    
    public MainPresenter(MainPresenterDisplay display, EventBus eventBus) {
        super(display, eventBus);
    }
    
    public interface MainPresenterDisplay extends CompositeDisplay {
        
    }
    
    @Override
    protected void onBind() {
        super.onBind();
    }
}
