package com.gwt.mvp.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Presenter;

/**
 * <code>MultiPresenter</code> manage a dynamic list of presenter. Each child can be revealed/disposed on demand.
 * 
 * @param <D>
 */
public class MultiPresenter<D extends MultiDisplay> extends ContainerPresenter<D, Presenter> {
    
    private final List<Presenter> currents;
    
    /**
     * Build a new instance of <code>MultiPresenter</code>.
     * 
     * @param display display instance
     * @param eventBus event bus instance
     * @param presenters presenters to add
     */
    public MultiPresenter(final D display, final EventBus eventBus, final Presenter... presenters) {
        super(display, eventBus, presenters);
        currents = new ArrayList<Presenter>();
    }
    
    @Override
    protected void onChildPresenterRevealed(final Presenter presenter) {
        revealDisplay();
        firePresenterRevealedEvent(false);
    }
    
    /**
     * Adds presenter on current selection.
     * 
     * @param presenter The presenter to Add.
     * @return true if the presenter has been added.
     */
    public boolean addCurrentPresenter(final Presenter presenter) {
        if (!contains(presenter)) {
            if (!currents.contains(presenter)) {
                currents.add(presenter);
                if (isRevealed()) {
                    presenter.revealDisplay();
                    display.add(presenter.getDisplay());
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * Remove the presenter from current selected presenters.
     * @param presenter
     * @return true if the presenter has been removed.
     */
    public boolean removeCurrentPresenter(final Presenter presenter) {
        if (!contains(presenter)) {
            if (currents.contains(presenter)) {
                currents.remove(presenter);
                if (isRevealed() && presenter.isRevealed()) {
                    display.remove(presenter.getDisplay());
                    presenter.disposeDisplay();
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * Reveal all child and add them on display.
     */
    @Override
    protected void onRevealDisplay() {
        for (Presenter p : currents) {
            p.revealDisplay();
            display.add(p.getDisplay());
        }
    }
    
    /**
     * Adds the presenter.
     * If container is revealed, the presenter will be revealed to.
     * 
     * @param presenter
     *            The presenter to add.
     * @return If added, returns <code>true</code>.
     */
    public boolean addPresenter(Presenter presenter) {
        if (!contains(presenter)) {
            children.add(presenter);
            /** bind if neccessary */
            if (isBound()) {
                presenter.bind();
            }
        }
        return true;
    }
    
    /**
     * Removes the presenter.
     * 
     * @param presenter The presenter to remove.
     * @return If removed, returns <code>true</code>.
     */
    public boolean removePresenter(Presenter presenter) {
        if (contains(presenter)) {
            children.remove(presenter);
            if (isBound()) {
                presenter.unbind();
            }
            return true;
        }
        return false;
    }
    
}
