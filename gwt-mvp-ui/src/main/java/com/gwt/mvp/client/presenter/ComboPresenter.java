package com.gwt.mvp.client.presenter;

import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Presenter;

/**
 * <code>ComboPresenter</code> class implement a "combo" presenter behaviors: only one presenter of those contains, can be revealed at time.
 * 
 * @param <D>
 * 
 * @author jguibert
 */
public class ComboPresenter<D extends ComboPresenter.ComboPresenterDisplay> extends ContainerPresenter<D, Presenter> {

    /** current presenter activated. */
    protected Presenter current;

    /**
     * <code>ComboPresenterDisplay</code> interface declare method to set current display.
     *
     * @see Display
     * @author Jerome Guibert
     */
    public interface ComboPresenterDisplay extends Display {

        /**
         * Set current display for this instance of <code>ComboDisplay</code>.
         *
         * @param display The display to set
         */
        public void setCurrentDisplay(final Display display);
    }

    /**
     * Build a new instance of <code>ComboPresenter</code>.
     * 
     * @param display display instance
     * @param eventBus event bus instance
     * @param presenters list of presenter to add
     */
    public ComboPresenter(final D display, final EventBus eventBus, final Presenter... presenters) {
        super(display, eventBus, presenters);
        current = null;
    }

    @Override
    protected void onChildPresenterRevealed(final Presenter presenter) {
        // reveal ourselves
        revealDisplay();
        // Make this presenter the focus
        setCurrentPresenter(presenter);
        // Reveal ourselves so that the child will be revealed.
        firePresenterRevealedEvent(false);
    }

    /**
     * If current presenter exists, it will be revealed.
     * 
     * @see Presenter#onRevealDisplay()
     */
    @Override
    protected void onRevealDisplay() {
        if (current != null) {
            current.revealDisplay();
        }
    }

    /**
     * Sets the specified presenter to the be currently displaying presenter. If
     * the presenter has not been added ({@see #addPresenter(Presenter}), it
     * will not be set as the current presenter.
     * 
     * @param presenter
     *            The presenter.
     * @return <code>true</code> if the presenter was successfully set as the
     *         current presenter.
     */
    protected boolean setCurrentPresenter(final Presenter presenter) {
        if (contains(presenter)) {
            if (presenter != current) {
                if (current != null) {
                    //Log.debug("Combo call dispose display on current");
                    current.disposeDisplay();
                }
                current = presenter;
                current.revealDisplay();
                display.setCurrentDisplay(presenter.getDisplay());
            }
            return true;
        }
        return false;
    }

    /**
     * @return The currently displaying presenter.
     */
    protected Presenter getCurrent() {
        return current;
    }

    @Override
    public String toString() {
        return getClass().getName() + " [current=" + current + ", children= " + children + "]";
    }
}
