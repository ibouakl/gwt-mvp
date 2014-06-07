package com.gwt.ui.client.tree;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.ui.client.AdvanceHorizontalPanel;

/**
 * @author ibouakl
 */
public class TreeNodeWidget extends FlowPanel {
    
    public TreeNodeWidget(ImageResource imageResource, Widget widget) {
        super();
        AdvanceHorizontalPanel advanceHorizontalPanel = new AdvanceHorizontalPanel();
        add(advanceHorizontalPanel);
        if (imageResource != null)
            advanceHorizontalPanel.addLeft(new Image(imageResource));
        advanceHorizontalPanel.addLeft(widget);
        
    }
    
}
