package com.gwt.mvp.client.presenter.loading;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * DefaultLoadingWidget implementation show a default text message "Loading...." and lock screen during loading occurs.
 * 
 */
public class DefaultLoadingWidget extends PopupPanel {
    
    /**
     * Build a new instance of <code>DefaultLoadingWidget</code>.
     */
    public DefaultLoadingWidget() {
        this(true);
    }
    
    /**
     * Build a new instance of <code>DefaultLoadingWidget</code> with specified behavior..
     * 
     * @param lockScreen the specified behavior: if true then the screen is locked while loading is shown.
     */
    public DefaultLoadingWidget(final boolean lockScreen) {
        super(false, lockScreen);
        add(new Label("Loading..."));
    }
    
}
