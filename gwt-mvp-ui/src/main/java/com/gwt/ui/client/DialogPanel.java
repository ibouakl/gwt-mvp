package com.gwt.ui.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

public class DialogPanel extends PopupPanel implements ProvidesResize, MouseMoveHandler, MouseUpHandler, MouseDownHandler {
    
    private static final int DRAG_ZONE_WIDTH = 5;
    
    enum DragZoneType {
        
        NONE("default"),

        MOVE("move"),

        RESIZE_E("e-resize"),

        RESIZE_W("w-resize"),

        RESIZE_S("s-resize"),

        RESIZE_N("n-resize"),

        RESIZE_NE("ne-resize"),

        RESIZE_NW("nw-resize"),

        RESIZE_SE("se-resize"),

        RESIZE_SW("sw-resize");
        
        private String cursor;
        
        DragZoneType(String cursor) {
            this.cursor = cursor;
        }
        
        public String getCursor() {
            return cursor;
        }
    }
    
    private final DockPanel container;
    
    private Widget contentWidget;
    
    private int clientWindowLeft;
    
    private int clientWindowRight;
    
    private int clientWindowTop;
    
    private int clientWindowBottom;
    
    private HandlerRegistration resizeHandlerRegistration;
    
    private final CaptionPanel captionPanel;
    
    private boolean dragging = false;
    
    private DragZoneType dragZoneType;
    
    private int dragStartX;
    
    private int dragStartY;
    
    private int dragStartLeft;
    
    private int dragStartTop;
    
    private int dragStartWidth;
    
    private int dragStartHeight;
    
    public DialogPanel(boolean autoHide, boolean modal) {
        super(autoHide, modal);
        
        getElement().getStyle().setProperty("zIndex", "20");
        
        getElement().getStyle().setProperty("padding", DRAG_ZONE_WIDTH + "px");
        
        updateClientWindowPosition();
        
        container = new DockPanel();
        DOM.setStyleAttribute(container.getElement(), "cursor", "default");
        
        captionPanel = new CaptionPanel();
        container.add(captionPanel, DockPanel.NORTH);
        
        setWidget(container);
        
        addDomHandler(this, MouseMoveEvent.getType());
        addDomHandler(this, MouseUpEvent.getType());
        addDomHandler(this, MouseDownEvent.getType());
        
    }
    
    public void setContentWidget(Widget widget) {
        
        if (contentWidget != null) {
            container.remove(contentWidget);
        }
        
        contentWidget = widget;
        
        container.add(contentWidget, DockPanel.CENTER);
        contentWidget.setSize("100%", "100%");
        
        container.setCellHeight(contentWidget, "100%");
        
    }
    
    public void setCaption(String caption) {
        captionPanel.setHTML(caption);
    }
    
    private void updateClientWindowPosition() {
        clientWindowLeft = Document.get().getScrollLeft();
        clientWindowRight = clientWindowLeft + Window.getClientWidth();
        clientWindowTop = Document.get().getScrollTop();
        clientWindowBottom = clientWindowTop + Window.getClientHeight();
    }
    
    @Override
    public void show() {
        updateClientWindowPosition();
        if (resizeHandlerRegistration == null) {
            resizeHandlerRegistration = Window.addResizeHandler(new ResizeHandler() {
                public void onResize(ResizeEvent event) {
                    updateClientWindowPosition();
                }
            });
        }
        super.show();
    }
    
    @Override
    protected void onPreviewNativeEvent(NativePreviewEvent event) {
        // We need to preventDefault() on mouseDown events (outside of the
        // DialogBox content) to keep text from being selected when it
        // is dragged.
        NativeEvent nativeEvent = event.getNativeEvent();
        
        EventTarget target = nativeEvent.getEventTarget();
        if (target != null && target.equals(this) && !event.isCanceled() && (event.getTypeInt() == Event.ONMOUSEDOWN)) {
            nativeEvent.preventDefault();
        }
        super.onPreviewNativeEvent(event);
    }
    
    protected void beginDragging(MouseDownEvent event) {
        dragging = true;
        dragZoneType = getDragZoneType(event);
        DOM.setCapture(getElement());
        dragStartX = event.getX() + getAbsoluteLeft();
        dragStartY = event.getY() + getAbsoluteTop();
        dragStartLeft = getAbsoluteLeft();
        dragStartTop = getAbsoluteTop();
        dragStartWidth = getOffsetWidth();
        dragStartHeight = getOffsetHeight();
    }
    
    /**
     * That simple override cancels the inherited event previewing and simply allows all mouse
     * events to pass through to other widgets in the application.
     */
    public boolean onEventPreview(Event event) {
   
        Event currentEvent = Event.getCurrentEvent();
        
        EventTarget target = currentEvent.getEventTarget();
        if (target != null && !target.equals(this)) {
            currentEvent.stopPropagation();
        }
        
        return true;
        
    }
    
    protected void continueDragging(MouseMoveEvent event) {
        if (dragging) {
            int absX = event.getX() + getAbsoluteLeft();
            int absY = event.getY() + getAbsoluteTop();
            
            // if the mouse is off the screen to the left, right, or top, don't
            // move or resize the dialog box.
            if (absX < clientWindowLeft || absX >= clientWindowRight || absY < clientWindowTop || absY >= clientWindowBottom) {
                return;
            }
            
            int width = dragStartWidth;
            int height = dragStartHeight;
            int left = dragStartLeft;
            int top = dragStartTop;
            
            switch (dragZoneType) {
                case RESIZE_E:
                    width = dragStartWidth - dragStartX + absX;
                    break;
                case RESIZE_W:
                    width = dragStartWidth + dragStartX - absX;
                    left = dragStartLeft - dragStartX + absX;
                    break;
                case RESIZE_S:
                    height = dragStartHeight - dragStartY + absY;
                    break;
                case RESIZE_N:
                    height = dragStartHeight + dragStartY - absY;
                    top = dragStartTop - dragStartY + absY;
                    break;
                case RESIZE_SE:
                    width = dragStartWidth - dragStartX + absX;
                    height = dragStartHeight - dragStartY + absY;
                    break;
                case RESIZE_SW:
                    width = dragStartWidth + dragStartX - absX;
                    height = dragStartHeight - dragStartY + absY;
                    left = dragStartLeft - dragStartX + absX;
                    break;
                case RESIZE_NE:
                    width = dragStartWidth - dragStartX + absX;
                    height = dragStartHeight + dragStartY - absY;
                    top = dragStartTop - dragStartY + absY;
                    break;
                case RESIZE_NW:
                    width = dragStartWidth + dragStartX - absX;
                    height = dragStartHeight + dragStartY - absY;
                    left = dragStartLeft - dragStartX + absX;
                    top = dragStartTop - dragStartY + absY;
                    break;
                case MOVE:
                    left = dragStartLeft - dragStartX + absX;
                    top = dragStartTop - dragStartY + absY;
                    break;
                default:
                    break;
            }
            
            setPixelSize(width - 2 * DRAG_ZONE_WIDTH, height - 2 * DRAG_ZONE_WIDTH);
            setPopupPosition(left, top);
            
            if (contentWidget instanceof RequiresResize) {
                ((RequiresResize)contentWidget).onResize();
            }
            
        }
    }
    
    protected void endDragging(MouseUpEvent event) {
        dragging = false;
        dragZoneType = DragZoneType.NONE;
        DOM.releaseCapture(getElement());
    }
    
    @Override
    public void onMouseMove(MouseMoveEvent event) {
        if (dragging) {
            continueDragging(event);
        } else {
            DragZoneType dragZoneType = getDragZoneType(event);
            setCursor(dragZoneType);
        }
    }
    
    @Override
    public void onMouseDown(MouseDownEvent event) {
        if (!DragZoneType.NONE.equals(getDragZoneType(event))) {
            beginDragging(event);
        }
    }
    
    @Override
    public void onMouseUp(MouseUpEvent event) {
        if (dragging) {
            endDragging(event);
        }
    }
    
    private void setCursor(DragZoneType dragZoneType) {
        DOM.setStyleAttribute(this.getElement(), "cursor", dragZoneType.getCursor());
    }
    
    private DragZoneType getDragZoneType(MouseEvent<?> mouseEvent) {
        if (captionPanel.equals(mouseEvent.getSource())) {
            return DragZoneType.MOVE;
        } else {
            int eventY = mouseEvent.getY() + getAbsoluteTop();
            int boxY = this.getAbsoluteTop();
            int height = this.getOffsetHeight();
            
            int eventX = mouseEvent.getX() + getAbsoluteLeft();
            int boxX = this.getAbsoluteLeft();
            int width = this.getOffsetWidth();
            
            int y = eventY - boxY;
            int x = eventX - boxX;
            if (y <= DRAG_ZONE_WIDTH) {
                if (x <= DRAG_ZONE_WIDTH) {
                    return DragZoneType.RESIZE_NW;
                } else if (x >= width - DRAG_ZONE_WIDTH) {
                    return DragZoneType.RESIZE_NE;
                } else {
                    return DragZoneType.RESIZE_N;
                }
            } else if (y >= height - DRAG_ZONE_WIDTH) {
                if (x <= DRAG_ZONE_WIDTH) {
                    return DragZoneType.RESIZE_SW;
                } else if (x >= width - DRAG_ZONE_WIDTH) {
                    return DragZoneType.RESIZE_SE;
                } else {
                    return DragZoneType.RESIZE_S;
                }
            } else {
                if (x <= DRAG_ZONE_WIDTH) {
                    return DragZoneType.RESIZE_W;
                } else if (x >= width - DRAG_ZONE_WIDTH) {
                    return DragZoneType.RESIZE_E;
                } else {
                    return DragZoneType.NONE;
                }
            }
        }
        
    }
    
    class CaptionPanel extends HTML {
        
        public CaptionPanel() {
            setWordWrap(false);
            this.getStyleElement().addClassName("captionPanel-dialog");
            setHeight("22px");
            DOM.setStyleAttribute(this.getElement(), "cursor", "move");
            
            addDomHandler(DialogPanel.this, MouseMoveEvent.getType());
            addDomHandler(DialogPanel.this, MouseUpEvent.getType());
            addDomHandler(DialogPanel.this, MouseDownEvent.getType());
            
        }
        
    }
    
}
