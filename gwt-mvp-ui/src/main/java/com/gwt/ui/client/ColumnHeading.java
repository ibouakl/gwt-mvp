package com.gwt.ui.client;

import com.google.gwt.user.client.ui.Widget;

public class ColumnHeading {
    private Widget widget;
    private String styleName;
    
    public ColumnHeading() {
        super();
    }
    
    public ColumnHeading(Widget widget) {
        this(widget, null);
    }
    
    public ColumnHeading(Widget widget, String styleName) {
        super();
        this.widget = widget;
        this.styleName = styleName;
    }
    
    public Widget getWidget() {
        return widget;
    }
    
    public void setWidget(Widget widget) {
        this.widget = widget;
    }
    
    public String getStyleName() {
        return styleName;
    }
    
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
    
    @Override
    public String toString() {
        return "ColumnHeading [styleName=" + styleName + ", widget=" + widget + "]";
    }
    
}
