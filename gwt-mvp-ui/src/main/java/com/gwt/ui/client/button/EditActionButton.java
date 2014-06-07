package com.gwt.ui.client.button;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.gwt.ui.client.SpanPanel;

/**
 * @author ibouakl
 */
public class EditActionButton extends SpanPanel implements HasClickHandlers {
    private String id;
    private HandlerRegistration clickHandler;
    
    public EditActionButton(String id) {
        super();
        this.id = id;
        init();
    }
    
    /**
     * Init the actions button
     */
    private void init() {
        
        // publish button
        Anchor edit = new Anchor();
        edit.setHref("#");
        edit.addStyleName("editAction_icon");
        edit.setTitle("Details");
        this.add(edit);
        
    }
    
    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        if (clickHandler != null)
            clickHandler.removeHandler();
        clickHandler = addDomHandler(handler, ClickEvent.getType());
        return clickHandler;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}
