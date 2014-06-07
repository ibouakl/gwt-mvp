package com.gwt.mvp.client.presenter.loading;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <code>LoadingDisplay</code> class. From David Chandler Blog @see http://turbomanage.wordpress.com/category/google-web-toolkit/
 * 
 * @author jguibert
 * @author ibouakl
 */
public class LoadingDisplay implements LoadingPresenter.LoadingDisplay {
    
    // inner widget
    private PopupPanel panel;
    
    /**
     * Build a new instance of <code>LoadingDisplay</code>.
     */
    public LoadingDisplay(PopupPanel popupPanel) {
        super();
        this.panel = popupPanel;
    }
    
    @Override
    public Widget asWidget() {
        return panel;
    }
    
    @Override
    public void hide() {
        panel.hide();
    }
    
    @Override
    public void show() {
        panel.center();
        panel.show();
    }
    
    @Override
    public void dispose() {
    }
    
    @Override
    public void init() {
    }
}
