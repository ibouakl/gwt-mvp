package com.gwt.mvp.client.presenter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Presenter;
import com.gwt.mvp.client.event.PresenterRevealedEvent;
import com.gwt.mvp.client.event.PresenterRevealedHandler;

/**
 * <code>ContainerPresenter</code> implement basic method for manage multiple
 * presenter.
 * 
 * @param <D>
 */
public abstract class ContainerPresenter<D extends Display, P extends Presenter> extends BasePresenter<D> {
    /** <code>children</code> attribute */
    protected final List<P> children;
    
    /**
     * Build a new instance of <code>ContainerPresenter</code>.
     * 
     * @param display display instance
     * @param eventBus the <code>eventBus</code> instance
     * @param presenters a list of presenter added to this container.
     */
    public ContainerPresenter(D display, EventBus eventBus, P... presenters) {
        super(display, eventBus);
        children = new ArrayList<P>();
        for (P presenter : presenters) {
            addPresenter(presenter);
        }
    }
    
    /**
     *Adds the presenter, if the current presenter is unbound.
     * 
     * @param presenter
     *            The presenter to add.
     * @return If added, returns <code>true</code>.
     */
    protected boolean addPresenter(P presenter) {
        if (!isBound()) {
            children.add(presenter);
            return true;
        }
        return false;
    }
    
    /**
     * Bind children and handle revelation events from children.
     * 
     * @see BasePresenter#onBind()
     */
    @Override
    protected void onBind() {
        for (Presenter child : children) {
            child.bind();
        }
        registerHandler(eventBus.addHandler(PresenterRevealedEvent.TYPE, new PresenterRevealedHandler() {
            @SuppressWarnings("unchecked")
            @Override
            public void onPresenterRevealed(PresenterRevealedEvent event) {
                if (contains(event.getPresenter())) {
                    onChildPresenterRevealed((P)event.getPresenter());
                }
            }
        }));
    }
    
    /**
     * Called when a child presenter is revealed.
     * 
     * @param presenter The revealed presenter.
     */
    protected abstract void onChildPresenterRevealed(final Presenter presenter);
    
    /**
     * Call {@link #unbind()} on each child.
     * 
     * @see ContainerPresenter#onUnbind()
     */
    @Override
    protected void onUnbind() {
        for (Presenter child : children) {
            child.unbind();
        }
    }
    
    /**
     * This method dispose all children's display.
     * 
     * @see BasePresenter#onDisposeDisplay()
     */
    @Override
    protected void onDisposeDisplay() {
        //Log.debug("Container presenter dispose display");
        for (Presenter child : children) {
            child.disposeDisplay();
        }
    }
    
    /**
     * @return a collection of child presenter.
     */
    protected List<P> getChildren() {
        return children;
    }
    
    /**
     * Test if the specified presenter is a child.
     * This implementation call child.equals instead of presenter.equals; By this way, we can play with composite presenter.
     * 
     * @param presenter
     * @return true if this container contains presenter.
     */
    protected boolean contains(Presenter presenter) {
        boolean find = false;
        if (presenter != null) {
            Iterator<P> iterator = children.iterator();
            while (!find && iterator.hasNext()) {
                find = iterator.next().equals(presenter);
            }
        }
        return find;
    }
    
    @Override
    public String toString() {
        return getClass().getName() + " [" + children + "]";
    }
    
}
