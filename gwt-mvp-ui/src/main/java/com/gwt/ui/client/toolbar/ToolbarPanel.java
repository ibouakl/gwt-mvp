package com.gwt.ui.client.toolbar;

import java.util.ArrayList;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.ui.client.resources.Images;

public class ToolbarPanel extends AbsolutePanel {
    private ArrayList<Toolbar> elements = new ArrayList<Toolbar>();
    
    private ArrayList<ToolbarSplitterInfo> splitters = new ArrayList<ToolbarSplitterInfo>();
    
    private boolean dragged = false;
    
    private ToolbarSplitterInfo draggedToolbar;
    
    /**
     * A constructor for this class.
     */
    public ToolbarPanel() {
        setStyleName("ToolbarPanel");
        setWidth("100%");
        sinkEvents(Event.MOUSEEVENTS);
    }
    
    /**
     * Add a toolbar to the panel. The toolbars are displayed in the order they are added.
     * 
     * @param bar toolbar widget
     */
    public void addToolbar(Toolbar bar) {
        if (elements.size() > 0) {
            Toolbar tb = elements.get(elements.size() - 1);
            createSplitter(tb);
        }
        
        add(bar);
        elements.add(bar);
        bar.redraw();
        populate();
    }
    
    private Image createSplitter(Toolbar toolbar) {
        Image splitter = createSplitter();
        add(splitter);
        splitters.add(new ToolbarSplitterInfo(toolbar, splitter));
        return splitter;
    }
    
    private Image createSplitter() {
        Image splitter = new Image(Images.IMAGES.spacerIcon());
        splitter.setSize("4px", "16px");
        splitter.setStyleName("ToolbarPanel-Splitter");
        return splitter;
    }
    

    public void onBrowserEvent(Event event) {
        switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEDOWN: {
                Element target = DOM.eventGetTarget(event);
                int size = splitters.size();
                for (int i = 0; i < size; i++) {
                    ToolbarSplitterInfo splitter = splitters.get(i);
                    Image sp = splitter.getSplitter();
                    
                    if (DOM.isOrHasChild(sp.getElement(), target)) {
                        draggedToolbar = splitter;
                        dragged = true;
                        DOM.setCapture(getElement());
                        DOM.eventPreventDefault(event);
                        
                        return;
                    }
                }
                
                dragged = false;
                break;
            }
                
            case Event.ONMOUSEUP: {
                DOM.releaseCapture(getElement());
                dragged = false;
                break;
            }
                
            case Event.ONMOUSEMOVE: {
                if (dragged) {
                    assert DOM.getCaptureElement() != null;
                    
                    Toolbar prev = draggedToolbar.getToobar();
                    
                    int x = DOM.eventGetClientX(event);
                    
                    int pw = prev.getOffsetWidth();
                    int pend = prev.getAbsoluteLeft() + pw;
                    int pchange = x - pend;
                    int pnw = pw + pchange;
                    if (pnw < 0) {
                        pnw = 0;
                    }
                    
                    int size = elements.size();
                    int[] widths = new int[size];
                    for (int i = 0; i < size; i++) {
                        Toolbar tb = elements.get(i);
                        if (tb == prev) {
                            widths[i] = pnw;
                        } else {
                            widths[i] = tb.getOffsetWidth();
                        }
                    }
                    
                    repopulate(widths);
                    
                    DOM.eventPreventDefault(event);
                }
                
                break;
            }
        }
    }
    
    private void repopulate(int[] widths) {
        int size = elements.size();
        for (int i = 0; i < size; i++) {
            Toolbar tb = elements.get(i);
            tb.setWidth(widths[i] + "px");
        }
        
        populate();
    }
    
    /**
     * Redraw the panel. Sometimes when the size of the child toolbar changes, it is necessary to draw the panel.
     */
    public void redraw() {
        int size = elements.size();
        for (int i = 0; i < size; i++) {
            Toolbar bar = elements.get(i);
            bar.redraw();
        }
        populate();
    }
    
    private void populate() {
        int max = 0;
        int left = 2;
        int top = 5;
        int size = elements.size();
        for (int i = 0; i < size; i++) {
            Widget w = elements.get(i);
            if (w.getOffsetHeight() > max) {
                max = w.getOffsetHeight();
            }
        }
        
        for (int i = 0; i < size; i++) {
            Widget w = elements.get(i);
            setWidgetPosition(w, left, top);
            w.setHeight(max + "px");
            left += w.getOffsetWidth();
            
            if (i < size - 1) {
                Image split = splitters.get(i).getSplitter();
                split.setHeight((max + top + top) + "px");
                setWidgetPosition(split, left, 0);
                left += split.getOffsetWidth();
            }
            
            left += 2;
        }
        
        setHeight((max + top + top) + "px");
    }
    
    protected void onLoad() {
        super.onLoad();
        redraw();
    }
    
    public Toolbar createToolbar(){
        Toolbar bar = new Toolbar();
        bar.setHorizontalSpacing(10);
        this. addToolbar(bar);
        return bar;
        
    }
}
