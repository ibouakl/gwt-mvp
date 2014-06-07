package com.gwt.ui.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.ui.client.supertable.HTMLHelper;

public class PopupMenu extends PopupPanel {
    private PopupMenu parent = null;
    
    private VerticalPanel panel = new VerticalPanel();
    
    private PopupMenu openChildMenu = null;
    
    private ArrayList<Widget> widgets = new ArrayList<Widget>();
    
    /**
     * A constructor for this class.
     */
    public PopupMenu() {
        super(true);
        setStyleName("gwtcomp-PopupMenu");
        
        panel = new VerticalPanel();
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        setWidget(panel);
        
    }
    
    /**
     * Adds a child menu item that is a cascaded menu.
     * 
     * @param widget normally a HTML or Label object is used to print the menu item name.
     * @param cascadedMenu the popup menu that is popped up when the user mouses over the widget.
     */
    public void addMenuItem(Widget widget, final PopupMenu cascadedMenu) {
        class ItemSelectionListener implements HasMouseOutHandlers, HasMouseOverHandlers {
            private PopupMenu parent0;
            
            private FocusPanel panel0;
            
            private PopupMenu cascadedMenu0;
            
            public ItemSelectionListener(final PopupMenu parent, final FocusPanel panel, final PopupMenu cascadedMenu) {
                this.parent0 = parent;
                this.panel0 = panel;
                this.cascadedMenu0 = cascadedMenu;
                
                panel0.addMouseOverHandler(new MouseOverHandler() {
                    
                    @Override
                    public void onMouseOver(MouseOverEvent event) {
                        panel0.addStyleName("gwtcomp-PopupMenu-MenuItem-MouseIn");
                        
                        // hide the previous cascaded menu
                        if (parent0.openChildMenu != null) {
                            parent0.openChildMenu.hide();
                            parent0.openChildMenu = null;
                        }
                        
                        // open the cascaded menu if one is available
                        if (cascadedMenu0 != null) {
                            int top = event.getRelativeY(event.getRelativeElement());
                            int left = event.getRelativeX(event.getRelativeElement());
                            cascadedMenu0.setPopupPosition(left, top);
                            cascadedMenu0.show();
                            parent0.openChildMenu = cascadedMenu0;
                        }
                        
                    }
                });
                
                panel0.addMouseOutHandler(new MouseOutHandler() {
                    
                    @Override
                    public void onMouseOut(MouseOutEvent event) {
                        panel0.removeStyleName("gwtcomp-PopupMenu-MenuItem-MouseIn");
                        
                    }
                });
                
            }
            
            @Override
            public void fireEvent(GwtEvent<?> arg0) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
                return addDomHandler(handler, MouseOutEvent.getType());
            }
            
            @Override
            public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
                return addDomHandler(handler, MouseOverEvent.getType());
            }
            
        }
        
        HorizontalPanel w = new HorizontalPanel();
        w.addStyleName("gwtcomp-PopupMenu-MenuItem");
        w.setWidth("100%");
        w.add(widget);
        w.setCellHorizontalAlignment(widget, HasHorizontalAlignment.ALIGN_LEFT);
        w.setCellVerticalAlignment(widget, HasVerticalAlignment.ALIGN_TOP);
        
        // add spacing
        w.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
        
        if (cascadedMenu != null) {
            Image im = new Image(GWT.getModuleBaseURL() + "gwtcomp-icons/right-arrow.png");
            w.add(im);
            w.setCellHorizontalAlignment(im, HasHorizontalAlignment.ALIGN_RIGHT);
            w.setCellVerticalAlignment(im, HasVerticalAlignment.ALIGN_MIDDLE);
            
            cascadedMenu.parent = this;
        }
        
        FocusPanel fp = new FocusPanel(w);
        panel.add(fp);
        widgets.add(fp);
        fp.setWidth("100%");
        fp.addStyleName("gwtcomp-PopupMenu-MenuItem");
        fp.setHeight("22px");
        
        new ItemSelectionListener(this, fp, cascadedMenu);
    }
    
    /**
     * Add a child menu item.
     * 
     * @param widget normally a HTML segment or a text lable is passed but other input widgets can be passed as well.
     */
    public void addMenuItem(Widget widget) {
        addMenuItem(widget, null);
    }
    
    /**
     * Removes a child menu at the given index.
     * 
     * @param index index
     */
    public void removeMenuItem(int index) {
        widgets.remove(index);
        panel.remove(index);
    }
    
    public void removeMenuItem(Widget widget) {
        panel.remove(widget);
        widgets.remove(widget);
        
    }
    
    /**
     * Add a separator item.
     */
    public void addSeparator() {
        
        HTML html = new HTML(HTMLHelper.hr("#AAAAAA"));
        html.setHeight("22px");
        panel.add(html);
        widgets.add(html);
    }
    
    public void clearMenuItems() {
        for (Widget widget : widgets) {
            panel.remove(widget);
        }
        widgets.clear();
        
    }
    
    /**
     * Hides the popup menu. In case the menu is a cascaded menu and has parents, the parents are hidden as well.
     */
    public void hideAll() {
        this.hide();
        PopupMenu parent = this.parent;
        while (parent != null) {
            parent.hide();
            parent = parent.parent;
        }
        
        // Don't leave any menu items selected
        int numItems = panel.getWidgetCount();
        for (int i = 0; i < numItems; i++) {
            panel.getWidget(i).removeStyleName("gwtcomp-PopupMenu-MenuItem-MouseIn");
        }
    }
    
    /**
     * Gets the root menu for a given popup menu. Cascaded menus have parents and possibly grand-parents. This method iterates through the
     * chain and returns the root popup menu.
     * 
     * @param menu a given popup menu object
     * @return the root popup menu
     */
    public static PopupMenu getRootMenu(PopupMenu menu) {
        PopupMenu element = menu;
        PopupMenu parent = element.parent;
        while (parent != null) {
            element = parent;
            parent = element.parent;
        }
        
        return element;
    }
}
