package com.gwt.mvp.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Presenter;
import com.gwt.mvp.client.event.PresenterChangedEvent;
import com.gwt.mvp.client.event.PresenterRevealedEvent;
import com.gwt.mvp.client.presenter.notify.NotifyMessageEvent;

/**
 * <code>BasePresenter<code> implement all basic for each presenter.
 * <p />
 * Lazy initialization of <code>Display</code> (Based on idea found here @see http://borglin.net/gwt-project/ ).
 * <p />
 * Based on initial work of David Peterson (@see project gwt-presenter-1.1.1-replace)). Here, we add special mechanism for reveal/dispose
 * display on request.
 * 
 * 
 * @author jguibert
 * 
 */
public abstract class BasePresenter<D extends Display> implements Presenter {

    /**
     * Flag to manage display initialization. trie if the presenter is in a 'revealed' state.
     */
    private boolean revealed;
    /**
     * true if the presenter is currently in a 'bound' state.
     */
    private boolean bound;
    /**
     * The display for the presenter.
     */
    protected final D display;
    /**
     * The {@link EventBus} for the application.
     */
    protected final EventBus eventBus;
    /**
     * List of <code>HandlerRegistration</code>.
     */
    private final List<HandlerRegistration> handlerRegistrations;

    /**
     * Build a new instance of <code>BasePresenter</code>.
     * 
     * @param display
     *            display instance
     * @param eventBus
     *            event bus instance
     */
    public BasePresenter(final D display, final EventBus eventBus) {
        super();
        this.display = display;
        this.eventBus = eventBus;
        revealed = false;
        bound = false;
        handlerRegistrations = new ArrayList<HandlerRegistration>();
    }

    @Override
    public void bind() {
        if (!bound) {
            bound = true;
            onBind();
        }
    }

    @Override
    public void unbind() {
        if (bound) {
            bound = false;
            for (HandlerRegistration reg : handlerRegistrations) {
                reg.removeHandler();
            }
            handlerRegistrations.clear();
            disposeDisplay();
            onUnbind();
        }

    }

    /**
     * This method is called when binding the presenter. Any additional bindings
     * should be done here.
     */
    protected abstract void onBind();

    /**
     * This method is called when unbinding the presenter. Any handler
     * registrations recorded with {@link #registerHandler(HandlerRegistration)} will have already been removed at this point.
     */
    protected abstract void onUnbind();

    /**
     * Any {@link HandlerRegistration}s added will be removed when {@link #unbind()} is called. This provides a handy way to track event
     * handler registrations when binding and unbinding.
     * 
     * @param handlerRegistration The registration.
     */
    protected void registerHandler(final HandlerRegistration handlerRegistration) {
        handlerRegistrations.add(handlerRegistration);
    }

    /**
     * Checks if the presenter has been bound. Will be set to false after a call
     * to {@link #unbind()}.
     * 
     * @return The current bound status.
     */
    @Override
    public boolean isBound() {
        return bound;
    }

    /**
     * Returns the display for the presenter.
     * 
     * @return The display.
     */
    @Override
    public D getDisplay() {
        return display;
    }

    /**
     * Fires a {@link PresenterChangedEvent} to the {@link EventBus}.
     * Call this method any time the presenter's state has been modified.
     */
    protected void firePresenterChangedEvent() {
        eventBus.fireEvent(new PresenterChangedEvent(this));
    }

    /**
     * Fires a {@link PresenterRevealedEvent} to the {@link EventBus}.
     * Implementations should call this when the presenter has been
     * revealed onscreen.
     * 
     * @param originator If set to true, this specifies that this presenter
     *            was the originator of the 'revelation' request.
     */
    protected void firePresenterRevealedEvent(final boolean originator) {
        eventBus.fireEvent(new PresenterRevealedEvent(this, originator));
    }

    /**
     * Triggers a {@link PresenterRevealedEvent}. Subclasses should override
     * this method and call <code>super.revealDisplay()</code> if they need to
     * perform extra operations when being revealed.
     */
    @Override
    public void revealDisplay() {
        if (!revealed) {
            revealed = true;
            /** initialize view */
            display.init();
            /** On reveal display */
            onRevealDisplay();
            firePresenterRevealedEvent(true);
        }
    }

    /**
     * Checks if the presenter has been revealed. Will be set to false after a call
     * to {@link #disposeDisplay()}.
     * 
     * @return The current revealed status.
     */
    @Override
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * Called before firing a {@link PresenterRevealedEvent}.
     * Add all handler associated with the display here.
     */
    protected abstract void onRevealDisplay();

    /**
     * Dispose Display. Call @see {@link #onDisposeDisplay()}.
     */
    @Override
    public void disposeDisplay() {
        if (revealed) {
            revealed = false;
            display.dispose();
            onDisposeDisplay();
        }
    }

    /**
     * This method is called after display disposed.
     * You could remove all display handler here.
     */
    protected abstract void onDisposeDisplay();

    @Override
    public String toString() {
        return getClass().getName();
    }

    /**
     * Send a message to the user {@link NotifyMessageEvent}.
     * 
     * @param message message to send
     */
    protected void notifyMessage(String message) {
        eventBus.fireEvent(new NotifyMessageEvent(message));
    }

    /**
     * Send a message to the user.
     * 
     * @param message message to send
     * @param delayMillis time to live of this message
     */
    protected void notifyMessage(String message, int delayMillis) {
        eventBus.fireEvent(new NotifyMessageEvent(message, delayMillis));
    }

    /**
     * Inner implementation of BoundListener
     */
    private class InnerBoundListener implements BoundListener {

        private final BasePresenter<?> target;

        public InnerBoundListener(final BasePresenter<?> target) {
            this.target = target;
        }

        @Override
        public void onBind() {
            target.onBind();
        }

        @Override
        public void onUnbind() {
            target.onUnbind();
        }
    }

    /**
     * Inner Implementation of DisplayListener.
     */
    private class InnerDisplayListener implements DisplayListener {

        private final BasePresenter<?> target;

        public InnerDisplayListener(final BasePresenter<?> target) {
            this.target = target;
        }

        @Override
        public void onRevealDisplay() {
            target.onRevealDisplay();
        }

        @Override
        public void onDisposeDisplay() {
            target.onDisposeDisplay();
        }
    }

    
}
