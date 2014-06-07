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
public class DeleteActionButton extends SpanPanel implements HasClickHandlers {
    
    private String id;
    private HandlerRegistration clickHandler;
    private int row, col;
    private Anchor delete;
    
    public DeleteActionButton() {
        this(null);
    }
    
    public DeleteActionButton(String id) {
        super();
        this.id = id;
        init();
        
    }
    
    /**
     * Init the actions button
     */
    private void init() {
        // Delete button
        delete = new Anchor();
        delete.setHref("#");
        delete.addStyleName("delete_icon");
        delete.setTitle("Supprimer");
        this.add(delete);
        
    }
    
    public Anchor getDelete() {
        return delete;
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        if (clickHandler != null) clickHandler.removeHandler();
        clickHandler = addDomHandler(handler, ClickEvent.getType());
        
        return clickHandler;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getRow() {
        return row;
    }
    
    public void setRow(int row) {
        this.row = row;
    }
    
    public int getCol() {
        return col;
    }
    
    public void setCol(int col) {
        this.col = col;
    }
    
}
