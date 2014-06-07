package com.gwt.mvp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.PopupPanel;
import com.gwt.mvp.client.event.PlaceRequestEvent;
import com.gwt.mvp.client.presenter.RootDisplay;
import com.gwt.mvp.client.presenter.RootPresenter;
import com.gwt.mvp.client.presenter.loading.DefaultLoadingWidget;
import com.gwt.mvp.client.presenter.loading.LoadingDisplay;
import com.gwt.mvp.client.presenter.loading.LoadingPresenter;
import com.gwt.mvp.client.presenter.notify.DefaultNotifyDisplayFactory;
import com.gwt.mvp.client.presenter.notify.NotifyDisplayFactory;
import com.gwt.mvp.client.presenter.notify.NotifyPresenter;

/**
 * ModuleEntryPoint initialize all necessary stuff for launch a MVP application module.
 * 
 * 
 * @author jguibert
 * @author ibouakl
 */
public abstract class ModuleEntryPoint implements EntryPoint {

    private boolean loadingActivated;
    private boolean notifyActivated;

    /**
     * Build a new instance of ModuleEntryPoint.
     */
    public ModuleEntryPoint() {
        this(true, true);
    }

    /**
     * Build a new instance of <code>ModuleEntryPoint</code>.
     * 
     * @param loading if true then adds loading extra feature
     * @param notify if true then adds notify extra feature
     */
    public ModuleEntryPoint(boolean loading, boolean notify) {
        super();
        this.loadingActivated = loading;
        this.notifyActivated = notify;
    }

    @Override
    public void onModuleLoad() {

        final EventBus eventBus = new EventBusManager();

        /** Setup root container */
        RootPresenter presenter = new RootPresenter(newRootDisplay(), eventBus);

        /** add child presenter */
        addPresenters(presenter, eventBus);

        /** add utilities */
        addFeatures(presenter, eventBus);

        revealRoot(presenter, eventBus);
        
        fireDefaultEvent(eventBus);
    }

    protected void fireDefaultEvent(final EventBus eventBus) {
        /** Fire current place if we have an history, either we load default page */
        if (!eventBus.fireCurrentPlace()) {
            eventBus.fireEvent(new PlaceRequestEvent(getDefaultPlace()));
        }
    }

    /**
     * Bind and reveal all
     * @param presenter
     */
    protected void revealRoot(RootPresenter presenter, final EventBus eventBus) {
        /** Reveall All */
        presenter.revealRoot();
    }

    /**
     * @return a new <code>RootDisplay</code> instance.
     */
    protected RootDisplay newRootDisplay() {
        return new RootDisplay();
    }

    /**
     * Add all Utilities.
     * 
     * @param presenter
     * @param eventBus
     */
    protected void addFeatures(final RootPresenter presenter, final EventBus eventBus) {
        if (notifyActivated) {
            addNotifyFeature(presenter, eventBus);
        }
        if (loadingActivated) {
            addLoadingFeature(eventBus);
        }
    }

    /**
     * Add notify message utility.
     * 
     * @param presenter
     * @param eventBus
     */
    private void addNotifyFeature(final RootPresenter presenter, final EventBus eventBus) {
        NotifyPresenter notifyPresenter = new NotifyPresenter(presenter.getDisplay(), eventBus, getNotifyDisplayFactory());
        notifyPresenter.bind();
    }

    /**
     * Override default implementation of <code>NotifyDisplayFactory</code> in order to customize notify display widget.
     * 
     * @return an instance of <code>NotifyDisplayFactory</code>.
     */
    protected NotifyDisplayFactory getNotifyDisplayFactory() {
        return new DefaultNotifyDisplayFactory();
    }

    /**
     * Add loading feature.
     * 
     * @param presenter root presenter instance
     * @param eventBus event bus instance
     */
    private void addLoadingFeature(final EventBus eventBus) {
        LoadingPresenter loadingPresenter = new LoadingPresenter(new LoadingDisplay(newLoadingWidget()), eventBus);
        loadingPresenter.bind();
        loadingPresenter.revealDisplay();
    }

    /**
     * @return a new instance of <code>PopupPanel</code> used for loading display.
     */
    protected PopupPanel newLoadingWidget() {
        return new DefaultLoadingWidget();
    }

    /**
     * Initialize root presenter by adding all necessary presenters.
     * 
     * @param rootPresenter
     * @param eventBus
     */
    protected abstract void addPresenters(final RootPresenter rootPresenter, final EventBus eventBus);

    /**
     * @return a default place .
     */
    protected abstract Place getDefaultPlace();
}
