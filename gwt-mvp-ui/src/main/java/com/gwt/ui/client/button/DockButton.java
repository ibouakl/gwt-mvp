package com.gwt.ui.client.button;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Anchor;

/**
 * Use this class to create a 2 span elements. the first is the parent and the second is the child. the result should look like this <span
 * class="styleName1"> <span class="styleName2">text</span> </span>
 * 
 * @author ibouakl
 */
public class DockButton extends Anchor {
    private String label;
    private String childSpanStyleName;
    
    public DockButton(String label, String parentSpanStyleName, String childSpanStyleName) {
        
        super("", "");
        this.label = label;
        this.childSpanStyleName = childSpanStyleName;
        this.setHref("#");
        this.setHTML("<span class=\"" + parentSpanStyleName + "\"><span class=\"" + childSpanStyleName + "\">" + label + "</span></span>");
    }
    
    /**
     * @param parentSpanStyleName the parentSpanStyleName to set
     */
    public void changeParentSpanStyleName(String parentSpanStyleName) {
        this.setHTML("<span class=\"" + parentSpanStyleName + "\"><span class=\"" + childSpanStyleName + "\">" + label + "</span></span>");
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (isAttached()) {
            onDetach();
            if (enabled) {
                sinkEvents(Event.ONCLICK);
            } else {
                unsinkEvents(Event.ONCLICK);
            }
            onAttach();
        }
    }
    
}
