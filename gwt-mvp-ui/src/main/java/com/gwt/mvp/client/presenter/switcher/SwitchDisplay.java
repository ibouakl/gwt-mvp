package com.gwt.mvp.client.presenter.switcher;

import com.google.gwt.user.client.ui.Widget;
import com.gwt.mvp.client.Display;

/**
 * 
 * 
 *  @author jguibert
 * @author ibouakl
 *
 */
public interface SwitchDisplay extends Display {
    
    public void add(Display display);
    
    public void remove(Display display);
    
    public void show(int index);
    
    public void show(Widget widget);
}
