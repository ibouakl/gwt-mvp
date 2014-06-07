package com.gwt.ui.client.button;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.gwt.ui.client.ULPanel;

/**
 * @author ibouakl
 */
public class MenuButton extends FlowPanel {
    private ULPanel listItem;
    private Anchor fosuedAnchor;
    
    public MenuButton() {
        super();
        this.addStyleName("menuButton");
        listItem = new ULPanel();
        this.add(listItem);
        listItem.addStyleName("group");
        
    }
    
    public Anchor addAnchor(String text, String href, ClickHandler handler) {
        final Anchor anchor = new Anchor(text, href);
        anchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
              setFocus(anchor);                
            }
        });
        
        if (handler != null) {
            anchor.addClickHandler(handler);
        }
        listItem.addItem(anchor, "menuButtonItem");
        return anchor;
        
    }
    
    public void setVisible(int anchorIndex,boolean isVisible){
        listItem.getWidget(anchorIndex).setVisible(isVisible);
        
    }
    
    public void setFocus(Anchor anchor){
        if (fosuedAnchor != null)
            fosuedAnchor.removeStyleName("focus");
        fosuedAnchor = anchor;
        anchor.addStyleName("focus");
    }
    
}
