package com.gwt.mvp.client.event;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.gwt.mvp.client.Presenter;

/**
 * 
 * @author jguibert
 * <code>SelectionPresenterEvent</code>.
 * 
 */
public class SelectionPresenterEvent extends SelectionEvent<Presenter> {
    
    public static final GwtEvent.Type<SelectionPresenterHandler> TYPE = new GwtEvent.Type<SelectionPresenterHandler>();
    
    public SelectionPresenterEvent(final Presenter selectedItem) {
        super(selectedItem);
    }
    
}
