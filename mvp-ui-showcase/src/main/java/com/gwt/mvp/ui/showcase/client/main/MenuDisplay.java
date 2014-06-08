package com.gwt.mvp.ui.showcase.client.main;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.ui.client.ULPanel;
import com.gwt.ui.client.button.DockButton;

/**
 * @author ibouakl
 */
public class MenuDisplay implements MenuPresenter.MenuPresenterDisplay {
    
    private FlowPanel menuPanel;
    private FlowPanel headerPanel;
    private FlowPanel dockPanel;
    private DockButton content;
    private FlowPanel logoArea;
    private DockButton dashborad;
    private DockButton widgets;
    private InlineLabel inlineLabel;
    private DockButton currentDockButton;
    
    public void init() {
        menuPanel = new FlowPanel();
        initHeader();
        initDock();
    }
    
    private void initHeader() {
        headerPanel = new FlowPanel();
        menuPanel.add(headerPanel);
        headerPanel.addStyleName("header");
        headerPanel.addStyleName("container_16");
        
        logoArea = new FlowPanel();
        headerPanel.add(logoArea);
        logoArea.addStyleName("logo");
        logoArea.addStyleName("grid_8");
        inlineLabel = new InlineLabel("MVP && widgets showcase");
        logoArea.add(inlineLabel);
        
        FlowPanel wrap = new FlowPanel();
        headerPanel.add(wrap);
        wrap.addStyleName("grid_8");
        
    }
    
    private Widget createItemsNavigation() {
        
        ULPanel listItem = new ULPanel();
        listItem.addStyleName("group");
        
        // Dashboard button
        dashborad = new DockButton("Dashboard", "outer", "inner dashboard");
        currentDockButton = dashborad;
        dashborad.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (currentDockButton != null)
                    currentDockButton.changeParentSpanStyleName("outer");
                dashborad.changeParentSpanStyleName("outerClicked");
                currentDockButton = dashborad;
            }
        });
        listItem.addItem(dashborad, "item first");
        
        // Content button
        content = new DockButton("Contents", "outerClicked", "inner content");
    
        content.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (currentDockButton != null)
                    currentDockButton.changeParentSpanStyleName("outer");
                content.changeParentSpanStyleName("outerClicked");
                currentDockButton = content;
            }
        });
        listItem.addItem(content, "item middle");
        
        // Settings button
        widgets = new DockButton("Parameters", "outer", "inner settings");
        widgets.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (currentDockButton != null)
                    currentDockButton.changeParentSpanStyleName("outer");
                widgets.changeParentSpanStyleName("outerClicked");
                currentDockButton = widgets;
            }
        });
        
        
        listItem.addItem(widgets, "item middle");
        return listItem;
        
        
        
        
    }
    
    private void initDock() {
        dockPanel = new FlowPanel();
        dockPanel.addStyleName("dock");
        FlowPanel wrapDock = new FlowPanel();
        wrapDock.addStyleName("menu");
        wrapDock.add(createItemsNavigation());
        dockPanel.add(wrapDock);
        menuPanel.add(dockPanel);
    }
    
    public void dispose() {
        menuPanel.removeFromParent();
        menuPanel = null;
        dashborad = null;
        content = null;
        widgets = null;
        inlineLabel = null;
    }
    
    public Widget asWidget() {
        return menuPanel;
    }

    @Override
    public void addContentClickHandler(ClickHandler clickHandler) {
        content.addClickHandler(clickHandler);
    }

    @Override
    public void addDashboardClickHandler(ClickHandler clickHandler) {
        dashborad.addClickHandler(clickHandler);
        
    }
    
}
