package com.gwt.mvp.ui.showcase.client;

import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.ModuleEntryPoint;
import com.gwt.mvp.client.Place;
import com.gwt.mvp.client.presenter.ComboDisplay;
import com.gwt.mvp.client.presenter.ComboPresenter;
import com.gwt.mvp.client.presenter.RootPresenter;
import com.gwt.mvp.ui.showcase.client.main.FooterDisplay;
import com.gwt.mvp.ui.showcase.client.main.FooterPresenter;
import com.gwt.mvp.ui.showcase.client.main.MainDisplay;
import com.gwt.mvp.ui.showcase.client.main.MainPresenter;
import com.gwt.mvp.ui.showcase.client.main.MenuDisplay;
import com.gwt.mvp.ui.showcase.client.main.MenuPresenter;
import com.gwt.mvp.ui.showcase.client.main.dashboard.DashBoardDisplay;
import com.gwt.mvp.ui.showcase.client.main.dashboard.DashboardPresenter;

/**
 * @author ibouakl
 */
public class MainModule extends ModuleEntryPoint {
    
    private MainPresenter main = null;
    
    /**
     * Build a new instance of <code>yalla vote admin Module</code>.
     */
    public MainModule() {
        super();
        
    }
    
    @Override
    protected void addPresenters(RootPresenter rootPresenter, EventBus eventBus) {
        main = new MainPresenter(new MainDisplay(), eventBus);
        // Header
        main.addPresenter(MainDisplay.Label.MENU, new MenuPresenter(new MenuDisplay(), eventBus));
        // center
        main.addPresenter(MainDisplay.Label.CENTER, new ComboPresenter<ComboDisplay>(new ComboDisplay(), eventBus, new DashboardPresenter(new DashBoardDisplay(), eventBus)));
        
        main.addPresenter(MainDisplay.Label.FOOTER, new FooterPresenter(new FooterDisplay(), eventBus));
    }
    
    @Override
    protected Place getDefaultPlace() {
        return DashboardPresenter.PLACE;
    }
    
    @Override
    protected void revealRoot(final RootPresenter root, final EventBus eventBus) {
        root.revealRoot();
        fireDefaultEvent(eventBus);
    }
    
}
