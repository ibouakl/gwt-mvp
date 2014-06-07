package com.gwt.ui.client.tree;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author ibouakl
 *
 */
public class AdvanceTreeItem extends TreeItem {
    
    private TreePopupMenu contextMenu;
    private int type;
    private Object userObject;
    
    /**
     * A constructor for this class
     */
    public AdvanceTreeItem() {
        super();
        this.type = 1;
    }
    
    /**
     * A constructor for this class.
     * 
     * @param widget widget rendition of the tree item.
     */
    public AdvanceTreeItem(Widget widget) {
        super(widget);
        this.type = 1;
    }
    
    /**
     * Note that all the items in the tree are converted to GWT HTML object before adding to the tree.
     */
    public AdvanceTreeItem addItem(String itemText) {
        HTML html = new HTML(itemText);
        AdvanceTreeItem advanceTreeItem = new AdvanceTreeItem(html);
        return addItem(advanceTreeItem);
    }
    
    /**
     * Add a Advance tree object as a child of this tree element.
     * 
     * @param item item to add.
     */
    public AdvanceTreeItem addItem(AdvanceTreeItem item) {
        super.addItem(item);
        calculateLevel(this, item);
        AdvanceTree.preventBrowserContextMenu(item.getElement());
        return item;
    }
    
    /**
     * calculate level for each tree item on the tree
     * 
     * @param parent
     * @param child
     */
    private void calculateLevel(AdvanceTreeItem parent, AdvanceTreeItem child) {
        child.setType(child.getType() + parent.getType());
        if (child.getChildCount() > 0) {
            for (int i = 0, n = child.getChildCount(); i < n; i++) {
                if (child.getChild(i) instanceof AdvanceTreeItem)
                    calculateLevel(parent, (AdvanceTreeItem)child.getChild(i));
            }
        }
    }
        
    /**
     * Gets the context menu for this element. If an element-specific context menu is not set, a null is returned.
     * 
     * @return Returns the contextMenu.
     */
    public TreePopupMenu getContextMenu() {
        return contextMenu;
    }
    
    /**
     * Returns the type of the object.
     * 
     * @return Returns the type.
     */
    public int getType() {
        return type;
    }
    
    /**
     * Sets an item-specific context menu. This will override the level-specific context menu.
     * 
     * @param contextMenu The contextMenu to set.
     */
    public void setContextMenu(TreePopupMenu contextMenu) {
        this.contextMenu = contextMenu;
    }
    
    public void setHTML(String html) {
        HTML h = new HTML(html);
        AdvanceTreeItem advanceTreeItem = new AdvanceTreeItem(h);
        addItem(advanceTreeItem);
        AdvanceTree.preventBrowserContextMenu(advanceTreeItem.getElement());
    }
    
    public void setText(String text) {
        HTML h = new HTML(text);
        AdvanceTreeItem advanceTreeItem = new AdvanceTreeItem(h);
        addItem(advanceTreeItem);
        AdvanceTree.preventBrowserContextMenu(advanceTreeItem.getElement());
    }
    
    /**
     * Sets the type of this object.
     * 
     * @param type The type to set.
     */
    public void setType(int type) {
        this.type = type;
    }
    
    public void setWidget(Widget newWidget) {
        AdvanceTree.preventBrowserContextMenu(newWidget.getElement());
        super.setWidget(newWidget);
    }
    
    public Object getUserObject() {
        return userObject;
    }
    
    /**
     * store any user object for the Tree item
     */
    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }
    
}
