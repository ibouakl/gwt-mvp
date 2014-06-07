package com.gwt.ui.client.tree;

import com.google.gwt.user.client.ui.TreeItem;
import com.gwt.ui.client.PopupMenu;

/**
 * @author ibouakl
 */
public interface AdvanceTreeListener {
    /**
     * This method is invoked just before a popup menu is popped up. The application can hide menu items, add new menu items, etc. by
     * implementing this method.
     * 
     * @param level the selected level.
     * @param menu the menu that will be popped up.
     * @param treeItems selected tree items.
     */
    public void onPopup(String level, PopupMenu menu, TreeItem[] treeItems);
    
    /**
     * Invoked when one or more tree items are selected. Note that this method is only invoked, when there is no popup menu associated with
     * the element and there is no popup menu associated with the level.
     * 
     * @param level level of the item
     * @param treeItems selected tree items
     */
    public void onSelect(String level, TreeItem[] treeItems);
    
    /**
     * Invoked when we deselect a item return the current item has been deselected
     * 
     * @param item
     */
    public void onDeselect(TreeItem item);
    
    /**
     * This method is invoked when a tree item is expanded.
     * 
     * @param item the tree item.
     */
    public void onExpand(TreeItem item);
    
    /**
     * This method is invoked when a tree item is collapsed.
     * 
     * @param item the tree item.
     */
    public void onCollapse(TreeItem item);
}
