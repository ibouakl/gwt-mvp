package com.gwt.mvp.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @author ibouakl
 *
 */
public interface ShortcutKeyboardsHander extends EventHandler {
    public void onShortcutKeyboards(final ShortcutKeyboards shortcutKeyboards);
}
