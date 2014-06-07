package com.gwt.mvp.client.presenter.notify;

import com.gwt.mvp.client.Display;

public class DefaultNotifyDisplayFactory  implements NotifyDisplayFactory{

    @Override
    public Display build(String message, int stackIndex) {
        return new NotifyDisplay(message, stackIndex);
    }
    
}
