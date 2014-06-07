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
public class PublishActionButton extends SpanPanel implements HasClickHandlers {
    private String id;
    private String title;
    private Boolean lock;
    private HandlerRegistration clickHandler;
    
    public PublishActionButton(String id) {
        this(id, new Boolean(false));
    }
    
    public PublishActionButton(String id, String title) {
        this(id, title, new Boolean(false));
    }
    
    public PublishActionButton(String id, Boolean lock) {
        super();
        this.id = id;
        this.lock = lock;
        this.title = "Publier";
        init();
    }
    
    public PublishActionButton(String id, String title, Boolean lock) {
        super();
        this.id = id;
        this.lock = lock;
        this.title = title;
        init();
    }
    
    /**
     * Init the actions button
     */
    private void init() {
        
        // publish button
        Anchor publishUrl = new Anchor();
        publishUrl.setHref("#");
        if (lock == null || !lock.booleanValue()) {
            publishUrl.addStyleName("publish_icon");
            publishUrl.setTitle(title);
        } else {
            publishUrl.addStyleName("lockPublish_icon");
            publishUrl.setTitle("Publication verrouillée");
        }
        
        this.add(publishUrl);
        
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

    /**
     * @return the lock
     */
    public Boolean getLock() {
        return lock;
    }
}
