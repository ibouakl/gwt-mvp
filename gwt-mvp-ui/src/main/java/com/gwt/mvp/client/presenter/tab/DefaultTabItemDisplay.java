package com.gwt.mvp.client.presenter.tab;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * <code>DefaultTabItemDisplay</code> provide default implementation of <code>TabItemPresenter.TabItemDisplay</code> without close mechanism.
 * 
 */
public class DefaultTabItemDisplay implements TabItemPresenter.TabItemDisplay {
    /** content */
    private Widget content;
    /** close click handler */
    private HasClickHandlers close;
    /** text tab */
    private String text;
    /** true if text is an html fragment */
    private boolean asHtml;
    
    /**
     * Build a new instance of <code>DefaultTabItemDisplay</code>.
     * 
     * @param text tab text
     */
    public DefaultTabItemDisplay(final String text) {
        this(text, false);
    }
    
    /**
     * Build a new instance of <code>DefaultTabItemDisplay</code>.
     * 
     * @param text tab text
     * @param asHtml true if text is an html fragment
     */
    public DefaultTabItemDisplay(final String text, final boolean asHtml) {
        super();
        this.text = text;
        this.asHtml = asHtml;
    }
    
    @Override
    public HasClickHandlers getClose() {
        return close;
    }
    
    @Override
    public Widget asWidget() {
        return content;
    }
    
    @Override
    public void init() {
        content = asHtml ? new HTML(text) : new Label(text);
        close = null;
    }
    
    @Override
    public void dispose() {
        content = null;
        close = null;
    }
    
}
