package com.gwt.mvp.client;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * <code>EventBus</code> is an interface providing minimal access to an {@link EventHandler} manager.
 * Rather than being attached to a single object, an EventBus provides a central
 * pathway to send events across the whole application.
 * <p />
 * Add {@link EventBus#fireCurrentPlace() } method to centralize all event management.
 * 
 * @author David Peterson
 * @author jguibert
 * @author ibouakl
 */
public interface EventBus {

    /**
     * Add Handler<H>.
     * 
     * @param <H>
     * @param type
     * @param handler
     * @return The HandlerRegistration.
     */
    public <H extends EventHandler> HandlerRegistration addHandler(final Type<H> type, final H handler);

    /**
     * Fire specific event.
     * 
     * @param event the event to fire.
     */
    public void fireEvent(final GwtEvent<?> event);

    /**
     * Fire Current Place event if we have an history.
     * 
     * @return true if event has been fired.
     */
    public boolean fireCurrentPlace();

    public Session getSession();

    public Session getSession(boolean create);
}
