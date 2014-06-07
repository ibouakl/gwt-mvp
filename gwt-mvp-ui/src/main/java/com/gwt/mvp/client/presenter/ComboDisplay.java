package com.gwt.mvp.client.presenter;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.presenter.ComboDisplay;

/**
 * @author jguibert
 * @author ibouakl
 *
 */
public class ComboDisplay implements ComboPresenter.ComboPresenterDisplay {

    private FlowPanel panel;
    private Widget current;

    public ComboDisplay() {
        super();
    }

    @Override
    public void init() {
        panel = new FlowPanel();
        current = null;
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setCurrentDisplay(Display display) {
        if (current != null) {
            panel.remove(current);
        }
        current = display.asWidget();
        panel.add(current);
    }

    @Override
    public void dispose() {
        panel = null;
        current = null;
    }
}
