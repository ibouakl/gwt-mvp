package com.gwt.mvp.ui.showcase.client;

import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.ModuleEntryPoint;
import com.gwt.mvp.client.Place;
import com.gwt.mvp.client.presenter.ComboDisplay;
import com.gwt.mvp.client.presenter.ComboPresenter;
import com.gwt.mvp.client.presenter.RootPresenter;
import com.gwt.mvp.ui.showcase.client.main.MainDisplay;
import com.gwt.mvp.ui.showcase.client.main.MainPresenter;
import com.gwt.mvp.ui.showcase.client.main.MenuDisplay;
import com.gwt.mvp.ui.showcase.client.main.MenuPresenter;
import com.gwt.mvp.ui.showcase.client.main.content.ContentDisplay;
import com.gwt.mvp.ui.showcase.client.main.content.ContentPresenter;
import com.gwt.mvp.ui.showcase.client.main.dashboard.DashBoardDisplay;
import com.gwt.mvp.ui.showcase.client.main.dashboard.DashboardPresenter;

/**
 * @author ibouakl
 */
public class MainModule extends ModuleEntryPoint {
    
    private MainPresenter main = null;
    
    /**
     * Build a new instance of mvp-ui-showcase Module.
     */
    public MainModule() {
        super();
        
    }
    
    /**
     * Here we define all presenters
     * 
     * @author ibouakl
     */
    @Override
    protected void addPresenters(RootPresenter rootPresenter, EventBus eventBus) {
        main = new MainPresenter(new MainDisplay(), eventBus);
        // Header
        main.addPresenter(MainDisplay.Label.MENU, new MenuPresenter(new MenuDisplay(), eventBus));
        // center
        main.addPresenter(MainDisplay.Label.CENTER, new ComboPresenter<ComboDisplay>(new ComboDisplay(), eventBus, new DashboardPresenter(new DashBoardDisplay(), eventBus), new ContentPresenter(new ContentDisplay(), eventBus)));
        
        // root presenter: add the main presenter to root presenter
        rootPresenter.addPresenter(main);
    }
    
    /**
     * set the default place
     */
    @Override
    protected Place getDefaultPlace() {
        return DashboardPresenter.PLACE;
    }
    
    /**
     * @author ibouakl
     */
    @Override
    protected void revealRoot(final RootPresenter root, final EventBus eventBus) {
        root.revealRoot();
        fireDefaultEvent(eventBus);
    }
    
}
