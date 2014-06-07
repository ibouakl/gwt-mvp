package com.gwt.ui.client.masterview;

import com.google.gwt.user.client.ui.Widget;

/**
 * This class represents the column of the @link Grid.
 * @author ibouakl
 */
public class Column {

    protected String propertyName;
    protected String title;
    protected String width;
    protected boolean sortable;
    protected boolean hideFiltre;
    protected Widget widget = null;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    /**
     * @param propertyName the name of the bean's property to display
     * @param title the title of the column
     * @param width the width of the column
     * @param sortable
     * @param hideFilter hide the TextBox filter
     */
    public Column(String propertyName, String title, boolean sortable, String width, boolean hideFilter) {
        this.propertyName = propertyName;
        this.title = title;
        this.sortable = sortable;
        this.width = width;
        this.hideFiltre = hideFilter;
    }

    /**
     * @param propertyName the name of the bean's property to display
     * @param title the title of the column
     * @param width the width of the column
     * @param sortable
     */
    public Column(String propertyName, String title, boolean sortable, String width) {
        this.propertyName = propertyName;
        this.title = title;
        this.sortable = sortable;
        this.width = width;
        this.hideFiltre = false;
    }

    public Column(String propertyName, String title, boolean sortable) {
        this.propertyName = propertyName;
        this.title = title;
        this.sortable = sortable;
        this.width = "";
    }

    public Column(String propertyName, String title) {
        this.propertyName = propertyName;
        this.title = title;
        this.sortable = true;
        this.width = "";
    }

    public Column(String propertyName) {
        this.propertyName = propertyName;
        setTitle(createTitleFromPropertyName(propertyName));
        this.sortable = true;
        this.width = "";

    }

    protected String createTitleFromPropertyName(String propertyName) {
        if (propertyName.length() == 0) {
            return "";
        }

        String firstLetterCapitalized = propertyName.substring(0, 1).toUpperCase();
        String otherLetters = propertyName.substring(1, propertyName.length() - 1);

        String title = firstLetterCapitalized + otherLetters;
        return title;
    }

    /**
     * Set Widget representation
     * @param widget
     */
    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    /**
     * Get Widget
     * @return
     */
    public Widget getWidget() {
        return widget;
    }

    public boolean isWidget() {
        return widget != null;
    }
}
