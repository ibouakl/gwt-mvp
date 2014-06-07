package com.gwt.mvp.client.dnd;

// import com.allen_sauer.gwt.log.client.Log;

/**
 * @author jguibert
 * @author ibouakl
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class DDPanel extends FocusPanel implements DDManager {
    
    private boolean dragging = false;
    private Clipboard source = null;
    private DragCursor cursor;
    private HandlerRegistration hEventPere = null;
    private Map<String, List<HandlerRegistration>> handlers = new HashMap<String, List<HandlerRegistration>>();
    
    public DDPanel() {
        super();
        
        super.addMouseMoveHandler(new MouseMoveHandler() {
            
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                if (dragging) {
                    cursor.onMouseMove(event);
                    event.preventDefault();
                } else {
                    destroyCursor();
                }
            }
        });
        super.addMouseUpHandler(new MouseUpHandler() {
            
            @Override
            public void onMouseUp(MouseUpEvent event) {
                if (dragging) {
                    dragging = false;
                    cursor.onMouseOut();
                    event.preventDefault();
                } else {
                    destroyCursor();
                }
            }
        });
        super.addMouseOutHandler(new MouseOutHandler() {
            
            @Override
            public void onMouseOut(MouseOutEvent event) {
                if (dragging) {
                    dragging = false;
                    cursor.onMouseOut();
                    event.preventDefault();
                } else {
                    destroyCursor();
                }
            }
        });
    }
    
    public void expand() {
        setSize(Window.getClientWidth() - getAbsoluteLeft() + "px", Window.getClientHeight() - getAbsoluteTop() + "px");
    }
    
    @Override
    public HandlerRegistration registerDrag(final HasMouseDownHandlers handle, final Widget description, final Clipboard clipboard, final String groupName) {
        HandlerRegistration registration = handle.addMouseDownHandler(new MouseDownHandler() {
            
            @Override
            public void onMouseDown(MouseDownEvent event) {
                if (!dragging) {
                    source = clipboard;
                    dragging = true;
                    cursor = new DragCursor(description, event);
                    cursor.onMouseOver(event);
                    cursor.onMouseOut();
                    cursor.onMouseOver(event);
                    event.preventDefault();
                }
            }
        });
        if (groupName != null) {
            register(registration, groupName);
        }
        return registration;
    }
    
    @Override
    public HandlerRegistration registerDrop(final HasMouseUpHandlers handle, final Clipboard clipboard, final String groupName) {
        HandlerRegistration registration = handle.addMouseUpHandler(new MouseUpHandler() {
            
            @Override
            public void onMouseUp(MouseUpEvent event) {
                if (dragging) {
                    dragging = false;
                    destroyCursor();
                    fireEvent(clipboard);
                    event.preventDefault();
                }
            }
        });
        if (groupName != null) {
            register(registration, groupName);
        }
        return registration;
    }
    
    @Override
    public void clearRegistration(final String groupName) {
        List<HandlerRegistration> list = handlers.get(groupName);
        if (list != null) {
            for (HandlerRegistration hr : list) {
                hr.removeHandler();
            }
            handlers.remove(groupName);
        }
    }
    
    private HandlerRegistration register(final HandlerRegistration handlerRegistration, final String groupName) {
        List<HandlerRegistration> list = handlers.get(groupName);
        if (list == null) {
            list = new ArrayList<HandlerRegistration>();
            handlers.put(groupName, list);
        }
        list.add(handlerRegistration);
        return handlerRegistration;
    }
    
    /**
     * Fire a DragInformation event width memorized data
     */
    @SuppressWarnings("unchecked")
    private void fireEvent(Clipboard dropTarget) {
        if (source != null) {
            ValueChangeEvent.fire(this, new DragInformation(source, dropTarget));
            source = null;
        }
    }
    
    /**
     * Add a listener on a Drag event used when drop is finished
     * 
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<DragInformation> handler) {
        if (hEventPere != null) {
            hEventPere.removeHandler();
        }
        hEventPere = addHandler(handler, ValueChangeEvent.getType());
        return hEventPere;
    }
    
    private void destroyCursor() {
        if (cursor != null) {
            cursor.onMouseOut();
            cursor.clear();
            cursor = null;
        }
    }
    
    /**
     * Inner class to manage drag cursor
     * 
     * @author jguibert
     */
    private class DragCursor {
        
        private PopupPanel tooltip = null;
        private int lastX = 0;
        private int lastY = 0;
        private boolean isShowing = false;
        private final int dx = 5;
        private final int dy = 10;
        
        /**
         * The Constructor of the DragCursor.
         * 
         * @param text The tooltip text. It should not be null.
         */
        public DragCursor(Widget content, MouseEvent<?> event) {
            super();
            tooltip = new PopupPanel();
            tooltip.setWidget(content);
            RootPanel.get().add(tooltip, event.getClientX(), event.getClientY());
            tooltip.setAnimationEnabled(false);
            tooltip.setStyleName("DragCursor");
            tooltip.hide();
            onMouseMove(event);
        }
        
        public DragCursor(String html, MouseEvent<?> event) {
            this(new HTML(html), event);
        }
        
        public void clear() {
            isShowing = false;
            tooltip.hide();
            RootPanel.get().remove(tooltip);
        }
        
        final void onMouseMove(MouseEvent<?> event) {
            
            if (isShowing) {
                int left = event.getClientX() + Window.getScrollLeft() + dx;
                int top = event.getClientY() + Window.getScrollTop() + dy;
                if (left != lastX || top != lastY) {
                    tooltip.setPopupPosition(left, top);
                    lastX = left;
                    lastY = top;
                }
            }
        }
        
        void onMouseOver(MouseEvent<?> event) {
            if (!isShowing) {
                isShowing = true;
                onMouseMove(event);
                tooltip.show();
                DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "move");
            }
        }
        
        void onMouseOut() {
            if (isShowing) {
                isShowing = false;
                tooltip.hide();
                DOM.setStyleAttribute(RootPanel.getBodyElement(), "cursor", "default");
            }
        }
    }
    
    @Override
    public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
        
        return null;
    }
    
    @Override
    public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
        // Log.error("try adding addMouseOutHandler");
        return null;
    }
    
    @Override
    public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
        // Log.error("try adding addMouseUpHandler");
        return null;
    }
    
    /**
     * A hack for a bug in GWT with the scroll location
     */
    static native int getScrollTop()/*-{
                                    return $doc.body.scrollTop;
                                    }-*/;
    
    /**
     * A hack for a bug in GWT with the scroll location
     */
    static native int getScrollLeft()/*-{
                                     return $doc.body.scrollLeft;
                                     }-*/;
}
