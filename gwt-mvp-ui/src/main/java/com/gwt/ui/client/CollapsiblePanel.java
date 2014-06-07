package com.gwt.ui.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasAnimation;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class CollapsiblePanel extends Composite implements HasChangeHandlers, HasWidgets, HasAnimation {
    /**
     * {@link CollapsiblePanel} styles.
     */
    public static class Styles {
        static String DEFAULT = "gwt-CollapsiblePanel";
        static String right = "gwt-CollapsiblePanel-right";
        static String CONTAINER = "container";
        static String HOVER_BAR = "hover-bar";
    }
    
    /**
     * Current {@link CollapsiblePanel} state.
     */
    enum State {
        WILL_HIDE, HIDING, IS_HIDDEN, WILL_SHOW, SHOWING, IS_SHOWN, EXPANDED;
        
        public static void checkTo(State from, State to) {
            boolean valid = check(from, to);
            if (!valid) {
                throw new IllegalStateException(from + " -> " + to + " is an illegal state transition");
            }
        }
        
        private static boolean check(State from, State to) {
            
            if (to == EXPANDED || from == null) {
                return true;
            }
            switch (from) {
                case WILL_HIDE:
                    return (to == IS_SHOWN || to == HIDING);
                case HIDING:
                    return (to == IS_HIDDEN || to == SHOWING);
                case IS_HIDDEN:
                    return (to == WILL_SHOW);
                case WILL_SHOW:
                    return (to == SHOWING || to == IS_HIDDEN);
                case SHOWING:
                    return (to == IS_SHOWN || to == HIDING);
                case IS_SHOWN:
                    return to == WILL_HIDE;
                case EXPANDED:
                    return to == HIDING;
                default:
                    throw new IllegalStateException("Unknown state");
            }
        }
        
        public boolean shouldHide() {
            return this == SHOWING || this == IS_SHOWN;
        }
        
        public boolean shouldShow() {
            return this == HIDING || this == IS_HIDDEN;
        }
    }
    
    /**
     * Delays showing of the {@link CollapsiblePanel}.
     */
    private class DelayHide extends Timer {
        
        public void activate() {
            setState(State.WILL_HIDE);
            delayedHide.schedule(getDelayBeforeHide());
        }
        
        @Override
        public void run() {
            hide();
        }
    }
    
    /**
     * Delays showing of the {@link CollapsiblePanel}.
     */
    private class DelayShow extends Timer {
        
        public void activate() {
            setState(State.WILL_SHOW);
            delayedShow.schedule(getDelayBeforeShow());
        }
        
        @Override
        public void run() {
            show();
        }
    }
    
    private class HidingAnimation extends Animation {
        @Override
        public void onCancel() {
        }
        
        @Override
        public void onComplete() {
            setPanelPos(0);
            setState(State.IS_HIDDEN);
        }
        
        @Override
        public void onStart() {
        }
        
        @Override
        public void onUpdate(double progress) {
            int slide = (int)(startFrom - (progress * startFrom));
            setPanelPos(slide);
        }
    }
    
    private class ShowingAnimation extends Animation {
        
        @Override
        public void onCancel() {
            // Do nothing, must now be hiding.
        }
        
        @Override
        public void onComplete() {
            setPanelPos(maxOffshift);
            setState(State.IS_SHOWN);
        }
        
        @Override
        public void onStart() {
        }
        
        @Override
        public void onUpdate(double progress) {
            int pos = (int)((double)(maxOffshift - startFrom) * progress);
            setPanelPos(pos + startFrom);
        }
    }
    
    private boolean animate = true;
    
    /**
     * Number of intervals used to display panel.
     */
    private int timeToSlide = 150;
    
    /**
     * How many milliseconds to delay a hover event before executing it.
     */
    private int delayBeforeShow = 300;
    
    /**
     * How many milliseconds to delay a hide event before executing it.
     */
    private int delayBeforeHide = 200;
    
    private State state;
    
    private ShowingAnimation overlayTimer = new ShowingAnimation();
    
    private HidingAnimation hidingTimer = new HidingAnimation();
    
    private DelayShow delayedShow = new DelayShow();
    
    private DelayHide delayedHide = new DelayHide();
    
    private int width;
    private int maxOffshift;
    private int currentOffshift;
    private int startFrom;
    private Panel container;
    private SimplePanel hoverBar;
    private ToggleButton collapseToggle;
    private AbsolutePanel master;
    private Widget contents;
    private boolean atRight = false;
    private HandlerRegistration handlerRegistration;
    private List<ChangeHandler> changeHandlers = new ArrayList<ChangeHandler>();
    
    /**
     * Constructor.
     */
    
    public CollapsiblePanel() {
        this(false);
    }
    
    public CollapsiblePanel(boolean atRight) {
        this.atRight = atRight;
        // Create the composite widget.
        master = new AbsolutePanel() {
            {
                sinkEvents(Event.ONMOUSEOUT | Event.ONMOUSEOVER);
            }
            
            @Override
            public void onBrowserEvent(Event event) {
                // Cannot handle browser events until contents are initialized.
                if (contents == null) {
                    return;
                }
                if (!CollapsiblePanel.this.collapseToggle.isDown()) {
                    switch (DOM.eventGetType(event)) {
                        case Event.ONMOUSEOUT:
                            Element to = DOM.eventGetToElement(event);
                            if (to == null && state == State.SHOWING) {
                                // Linux hosted mode hack.
                                return;
                            }
                            if (to != null && DOM.isOrHasChild(master.getElement(), to)) {
                                break;
                            }
                            switch (state) {
                                case WILL_SHOW:
                                    setState(State.IS_HIDDEN);
                                    delayedShow.cancel();
                                    break;
                                case SHOWING:
                                    hide();
                                    break;
                                case IS_SHOWN:
                                    delayedHide.activate();
                                    break;
                            }
                            break;
                        case Event.ONMOUSEOVER:
                            if (state == State.WILL_HIDE) {
                                setState(State.IS_SHOWN);
                                delayedHide.cancel();
                            }
                            break;
                    }
                    super.onBrowserEvent(event);
                }
            }
        };
        
        DOM.setStyleAttribute(master.getElement(), "overflow", "visible");
        initWidget(master);
        
        if (!atRight) {
            setStyleName(Styles.DEFAULT);
            
        } else {
            setStyleName(Styles.right);
            
        }
        
        // Create hovering container.
        // Create the composite widget.
        hoverBar = new SimplePanel() {
            {
                sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEDOWN);
            }
            
            @Override
            public void onBrowserEvent(Event event) {
                // Cannot handle browser events until contents are initialized.
                if (contents == null) {
                    return;
                }
                if (!CollapsiblePanel.this.collapseToggle.isDown()) {
                    switch (DOM.eventGetType(event)) {
                        case Event.ONMOUSEOVER:
                            switch (state) {
                                case HIDING:
                                    show();
                                    break;
                                case IS_HIDDEN:
                                    delayedShow.activate();
                                    break;
                            }
                            break;
                        case Event.ONMOUSEDOWN:
                            setCollapsedState(false, true);
                    }
                }
            }
        };
        
        hoverBar.setStyleName(Styles.HOVER_BAR);
        master.add(hoverBar, 0, 0);
        
        // Create the contents container.
        container = new SimplePanel();
        container.setStyleName(Styles.CONTAINER);
        master.add(container, 0, 0);
        setState(State.EXPANDED);
    }
    
    /**
     * Constructor.
     */
    public CollapsiblePanel(Widget contents) {
        this();
        initContents(contents);
    }
    
    public void add(Widget w) {
        initContents(w);
    }
    
    public void clear() {
        throw new IllegalStateException("Collapsible Panel cannot be cleared once initialized");
    }
    
    public Widget getContents() {
        return contents;
    }
    
    /**
     * Gets the delay before hiding.
     * 
     * @return the delayBeforeHide
     */
    public int getDelayBeforeHide() {
        return delayBeforeHide;
    }
    
    /**
     * Gets the delay before showing the panel.
     * 
     * @return the delayBeforeShow
     */
    public int getDelayBeforeShow() {
        return delayBeforeShow;
    }
    
    /**
     * Gets the time to slide the panel out.
     * 
     * @return the timeToSlide
     */
    public int getTimeToSlide() {
        return timeToSlide;
    }
    
    /**
     * Hides the panel when the panel is the collapsible state. Does nothing if the panel is expanded or not attached.
     */
    public void hide() {
        if (getState() != State.EXPANDED && isAttached()) {
            hiding();
        }
    }
    
    /**
     * Uses the given toggle button to control whether the panel is collapsed or not.
     */
    public void hookupControlToggle(ToggleButton button) {
        this.collapseToggle = button;
        collapseToggle.setDown(!this.isCollapsed());
        collapseToggle.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setCollapsedState(!CollapsiblePanel.this.collapseToggle.isDown(), true);
                
            }
        });
        
    }
    
    public boolean isAnimationEnabled() {
        return animate;
    }
    
    /**
     * Is the panel currently in its collapsed state.
     */
    public boolean isCollapsed() {
        return getState() != State.EXPANDED;
    }
    
    public boolean remove(Widget w) {
        return false;
    }
    
    public void setAnimationEnabled(boolean enable) {
        animate = enable;
    }
    
    /**
     * Sets the state of the collapsible panel.
     * 
     * @param collapsed is the panel collapsed?
     */
    public void setCollapsedState(boolean collapsed) {
        setCollapsedState(collapsed, false);
    }
    
    /**
     * Sets the state of the collapsible panel.
     * 
     * @param collapsed is the panel collapsed?
     * @param fireEvents should the change listeners be fired?
     */
    public void setCollapsedState(boolean collapsed, boolean fireEvents) {
        if (isCollapsed() == collapsed) {
            return;
        }
        if (collapseToggle != null) {
            collapseToggle.setDown(!collapsed);
        }
        if (collapsed) {
            becomeCollapsed();
            
        } else {
            becomeExpanded();
            
        }
        
        if (fireEvents) {
            addChangeHandler(new ChangeHandler() {
                
                @Override
                public void onChange(ChangeEvent arg0) {
                    // TODO Auto-generated method stub
                    
                }
            });
            
        }
        
    }
    
    /**
     * Sets the delay before the collapsible panel hides the panel after the user leaves the panel.
     * 
     * @param delayBeforeHide the delayBeforeHide to set
     */
    public void setDelayBeforeHide(int delayBeforeHide) {
        this.delayBeforeHide = delayBeforeHide;
    }
    
    /**
     * Set delay before showing the panel after the user has hovered over the hover bar.
     * 
     * @param delayBeforeShow the delayBeforeShow to set
     */
    public void setDelayBeforeShow(int delayBeforeShow) {
        this.delayBeforeShow = delayBeforeShow;
    }
    
    /**
     * Sets the contents of the hover bar.
     */
    public void setHoverBarContents(Widget bar) {
        hoverBar.setWidget(bar);
    }
    
    /**
     * Sets the time to slide the panel out.
     * 
     * @param timeToSlide the timeToSlide to set
     */
    public void setTimeToSlide(int timeToSlide) {
        this.timeToSlide = timeToSlide;
    }
    
    @Override
    public void setWidth(String width) {
        if (contents == null) {
            throw new IllegalStateException("Cannot set the width of the collapsible panel before its contents are initialized");
        }
        contents.setWidth(width);
        refreshWidth();
    }
    
    /**
     * Shows the panel if the panel is in a collapsible state. Does nothing if the panel is expanded or not attached. Note: this method
     * should only be called if the user's mouse will be over the panel's contents after show() is executed.
     */
    public void show() {
        if (getState() != State.EXPANDED && isAttached()) {
            cancelAllTimers();
            setState(State.SHOWING);
            startFrom = currentOffshift;
            overlayTimer.run(this.getTimeToSlide());
        }
    }
    
    /**
     * Display this panel in its collapsed state. The panel's contents will be hidden and only the hover var will be visible.
     */
    protected void becomeCollapsed() {
        cancelAllTimers();
        // Now hide.
        if (isAttached()) {
            adjustmentsForCollapsedState();
            hiding();
        } else {
            // onLoad will ensure this is correctly displayed. Here we just ensure
            // that the panel is the correct final hidden state. Forcing the state to
            // is hidden regardless of what is was.
            state = State.IS_HIDDEN;
        }
        
        DOM.setStyleAttribute(container.getElement(), "visibility", "hidden");
        for(ChangeHandler changeHandler:changeHandlers)
            changeHandler.onChange(null);
    }
    
    /**
     * Display this panel in its expanded state. The panel's contents will be fully visible and take up all required space.
     */
    protected void becomeExpanded() {
        cancelAllTimers();
        // The master width needs to be readjusted back to it's original size.
        if (isAttached()) {
            adjustmentsForExpandedState();
        }
        setState(State.EXPANDED);
        DOM.setStyleAttribute(container.getElement(), "visibility", "visible");
        for(ChangeHandler changeHandler:changeHandlers)
            changeHandler.onChange(null);
    }
    
    /**
     * This method is called immediately after a widget becomes attached to the browser's document.
     */
    @Override
    protected void onLoad() {
        if (contents != null) {
            refreshWidth();
        }
    }
    
    protected void setPanelPos(int pos) {
        currentOffshift = pos;
        if (!atRight) {
            DOM.setStyleAttribute(container.getElement(), "left", pos - width + "px");
        } else {
            DOM.setStyleAttribute(container.getElement(), "right", pos - width + "px");
            DOM.setStyleAttribute(container.getElement(), "left", "");
           
        }
        
        if (pos - width < 0)
            DOM.setStyleAttribute(container.getElement(), "visibility", "hidden");
        else
            DOM.setStyleAttribute(container.getElement(), "visibility", "visible");
        
        
     
        
    }
    
    /**
     * Visible for testing.
     * 
     * @return the hover bar component
     */
    public SimplePanel getHoverBar() {
        return hoverBar;
    }
    
    /**
     * Visible for testing. Note that the state is completely internal to the implementation.
     * 
     * @return the current state
     */
    State getState() {
        return state;
    }
    
    private void adjustmentsForCollapsedState() {
        int hoverBarWidth = hoverBar.getOffsetWidth();
        int aboutHalf = (hoverBarWidth / 2) + 1;
        int newWidth = width + aboutHalf;
        maxOffshift = newWidth;
        
        // Width is now hoverBarWidth.
        master.setWidth(hoverBarWidth + "px");
        
        // clean up state.
        currentOffshift = width;
    }
    
    private void adjustmentsForExpandedState() {
        master.setWidth(width + "px");
        
        if (!atRight) {
            DOM.setStyleAttribute(container.getElement(), "left", "0px");
        } else {
            DOM.setStyleAttribute(container.getElement(), "right", "0px");
            DOM.setStyleAttribute(container.getElement(), "left", "");
           
        }
        
    }
    
    private void cancelAllTimers() {
        delayedHide.cancel();
        delayedShow.cancel();
        overlayTimer.cancel();
        hidingTimer.cancel();
    }
    
    private void hiding() {
        assert (isAttached());
        cancelAllTimers();
        setState(State.HIDING);
        startFrom = currentOffshift;
        hidingTimer.run(timeToSlide);
    }
    
    /**
     * Initialize the panel's contents.
     * 
     * @param contents contents
     */
    private void initContents(Widget contents) {
        if (this.contents != null) {
            throw new IllegalStateException("Contents have already be set");
        }
        
        this.contents = contents;
        container.add(contents);
        
        if (isAttached()) {
            refreshWidth();
        }
    }
    
    private void refreshWidth() {
        // Now include borders into master.
        width = container.getOffsetWidth();
        if (width == 0) {
            throw new IllegalStateException("The underlying content width cannot be 0. Please ensure that the .container css style has a fixed width");
        }
        if (getState() == State.EXPANDED) {
            adjustmentsForExpandedState();
            
        } else {
            cancelAllTimers();
            adjustmentsForCollapsedState();
            // we don't know if we just moved the mouse outside of the
            setPanelPos(0);
            state = State.IS_HIDDEN;
        }
    }
    
    private void setState(State state) {
        // checks are assuming animation.
        if (isAnimationEnabled()) {
            State.checkTo(this.state, state);
        }
        
        this.state = state;
    }
    
    @Override
    public Iterator<Widget> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public HandlerRegistration addChangeHandler(ChangeHandler arg0) {        
        changeHandlers.add(arg0);  
        if(handlerRegistration!=null)
            handlerRegistration.removeHandler();
        handlerRegistration = addDomHandler(arg0, ChangeEvent.getType());
            
        return handlerRegistration;
    }
    
}
