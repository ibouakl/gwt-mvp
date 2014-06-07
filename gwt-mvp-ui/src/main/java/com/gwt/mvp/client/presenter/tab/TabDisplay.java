package com.gwt.mvp.client.presenter.tab;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.gwt.mvp.client.Display;

/**
 * <code>TabDisplay</code> interface declare method to manage a tab mechanism.
 * 
 */
public interface TabDisplay extends Display {
    
    /**
     * Adds display.
     */
    public void add(final Display tab, final Display child);
    
    /**
     * Remove display.
     * 
     * @param child
     * @return
     */
    public boolean remove(Display child);
    
    /**
     * @return number of current children.
     */
    public int getDisplayCount();
    
    /**
     * @param child
     * @return index of this display, -1 if display in not in this tab.
     */
    public int getDisplayIndex(final Display child);
    
    /**
     * @return selected display index.
     */
    public int getSelectedDisplayIndex();
    
    /**
     * Select display.
     * 
     * @param tabIndex start from 0 to getCount() -1
     */
    public void selectDisplay(final int tabIndex) throws ArrayIndexOutOfBoundsException;
    
    /**
     * @return an instance of <code>HasSelectionHandlers<Integer> </code> if selection event is managed, null if not.
     */
    public HasSelectionHandlers<Integer> getSelectionHandlers();
    
    public void setTabText(int tabindex, String text);
    
}
