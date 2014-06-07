package com.gwt.ui.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class OutlookStackPanel extends Composite {
    
    private VerticalPanel container = new VerticalPanel();
    
    private DeckPanel content = new DeckPanel();
    
    private DeckPanel header = new DeckPanel();
    
    private VerticalPanel footer = new VerticalPanel();
    
    private List<Widget> buttons = new ArrayList<Widget>();
    private List<Widget> headers = new ArrayList<Widget>();
    
    private String DEFAULT_STYLE = "OutlookStack";
    
    private String DEFAULT_HEADER_STYLE = "OutlookStackHeader";
    private String DEFAULT_HEADER_INNER_STYLE = "OutlookStackHeaderInner";
    private String DEFAULT_CONTENT_STYLE = "OutlookStackContent";
    
    private String DEFAULT_HEADER_HIGHT = "31px";
    private String DEFAULT_BUTTON_HIGHT = "31px";
    
    private String DEFAULT_WIDTH = "100%";
    
    /**
     * Creates an empty stack panel.
     */
    public OutlookStackPanel() {
        content.setAnimationEnabled(true);
        content.setWidth(DEFAULT_WIDTH);
        
        container.setStyleName(DEFAULT_STYLE);
        header.setSize(DEFAULT_WIDTH, DEFAULT_HEADER_HIGHT);
        footer.setSize(DEFAULT_WIDTH, "");
        
        ScrollPanel scroller = new ScrollPanel();
        scroller.setAlwaysShowScrollBars(false);
        scroller.add(content);
        
        container.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        container.add(header);
        container.add(scroller);
        
        container.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
        container.add(footer);
        
        content.setAnimationEnabled(true);
        
        header.setStyleName(DEFAULT_HEADER_STYLE);
        
        initWidget(container);
        
        sinkEvents(Event.ONCLICK);
        sinkEvents(Event.ONMOUSEOVER);
        sinkEvents(Event.ONMOUSEOUT);
        
    }
    
    @Override
    public void setHeight(String height) {
        content.setHeight(height);
    }
    
    /*
     * Those methods may be better to put as abstract
     */
    public Widget createHeader(String buttonName) {
        return new HTML("&nbsp;&nbsp;" + buttonName);
    }
    
    public Widget createButton(String iconUrl, String buttonName) {
        return new HTML(( iconUrl != null ? "<img src=\""+iconUrl+"\"/> " + "&nbsp;&nbsp;" : ("")) + buttonName);
    }
    
    /*
     * Main outlook stack panel methods
     */

    public void add(Widget stackContent, String iconUrl, String buttonName) {
        insert(stackContent,iconUrl, buttonName, 0); // getWidgetCount()
    }
    
    public int getWidgetCount() {
        return buttons.size();
    }
    
    public int getSelectedIndex() {
        return content.getVisibleWidget();
    }
    
    public Widget getSelectedWidget() {
        if (content.getVisibleWidget() >= 0) {
            return content.getWidget(content.getVisibleWidget());
        } else {
            return null;
        }
    }
    
    /*
     * OutlookStackPanel main implementation methods
     */

    private void insert(Widget stackContent, String iconUrl, String buttonName, int beforeIndex) {
        Widget headerElem = createHeader(buttonName);
        Widget buttonElem = createButton(iconUrl, buttonName);
        
        headers.add(headerElem);
        buttons.add(buttonElem);
        
        header.add(headerElem);
        footer.add(buttonElem);
        
        content.add(stackContent);
        stackContent.getElement().removeAttribute("style");
        
        if (headerElem != null) {
            headerElem.setStyleName(DEFAULT_HEADER_INNER_STYLE);
            headerElem.setHeight(DEFAULT_HEADER_HIGHT);
        }
        
        if (buttonElem != null) {
            buttonElem.setStyleName(DEFAULT_HEADER_STYLE);
            buttonElem.setHeight(DEFAULT_BUTTON_HIGHT);
            buttonElem.setWidth(DEFAULT_WIDTH);
            
        }
        
        if (stackContent.getStyleName() == null || ("").equals(stackContent.getStyleName().trim())) {
            stackContent.setStyleName(DEFAULT_CONTENT_STYLE);
        }
        
        content.showWidget(0);
        header.showWidget(0);
    }
    
    @Override
    public void onBrowserEvent(Event event) {
        Element target = Element.as(((NativeEvent)event).getEventTarget());
        if (DOM.eventGetType(event) == Event.ONCLICK) {
            for (Widget widget : buttons) {
                if (widget.getElement() == target) {
                    
                    buttons.get(header.getVisibleWidget()).removeStyleDependentName("selected");
                    widget.addStyleDependentName("selected");
                    
                    int index = buttons.indexOf(widget);
                    if (index >= 0) {
                        header.showWidget(index);
                        content.showWidget(index);
                    }
                }
            }
        } else if (DOM.eventGetType(event) == Event.ONMOUSEOVER) {
            for (Widget widget : buttons) {
                if (widget.getElement() == target) {
                    widget.addStyleDependentName("over");
                }
            }
        } else if (DOM.eventGetType(event) == Event.ONMOUSEOUT) {
            for (Widget widget : buttons) {
                if (widget.getElement() == target) {
                    widget.removeStyleDependentName("over");
                }
            }
        }
        super.onBrowserEvent(event);
    }
}
