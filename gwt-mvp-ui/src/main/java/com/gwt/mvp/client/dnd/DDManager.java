package com.gwt.mvp.client.dnd;

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 */
public interface DDManager extends HasValueChangeHandlers<DragInformation> {

    public void clearRegistration(final String groupName);

    public HandlerRegistration registerDrag(final HasMouseDownHandlers handle, final Widget description, final Clipboard clipboard, final String groupName);

    public HandlerRegistration registerDrop(final HasMouseUpHandlers handle, final Clipboard clipboard, final String groupName);
}
