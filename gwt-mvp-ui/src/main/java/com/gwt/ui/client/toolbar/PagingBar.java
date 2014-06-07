package com.gwt.ui.client.toolbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.gwt.ui.client.AdvanceHorizontalPanel;

/**
 * @author ibouakl
 */
public class PagingBar extends AdvanceHorizontalPanel {
    
    private PushButton first;
    private PushButton next;
    private PushButton prev;
    private PushButton last;
    
    /**
     * Constructor
     */
    public PagingBar() {
        super();
        
        first = new PushButton("<< Premiers");
        first.setStylePrimaryName("gic-PushButton");
        first.setTitle("Les plus r\u00e9cents");
        this.addLeft(first);
        
        prev = new PushButton(new Image(GWT.getModuleBaseURL() + "gwtcomp-icons/previous.png"));
        prev.setText("< Pr\u00e9c.");
        prev.setStylePrimaryName("gic-PushButton");
        prev.setTitle("Pr\u00e9c\u00e9dents");
        this.addLeft(prev);
        
        next = new PushButton("Suiv.>");
        next.setStylePrimaryName("gic-PushButton");
        next.setTitle("Suivant");
        this.addLeft(next);
        
        last = new PushButton("Derniers >>");
        last.setStylePrimaryName("gic-PushButton");
        last.setTitle("Les plus anciens");
        this.addLeft(last);
        
    }
    
    /**
     * set the enable status if buttons
     * 
     * @param status
     */
    public void setButtonStatus(String status) {
        first.setEnabled(status.charAt(0) == '1');
        prev.setEnabled(status.charAt(1) == '1');
        next.setEnabled(status.charAt(2) == '1');
        last.setEnabled(status.charAt(3) == '1');
    }
    
    public void setButtonsStatus(boolean enableFirst, boolean enablePrevious, boolean enableNext, boolean enableLast) {
        first.setEnabled(enableFirst);
        prev.setEnabled(enablePrevious);
        next.setEnabled(enableNext);
        last.setEnabled(enableLast);
        
    }
    
    public HasClickHandlers getFirst() {
        return first;
    }
    
    public HasClickHandlers getNext() {
        return next;
    }
    
    public HasClickHandlers getLast() {
        return last;
    }
    
    public HasClickHandlers getPrevious() {
        return prev;
    }
}
