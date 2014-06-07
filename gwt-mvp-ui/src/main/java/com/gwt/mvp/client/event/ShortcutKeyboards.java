package com.gwt.mvp.client.event;

/**
 * @author ibouakl
 *
 */
public enum ShortcutKeyboards {
 
    Esc, CtrlS,CtrlUp,CtrlDown,ShiftUp,ShiftDown;
    
    /**
     * @return Associated Shortcut 
     */
    public String getType() {
        return name().toLowerCase();
    }
}
