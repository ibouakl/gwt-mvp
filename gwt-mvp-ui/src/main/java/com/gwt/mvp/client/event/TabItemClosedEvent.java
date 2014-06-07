package com.gwt.mvp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.gwt.mvp.client.presenter.tab.TabItemPresenter;

/**
 * <code>TabItemClosedEvent</code>.
 * 
 * @author Jerome Guibert
 */
public class TabItemClosedEvent extends GwtEvent<TabItemClosedHander> {
    
    public static final Type<TabItemClosedHander> TYPE = new Type<TabItemClosedHander>();
    
    private final TabItemPresenter presenter;
    
    public TabItemClosedEvent(final TabItemPresenter presenter) {
        super();
        this.presenter = presenter;
    }
    
    @Override
    protected void dispatch(TabItemClosedHander handler) {
        handler.onTabItemClosed(presenter);
    }
    
    @Override
    public Type<TabItemClosedHander> getAssociatedType() {
        return TYPE;
    }
    
}
