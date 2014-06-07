package com.gwt.ui.client.supertable;

import java.util.Comparator;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

/**
 * 
 * @author ibouakl
 *
 */
public class ColumnProperty {
    private String header;
    
    private Comparator<Widget[]> ascendingSortComparator;
    
    private Comparator<Widget[]> descendingSortComparator;
    
    private boolean displayed = true;
    
    private boolean selected = false;
    
    private boolean addToColumnsSelection;
    
    private String width;
    
    private HorizontalAlignmentConstant horizontalAlignment = HasHorizontalAlignment.ALIGN_LEFT;
    
    private VerticalAlignmentConstant verticalAlignment = HasVerticalAlignment.ALIGN_TOP;
    
    /**
     * A constructor for this class.
     * 
     * @param header column header.
     * @param ascendingSortComparator ascending comparator class for the column. If this value is set to null, the "ascending sort" icon is
     *            not displayed.
     * @param descendingSortComparator descending comparator class for the column. If this value is set to null, the "descending sort" icon
     *            is not displayed.
     */
    public ColumnProperty(String header, Comparator<Widget[]> ascendingSortComparator, Comparator<Widget[]> descendingSortComparator) {
       this(header,ascendingSortComparator,descendingSortComparator,true);
        
    }
    
    public ColumnProperty(String header, Comparator<Widget[]> ascendingSortComparator, Comparator<Widget[]> descendingSortComparator,boolean addToColumnsSelection) {
        this.header = header;
        this.ascendingSortComparator = ascendingSortComparator;
        this.descendingSortComparator = descendingSortComparator;
        this.addToColumnsSelection = addToColumnsSelection;
    }
    
    /**
     * Returns the ascendingSortComparator.
     * 
     * @return returns the ascending comparator object
     */
    public Comparator<Widget[]> getAscendingSortComparator() {
        return ascendingSortComparator;
    }
    
    /**
     * Returns the column header.
     * 
     * @return returns the header
     */
    public String getHeader() {
        return header;
    }
    
    /**
     * Returns the descendingSortComparator.
     * 
     * @return returns the descending comparator object
     */
    public Comparator<Widget[]> getDescendingSortComparator() {
        return descendingSortComparator;
    }
    
    public boolean isDisplayed() {
        return displayed;
    }
    
    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
    
    /**
     * @return Returns the width.
     */
    public String getWidth() {
        return width;
    }
    
    /**
     * @param width The width to set.
     */
    public void setWidth(String width) {
        this.width = width;
    }
    
    /**
     * @return Returns the selected.
     */
    public boolean isSelected() {
        return selected;
    }
    
    /**
     * @param selected The selected to set.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    /**
     * @return Returns the horizontalAlignment.
     */
    public HorizontalAlignmentConstant getHorizontalAlignment() {
        return horizontalAlignment;
    }
    
    /**
     * @param horizontalAlignment The horizontalAlignment to set.
     */
    public void setHorizontalAlignment(HorizontalAlignmentConstant horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }
    
    /**
     * @return Returns the verticalAlignment.
     */
    public VerticalAlignmentConstant getVerticalAlignment() {
        return verticalAlignment;
    }
    
    /**
     * @param verticalAlignment The verticalAlignment to set.
     */
    public void setVerticalAlignment(VerticalAlignmentConstant verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public boolean isAddToColumnsSelection() {
        return addToColumnsSelection;
    }

    public void setAddToColumnsSelection(boolean addToColumnsSelection) {
        this.addToColumnsSelection = addToColumnsSelection;
    }
    
}
