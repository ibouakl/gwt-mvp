package com.gwt.mvp.client.presenter.tab;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.mvp.client.Display;

/**
 * <code>DefaultTabDisplay</code> provide default implementation of<code>TabDisplay</code> interface.
 * 
 */
public class DefaultTabDisplay implements TabDisplay {
    
    private TabLayoutPanel tabLayoutPanel;
    private double tabBarSize;
    private Unit tabBarUnit;
    
    /**
     * Build a new instance of <code>DefaultTabDisplay</code>.
     */
    public DefaultTabDisplay(double tabBarSize, Unit tabBarUnit) {
        super();
        tabLayoutPanel = null;
        this.tabBarSize = tabBarSize;
        this.tabBarUnit = tabBarUnit;
    }
    
    @Override
    public void add(final Display tab, final Display child) {
        tabLayoutPanel.add(child.asWidget(), tab.asWidget());
    }
    
    @Override
    public int getDisplayCount() {
        return tabLayoutPanel != null ? tabLayoutPanel.getWidgetCount() : -1;
    }
    
    public boolean remove(Display child) {
        return tabLayoutPanel.remove(child.asWidget());
    }
    
    @Override
    public int getSelectedDisplayIndex() {
        return tabLayoutPanel != null ? tabLayoutPanel.getSelectedIndex() : -1;
    }
    
    @Override
    public void selectDisplay(final int tabIndex) {
        tabLayoutPanel.selectTab(tabIndex);
    }
    
    @Override
    public int getDisplayIndex(final Display child) {
        return tabLayoutPanel.getWidgetIndex(child.asWidget());
    }
    
    @Override
    public void init() {
        tabLayoutPanel = new TabLayoutPanel(tabBarSize, tabBarUnit);
    }
    
    @Override
    public void dispose() {
        tabLayoutPanel.clear();
        tabLayoutPanel = null;
    }
    
    @Override
    public Widget asWidget() {
        return tabLayoutPanel;
    }

    @Override
    public HasSelectionHandlers<Integer> getSelectionHandlers() {
        return tabLayoutPanel;
    }

    @Override
    public void setTabText(int tabindex, String text) {
        tabLayoutPanel.setTabText(tabindex, text);
        
    }
    
}
