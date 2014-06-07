package com.gwt.ui.client.toolbar;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class Toolbar extends Composite {
    
    private AbsolutePanel panel;
    private int horizontalSpacing = 10;
    private int verticalSpacing = 5;
    private ArrayList<Widget> widgets = new ArrayList<Widget>();
    
    /**
     * Constructor
     */
    public Toolbar() {
        panel = new AbsolutePanel();
        initWidget(panel);
        panel.addStyleName("Toolbar");
    }
    
    /**
     * Adds a widget into the toolbar.
     * 
     * @param w widget to add.
     */
    public void addWidget(Widget w) {
        widgets.add(w);
        panel.add(w);
        
        if (w instanceof Image) {
            w.addStyleName("Toolbar-Image");
        }
    }
    
    /**
     * Redraw the toolbar. This is needed in some cases when the size of the tools widgets changes.
     */
    public void redraw() {
        int left = horizontalSpacing;
        int top = verticalSpacing;
        int max = 0;
        
        int size = widgets.size();
        for (int i = 0; i < size; i++) {
            Widget w = widgets.get(i);
            panel.setWidgetPosition(w, left, top);
            left += w.getOffsetWidth() + horizontalSpacing;
            
            if (w.getOffsetHeight() > max) {
                max = w.getOffsetHeight();
            }
        }
        
        setHeight((max + verticalSpacing + verticalSpacing) + "px");
        setWidth(left + horizontalSpacing + "px");
        
    }
    
    /**
     * Returns the horizontal spacing.
     * 
     * @return Returns the horizontalSpacing.
     */
    public int getHorizontalSpacing() {
        return horizontalSpacing;
    }
    
    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }
    
    /**
     * Returns the vertical spacing.
     * 
     * @return Returns the verticalSpacing.
     */
    public int getVerticalSpacing() {
        return verticalSpacing;
    }
    
    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }
    
}
