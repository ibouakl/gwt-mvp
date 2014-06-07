package com.gwt.mvp.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author ibouakl
 *
 */
public class ShortcutKeyboardsEvent extends GwtEvent<ShortcutKeyboardsHander> {
    
    public static final Type<ShortcutKeyboardsHander> TYPE = new Type<ShortcutKeyboardsHander>();
    private ShortcutKeyboards shortcutKeyboards;
    public ShortcutKeyboardsEvent(ShortcutKeyboards shortcutKeyboards) {
        super();
        this.shortcutKeyboards = shortcutKeyboards;
    }
    
    @Override
    protected void dispatch(final ShortcutKeyboardsHander handler) {
        handler.onShortcutKeyboards(shortcutKeyboards);
    }
    
    @Override
    public Type<ShortcutKeyboardsHander> getAssociatedType() {
        return TYPE;
    }
    
}
