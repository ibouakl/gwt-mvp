package com.gwt.ui.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Realy Simple Accordion panel with the ability to open/close the nested widgets, preferably in a cool animated style.
 * 
 * @author ibouakl
 */
public class AccordionPanel extends Composite {
    
    private Panel aPanel;
    private String animField;
    private String animBounds;
    
    private static int NUM_FRAMES = 8;
    
    private Widget currentlyExpanded = null;
    private Label currentlyExpandedLabel = null;
    
    /**
     * Constructor
     * 
     * @param horizontal
     */
    public AccordionPanel(boolean horizontal) {
        if (horizontal) {
            aPanel = new HorizontalPanel();
            animField = "width";
            animBounds = "scrollWidth";
        } else {
            aPanel = new VerticalPanel();
            animField = "height";
            animBounds = "scrollHeight";
        }
        initWidget(aPanel);
        
        setStylePrimaryName("accordion");
    }
    
    public AccordionPanel() {
        this(false);
    }
    
    /**
     * method for adding new widgets to an accordion.
     * 
     * @param label
     * @param content
     */
    public void add(String label, final Widget content, boolean isExpanded) {
        final Label l = new Label(label);
        l.setStylePrimaryName(getStylePrimaryName() + "-title");
        final SimplePanel sp = new SimplePanel();
        sp.setWidget(content);
        
        l.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent arg0) {
                expand(l, sp);
                
            }
        });
        
        aPanel.add(l);
        sp.setStylePrimaryName(getStylePrimaryName() + "-content");
        DOM.setStyleAttribute(sp.getElement(), animField, "0px");
        DOM.setStyleAttribute(sp.getElement(), "overflow", "hidden");
        aPanel.add(sp);
        if (isExpanded)
            expand(l, sp);
    }
    
    private void expand(final Label label, final Widget c) {
        
        if (currentlyExpanded != null)
            DOM.setStyleAttribute(currentlyExpanded.getElement(), "overflow", "hidden");
        
        // We create a Timer, whose run method will animate the collapse/expansion. We simultaneously collapse any currently expanded Widget
        // and expand the target widget. All we have to do, is fetch the max dimensions from scrollHeight/scrollWidth, and then interpolate
        // them down to zero for collapse.
        
        final Timer t = new Timer() {
            int frame = 0;
            
            public void run() {
                if (currentlyExpanded != null) {
                    Widget w = currentlyExpanded;
                    Element elem = w.getElement();
                    int oSh = DOM.getElementPropertyInt(elem, animBounds);
                    DOM.setStyleAttribute(elem, animField, "" + ((NUM_FRAMES - frame) * oSh / NUM_FRAMES) + "px");
                    
                }
                
                if (currentlyExpanded != c) {
                    Widget w = c;
                    Element elem = w.getElement();
                    int oSh = DOM.getElementPropertyInt(elem, animBounds);
                    DOM.setStyleAttribute(elem, animField, "" + (frame * oSh / NUM_FRAMES) + "px");
                }
                frame++;
                
                if (frame <= NUM_FRAMES) {
                    schedule(10);
                } else {
                    if (currentlyExpanded != null) {
                        currentlyExpanded.removeStyleDependentName("selected");
                        currentlyExpandedLabel.removeStyleDependentName("selected");
                    }
                    c.addStyleDependentName("selected");
                    if (currentlyExpanded != c) {
                        currentlyExpanded = c;
                        currentlyExpandedLabel = label;
                        currentlyExpandedLabel.addStyleDependentName("selected");
                        Element elem = c.getElement();
                        DOM.setStyleAttribute(elem, "overflow", "auto");
                        DOM.setStyleAttribute(elem, animField, "auto");
                    } else {
                        currentlyExpanded = null;
                    }
                    
                }
                
            }
        };
        t.schedule(10);
    }
}
