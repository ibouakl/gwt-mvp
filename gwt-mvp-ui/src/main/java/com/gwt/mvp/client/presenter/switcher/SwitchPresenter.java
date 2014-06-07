package com.gwt.mvp.client.presenter.switcher;

import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Presenter;
import com.gwt.mvp.client.presenter.ContainerPresenter;

/**
 * @author ibouakl
 * @author jguibert
 */
public class SwitchPresenter<D extends SwitchDisplay> extends ContainerPresenter<D, Presenter> {
    
    public SwitchPresenter(D display, EventBus eventBus, Presenter... presenters) {
        super(display, eventBus, presenters);
        
    }
    
    @Override
    protected void onChildPresenterRevealed(Presenter presenter) {
        revealDisplay();
        firePresenterRevealedEvent(false);
    }
    
    /**
     * Reveal all child and add them on display.
     */
    @Override
    protected void onRevealDisplay() {
        for (Presenter p : children) {
            if (p != null) {
                p.revealDisplay();
                display.add(p.getDisplay());
            }
        }
    }
    
    public boolean removePresenter(Presenter presenter) {
        if (contains(presenter)) {
            children.remove(presenter);
            if (presenter.isBound()) {
                presenter.unbind();
            }
            if (isRevealed() && presenter.isRevealed()) {
                display.remove(presenter.getDisplay());
                presenter.disposeDisplay();
            }
            
            return true;
        }
        return false;
    }
    
    public void removeAllPresenter() {
        for (Presenter p : children) {
            removePresenter(p);
        }
    }
    
    public void setCurrentPresenter(Presenter presenter) {
        if (isRevealed() && presenter != null && contains(presenter)) {
            display.show(presenter.getDisplay().asWidget());
        }
    }
    
    /**
     * Adds the presenter. If container is revealed, the presenter will be revealed to.
     * 
     * @param presenter The presenter to add.
     * @return If added, returns <code>true</code>.
     */
    public boolean addPresenter(Presenter presenter) {
        if (!contains(presenter)) {
            children.add(presenter);
            /** bind if neccessary */
            if (isBound()) {
                presenter.bind();
            }
            if (isRevealed()) {
                presenter.revealDisplay();
                display.add(presenter.getDisplay());
            }
            
        }
        return true;
    }
}
