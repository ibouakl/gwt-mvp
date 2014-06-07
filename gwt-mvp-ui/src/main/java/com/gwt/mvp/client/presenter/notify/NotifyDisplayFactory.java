package com.gwt.mvp.client.presenter.notify;

import com.gwt.mvp.client.Display;

public interface NotifyDisplayFactory {
    /**
     * Build a new instance of ..
     * @param message message to show
     * @param stackIndex stack index
     * @return
     */
    public Display build(final String message, final int stackIndex);
}
