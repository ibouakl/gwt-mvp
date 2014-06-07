package com.gwt.ui.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.gwt.ui.client.toolbar.RichTextToolbar;

/**
 * 
 * @author ibouakl
 *
 */
public class AdvancedRichText extends FlowPanel {

	public AdvancedRichText() {
        RichTextArea area = new RichTextArea();
        area.setSize("100%", "14em");
        RichTextToolbar     toolbar = new RichTextToolbar(area);
        toolbar.setWidth("100%");
        add(toolbar);
        add(area);
	}
}
