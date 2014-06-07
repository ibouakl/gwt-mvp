package com.gwt.ui.client.datagrid;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author ibouakl
 */
public class DataGridColumn {

    /** identifier */
    private String key;
    /** column name */
    private String name;
    /** column index */
    private int index;
    /** visible */
    private boolean visible;
    /** is selected */
    private boolean isSelected;
    /** Width of column */
    private String width;
    /** widget */
    private Widget widget;
    private Element th;

    public DataGridColumn(String key, String name, int index) {
        this(key, name, index, true);
    }

    public DataGridColumn(String key, String name, int index, boolean isVisible) {
        this(key, name, index, isVisible, true);
    }

    public DataGridColumn(String key, String name, int index, boolean isVisible, boolean isSelected) {
        this(key, name, index, isVisible, isSelected, null);
    }

    public DataGridColumn(String key, String name, int index, boolean isVisible, boolean isSelected, String width) {
        this.key = key;
        this.name = name;
        this.index = index;
        this.visible = isVisible;
        this.isSelected = isSelected;
        this.width = width;
        this.widget = (name != null ? new Label(name) : new Label(""));
    }

    public DataGridColumn(String key, Widget widget, int index, boolean isVisible, boolean isSelected, String width) {
        this.key = key;
        this.name = null;
        this.index = index;
        this.visible = isVisible;
        this.isSelected = isSelected;
        this.width = width;
        this.widget = widget;
    }

    /**
     * Getter for property 'name'.
     * 
     * @return Value for property 'name'.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for property 'name'.
     * 
     * @param name Value to set for property 'name'.
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Widget getWidget() {
        return widget;
    }

    public String getKey() {
        return key;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
        if (th != null) {
            if (!visible) {
                th.addClassName("hide");
            } else {
                th.removeClassName("hide");
            }

        }
    }

    public void setTh(Element th) {
        this.th = th;
    }

    public Element getTh() {
        return th;
    }
}
