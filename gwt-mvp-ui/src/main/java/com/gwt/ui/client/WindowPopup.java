package com.gwt.ui.client;

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.ui.client.resources.Images;

/**
 * @author jguibert
 */
public class WindowPopup extends DialogBox {
    
    private FlowPanel content;
    private CaptionTitle captionTitle;
    private Image closer;
    private ClickHandler closeHandler;
    private ClickHandler saveHandler;
    private FlowPanel captionPanel;
    private boolean isCtrl = false;
    
    public WindowPopup() {
        super(false, true);
        
        Element td01 = getCellElement(0, 1);
        Widget caption = (Widget)this.getCaption();
        DOM.removeChild(td01, caption.getElement());
        
        captionPanel = new FlowPanel();
        captionPanel.addStyleName("Caption-Panel");
        DOM.appendChild(td01, captionPanel.getElement());
        adopt(captionPanel);
        
        FlowPanel closePanel = new FlowPanel();
        closePanel.addStyleName("Close-Panel");
        closer = new Image(Images.IMAGES.closeWindowIcon());
        closer.addStyleName("Close-Button");
        closePanel.add(closer);
        captionPanel.add(closePanel);
        
        this.captionTitle = new CaptionTitle();
        captionTitle.addStyleName("Caption-Title");
        captionPanel.add(this.captionTitle);
        
        content = new FlowPanel();
        setWidget(content);
        
    }
    
    static protected boolean isWidgetEvent(NativeEvent event, Widget w) {
        EventTarget target = event.getEventTarget();
        if (Element.is(target)) {
            boolean t = w.getElement().isOrHasChild(Element.as(target));
            return t;
        }
        return false;
    }
    
    @Override
    public void onBrowserEvent(Event event) {
        if (isWidgetEvent(event, closer)) {
            switch (event.getTypeInt()) {
                case Event.ONMOUSEUP:
                case Event.ONCLICK:
                    if (closeHandler != null)
                        closeHandler.onClick(null);
                    break;
                
            }
            
            return;
        }
        super.onBrowserEvent(event);
    }
    
    @Override
    protected void onPreviewNativeEvent(NativePreviewEvent event) {
        super.onPreviewNativeEvent(event);
        switch (event.getTypeInt()) {
            case Event.ONKEYDOWN:

                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE)
                    if (closeHandler != null)
                        closeHandler.onClick(null);
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_CTRL) {
                    isCtrl = true;
                }
                if (event.getNativeEvent().getKeyCode() == 83 && isCtrl) {
                    if (saveHandler != null)
                        saveHandler.onClick(null);
                    event.cancel();
                }
                break;
            case Event.ONKEYUP:
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_CTRL) {
                    isCtrl = false;
                }
                break;
            default:
                break;
        }
        
    }
    
    public FlowPanel getContent() {
        return content;
    }
    
    public void setCaption(String caption) {
        captionTitle.setText(caption);
    }
    
    public void addClosehandler(ClickHandler clickHandler) {
        this.closeHandler = clickHandler;
        
    }
    
    public void addSaveHandler(ClickHandler clickHandler) {
        saveHandler = clickHandler;
    }
    
    private class CaptionTitle extends HTML implements Caption {
    }
}
