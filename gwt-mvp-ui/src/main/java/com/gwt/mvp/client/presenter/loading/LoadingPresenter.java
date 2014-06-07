package com.gwt.mvp.client.presenter.loading;

import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.presenter.BasePresenter;

/**
 * <code>LoadingPresenter</code> presnter implement mecanism for showing a loading panel.
 * <p>
 * From an idea original of David Chandler Blog @see http://turbomanage.wordpress.com/category/google-web-toolkit/
 * </p>
 * 
 * @author jguibert
 * @author ibouakl
 */
public class LoadingPresenter extends BasePresenter<LoadingPresenter.LoadingDisplay> {
    
    /**
     * <code>LoadingDisplay</code> interface define methods to control hide/show mechanism.
     * 
     * @author Jerome Guibert
     */
    public interface LoadingDisplay extends Display {
        /**
         * Hide Loading display.
         */
        void hide();
        
        /**
         * show Loading display.
         */
        void show();
    }
    
    /**
     * Build a new instance of <code>LoadingPresenter</code>.
     * 
     * @param display
     * @param eventBus
     */
    public LoadingPresenter(LoadingDisplay display, EventBus eventBus) {
        super(display, eventBus);
        bind();
    }
    
    @Override
    protected void onBind() {
        registerHandler(this.eventBus.addHandler(LoadingEvent.TYPE, new LoadingEventHandler() {
            @Override
            public void onLoadingEvent(boolean isComplete) {
                if (isComplete) {
                    display.hide();
                } else {
                    display.show();
                }
            }
        }));
    }
    
    @Override
    protected void onUnbind() {
        
    }
    
    @Override
    protected void onRevealDisplay() {
        display.hide();
    }
    
    @Override
    protected void onDisposeDisplay() {
        
    }
}
