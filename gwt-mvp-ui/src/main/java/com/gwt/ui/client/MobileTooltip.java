package com.gwt.ui.client;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class MobileTooltip extends PopupPanel {
    // tooltip class name, defined in your css file
    public static final String DEF_TOOLTIP_STYLE = "MobileTooltip";
    public static final String DEF_TOOLTIP_WIDTH = "180px";
    public static final String DEF_TOOLTIP_HEIGHT = "";
    // the distance from mouse to the tooltip
    public static final int DEF_MOUSE_OFFSET_X = 20;
    public static final int DEF_MOUSE_OFFSET_Y = 20;
    
    private HTML container;
    private int offsetX;
    private int offsetY;
    
    public MobileTooltip(String content) {
        super();
        init(content, null, null, null);
    }
    
    public MobileTooltip(String content, String style) {
        super();
        init(content, null, null, style);
    }
    
    public MobileTooltip(String content, String width, String height) {
        super();
        init(content, width, height, null);
    }
    
    public MobileTooltip(String content, String width, String height, String style) {
        super();
        init(content, width, height, style);
    }
    
    /**
     * initialization
     * 
     * @param content
     * @param width
     * @param height
     * @param style
     */
    private void init(String content, String width, String height, String style) {
        this.container = new HTML(content);
        setWidget(container);
        
        if (width == null) {
            this.setWidth(DEF_TOOLTIP_WIDTH);
        } else {
            this.setWidth(width);
        }
        
        if (height == null) {
            this.setHeight(DEF_TOOLTIP_HEIGHT);
        } else {
            this.setHeight(height);
        }
        
        if (style == null || style.length() == 0) {
            style = DEF_TOOLTIP_STYLE;
        }
        
        this.offsetX = DEF_MOUSE_OFFSET_X;
        this.offsetY = DEF_MOUSE_OFFSET_Y;
        
        setStyleName(style);
    }
    
    public HTML getContainer() {
        return container;
    }
    
    // don't process any event, let parent process it
    public boolean onEventPreview(Event event) {
        return true;
    }
    
    public int getOffsetX() {
        return offsetX;
    }
    
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    public int getOffsetY() {
        return offsetY;
    }
    
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
}
