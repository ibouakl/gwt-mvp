package com.gwt.ui.client.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author ibouakl
 *
 */
public class AdvanceTree extends Tree {
    
    private HashMap<String, TreePopupMenu> contextMenus = new HashMap<String, TreePopupMenu>();
    private ArrayList<AdvanceTreeListener> listeners = new ArrayList<AdvanceTreeListener>();
    private boolean multiSelectionEnabled = false;
    private ArrayList<TreeItem> selectedItems = new ArrayList<TreeItem>();
    private String selectedLevel = "";
    
    /**
     * A constructor for this class.
     */
    public AdvanceTree() {
        super();
        init();
    }
    
    /**
     * A constructor for this class.
     */
    public AdvanceTree(Tree.Resources resources) {
        super(resources);
        init();
    }
    
    protected static native void preventBrowserContextMenu(JavaScriptObject element)
    /*-{
     element.oncontextmenu = function() 
     {
     return false;
     }
     }-*/;
    
    /**
     * Note that all the items in the tree are converted to GWT HTML object before adding to the tree. Therefore, the GWT Tree.getText()
     * method will return a null.
     */
    public TreeItem addItem(String itemText) {
        HTML html = new HTML(itemText);
        preventBrowserContextMenu(html.getElement());
        return super.addItem(html);
    }
    
    /**
     * Add a AdvanceTree object to the root of the tree.
     * 
     * @param item item to add.
     */
    public void addItem(AdvanceTreeItem item) {
        super.addItem(item);  
        preventBrowserContextMenu(item.getElement());
    }
    
    public void addItem(TreeItem item) {
        super.addItem(item);
        preventBrowserContextMenu(item.getElement());
    }
    
    public TreeItem addItem(Widget widget) {
        preventBrowserContextMenu(widget.getElement());
        return super.addItem(widget);
    }
    
    /**
     * Add a tree listener.
     * 
     * @param listener the listener object.
     */
    public void addTreeListener(AdvanceTreeListener listener) {
        listeners.add(listener);
    }
    
    /**
     * @param item a tree item.
     * @return the level
     */
    public static String getLevel(TreeItem item) {
        ArrayList<Integer> items = new ArrayList<Integer>();
        do {
            if (item instanceof AdvanceTreeItem) {
                items.add(new Integer(((AdvanceTreeItem)item).getType()));
            } else {
                items.add(new Integer(0));
            }
            
            item = item.getParentItem();
        } while (item != null);
        
        StringBuffer buffer = new StringBuffer();
        Collections.reverse(items);
        
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                buffer.append('.');
            }
            
            buffer.append(((Integer)items.get(i)).toString());
        }
        return buffer.toString();
    }
    
    /**
     * Note that if multi-selection is enabled, this method returns the item selected first. For trees with multi-selection, the
     * getSelectedItems() method must be used.
     */
    public TreeItem getSelectedItem() {
        if (selectedItems.size() == 0) {
            return null;
        }  
        return selectedItems.get(0);
    }
    
    /**
     * Returns the selected items.
     * 
     * @return selected items.
     */
    public TreeItem[] getSelectedItems() {
        TreeItem[] items = convertSelectedToArray();
        return items;
    }
    
    /**
     * Returns if multi-selection is enabled.
     * 
     * @return Returns the multiSelectionEnabled.
     */
    public boolean isMultiSelectionEnabled() {
        return multiSelectionEnabled;
    }
    
    /**
     * Remove the context menu at a given level.
     * 
     * @param level
     */
    public void removeContextMenu(String level) {
        contextMenus.remove(level);
    }
    
    /**
     * Removes the tree listener.
     * 
     * @param listener listener to remove.
     */
    public void removeTreeListener(AdvanceTreeListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Sets the context menu for a given level.
     * 
     * @param level the level to set
     * @param contextMenu the context menu object.
     */
    public void setContextMenu(String level, TreePopupMenu contextMenu) {
        contextMenus.put(level, contextMenu);
    }
    
    /**
     * Enables or disables multi-selection.
     * 
     * @param multiSelectionEnabled The multiSelectionEnabled to set.
     */
    public void setMultiSelectionEnabled(boolean multiSelectionEnabled) {
        this.multiSelectionEnabled = multiSelectionEnabled;
    }
    
    public void setSelectedItem(TreeItem item) {
        setSelectedItem(item, false);
    }
    
    public void setSelectedItem(TreeItem item, boolean fireEvents) {
        if (item == null) {
            super.setSelectedItem(item, fireEvents);
            return;
        }
        
        if (fireEvents) {
            super.setSelectedItem(item, fireEvents);
        } else {
            processItemSelected(item, false);
        }
    }
    
    private TreeItem[] convertSelectedToArray() {
        TreeItem[] items = new TreeItem[selectedItems.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = selectedItems.get(i);
        }
        return items;
    }
    
    private void init() {
        addStyleName("AdvanceTree");
        initTreeListener();
    }
    
    private void initTreeListener() {
        
        
   
        
        addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                processItemSelected(event.getSelectedItem(), true);
            }
        });
        
        addOpenHandler(new OpenHandler<TreeItem>() {
            @Override
            public void onOpen(OpenEvent<TreeItem> event) {
                for (int i = 0; i < listeners.size(); i++) {
                    AdvanceTreeListener listener = listeners.get(i);
                    listener.onExpand(event.getTarget());
                }
            }
        });
        
        addCloseHandler(new CloseHandler<TreeItem>() {
            @Override
            public void onClose(CloseEvent<TreeItem> event) {
                for (int i = 0; i < listeners.size(); i++) {
                    AdvanceTreeListener listener = listeners.get(i);
                    listener.onCollapse(event.getTarget());
                    
                }
            }
        });
        addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                if (event.getNativeEvent().getButton() == Event.BUTTON_RIGHT) {
                    if (getSelectedItems() != null && getSelectedItems().length > 0) {
                        String level = getLevel(getSelectedItems()[0]);
                        TreePopupMenu menu = contextMenus.get(level);
                        if (menu != null) {
                            popup(getSelectedItems()[0], menu, level);
                        }
                    }
                    
                }
                
            }
        });
        
        addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                if (event.getNativeEvent().getKeyCode() == Event.BUTTON_RIGHT) {
                    
                }
                
            }
        });
        
    }
    
    private void popup(TreeItem parent, TreePopupMenu menu, String level) {
        if (listeners.size() > 0) {
            TreeItem[] items = convertSelectedToArray();
            for (int i = 0; i < listeners.size(); i++) {
                AdvanceTreeListener listener = listeners.get(i);
                listener.onPopup(level, menu, items);
            }
        }
        menu.setPopupPosition(parent.getAbsoluteLeft(), parent.getAbsoluteTop());
        menu.show();
        
    }
    
    private void processItemSelected(TreeItem item, boolean fireEvents) {
        String level = getLevel(item);
        if (processSelection(item, level) == false) {
            for (int i = 0; i < listeners.size(); i++) {
                AdvanceTreeListener listener = listeners.get(i);
                listener.onDeselect(item);
            }
            return; // an items has been unselected, no need to take any further action
        }
        
        if (fireEvents) {
            TreeItem[] items = convertSelectedToArray();
            for (int i = 0; i < listeners.size(); i++) {
                AdvanceTreeListener listener = listeners.get(i);
                listener.onSelect(level, items);
            }
        }
        
    }
    
    private boolean processSelection(TreeItem item, String level) {
        if (multiSelectionEnabled) {
            if (selectedItems.contains(item)) {
                selectedItems.remove(item);
                item.getWidget().removeStyleName("AdvanceTree-SelectedItem");
                return false;
            }
            if (!level.equals(selectedLevel)) {
                unselectItems();
            }
            selectItem(item, level);
            return true;
            
        } else {
            unselectItems();
            selectItem(item, level);
            return true;
        }
    }
    
    private void selectItem(TreeItem item, String level) {
        selectedItems.add(item);
        selectedLevel = level;
        item.getWidget().addStyleName("AdvanceTree-SelectedItem");
    }
    
    private void unselectItems() {
        for (int i = 0; i < selectedItems.size(); i++) {
            TreeItem el = selectedItems.get(i);
            el.getWidget().removeStyleName("AdvanceTree-SelectedItem");
        }
        selectedItems.clear();
        selectedLevel = "";
    }
    
}
