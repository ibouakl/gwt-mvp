package com.gwt.mvp.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.gwt.mvp.client.presenter.tab.TabItemPresenter;

/**
 * <code>TabItemClosedHander</code> declare method in order to handle <code>TabItemClosedEvent</code>.
 * 
 * @author jguibert
 */
public interface TabItemClosedHander extends EventHandler {
    /**
     * Called after tab presenter was closed.
     * 
     * @param presenter
     */
    public void onTabItemClosed(TabItemPresenter presenter);
}
