package com.gwt.ui.client.button;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;

public class HyperlinkButton extends Anchor {
    String disabledHTML = "<table class='HyperlinkButton-disabled' cellpadding='0' cellspacing='0'>" + "					<tbody><tr>" + "					<td class='HyperlinkButton-left-disabled'><img src='images/HyperlinkButton-spacer.gif' width='6' border='0' height='1'></td>"
                          + "					<td class='HyperlinkButton-bg-disabled'>{TEXT}</td>" + "					<td class='HyperlinkButton-right-disabled'><img src='images/HyperlinkButton-spacer.gif' width='6' border='0' height='1'></td>" + "					</tr></tbody></table>";
    String enabledHTML = "<table class='HyperlinkButton' cellpadding='0' cellspacing='0'>" + "					<tbody><tr>" + "					<td class='HyperlinkButton-left'><img src='images/HyperlinkButton-spacer.gif' width='6' border='0' height='1'></td>"
                         + "					<td class='HyperlinkButton-bg'>{TEXT}</td>" + "					<td class='HyperlinkButton-right'><di><img src='images/HyperlinkButton-spacer.gif' width='6' border='0' height='1'></td>" + "					</tr></tbody></table>";
    
    private String text;
    
    private boolean enabled;
    
    private ClickHandler handler;
    
    private HandlerRegistration handlerRegistration;
    
    public HyperlinkButton(String text) {
        this(text, true, null);
    }
    
    public HyperlinkButton(String text, boolean enabled) {
        this(text, enabled, null);
    }
    
    public HyperlinkButton(String text, ClickHandler handler) {
        this(text, true, handler);
    }
    
    public HyperlinkButton(String text, boolean enabled, ClickHandler handler) {
        this.enabled = enabled;
        this.text = text;
        this.handler = handler;
        
        updateGUI();
    }
    
    @Override
    public void setText(String text) {
        this.text = text;
        updateGUI();
    }
    
    /**
     * Return button status
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * Change button status
     */
    public void setEnabled(boolean enabled) {
        if (enabled == this.enabled)
            return;
        this.enabled = enabled;
        updateGUI();
        
    }
    
    /**
     * Used to update UI after status was changed
     */
    protected void updateGUI() {
        String html;
        if (enabled) {
            html = enabledHTML.replace("{TEXT}", text);
        } else {
            html = disabledHTML.replace("{TEXT}", text);
        }
        
        setHTML(html);
        
        if (enabled && handler != null) {
            handlerRegistration = addClickHandler(handler);
        } else {
            if (handler != null)
                handlerRegistration.removeHandler();
        }
    }

    public String getText() {
        return text;
    }
}
