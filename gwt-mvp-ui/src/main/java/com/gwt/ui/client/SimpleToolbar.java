package com.gwt.ui.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.ui.client.button.AnchorButton;

public class SimpleToolbar extends Composite {
    private AbsolutePanel panel;
    
    private int horizontalSpacing = 10;
    
    private int verticalSpacing = 5;
    
    private ArrayList<Widget> list = new ArrayList<Widget>();
    
    /**
     * A constructor for this class.
     */
    public SimpleToolbar() {
        panel = new AbsolutePanel();
        initWidget(panel);
        panel.addStyleName("SimpleToolbarPanel");
    }
    
    /**
     * Adds a "tool" widget into the toolbar.
     * 
     * @param w widget to add.
     */
    public void addWidget(Widget w) {
        list.add(w);
        panel.add(w);
        
        if (w instanceof Image || w instanceof AnchorButton) {
            w.addStyleName("SimpleToolbar-Image");
        }
    }
    
    
    
    
    /**
     * Returns the horizontal spacing in pixels.
     * 
     * @return Returns the horizontalSpacing.
     */
    public int getHorizontalSpacing() {
        return horizontalSpacing;
    }
    
    /**
     * Sets the horizontal spacing between the tools in the toolbar.
     * 
     * @param horizontalSpacing The horizontalSpacing to set.
     */
    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }
    
    /**
     * Returns the vertical spacing (top and bottom).
     * 
     * @return Returns the verticalSpacing.
     */
    public int getVerticalSpacing() {
        return verticalSpacing;
    }
    
    /**
     * Sets the vertical spacing.
     * 
     * @param verticalSpacing The verticalSpacing to set.
     */
    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }
    
    /**
     * Redraw the toolbar. This is needed in some cases when the size of the tools widgets changes.
     */
    public void redraw() {
        int left = horizontalSpacing;
        int top = verticalSpacing;
        int max = 0;
        
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Widget w = list.get(i);
            panel.setWidgetPosition(w, left, top);
            left += w.getOffsetWidth() + horizontalSpacing;
            
            if (w.getOffsetHeight() > max) {
                max = w.getOffsetHeight();
            }
        }
        
        setHeight((max + verticalSpacing + verticalSpacing) + "px");
        setWidth(left + horizontalSpacing + "px");
    }
}
