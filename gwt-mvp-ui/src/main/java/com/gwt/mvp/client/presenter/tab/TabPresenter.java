package com.gwt.mvp.client.presenter.tab;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Presenter;
import com.gwt.mvp.client.event.SelectionPresenterEvent;
import com.gwt.mvp.client.event.TabItemClosedEvent;
import com.gwt.mvp.client.event.TabItemClosedHander;
import com.gwt.mvp.client.presenter.ContainerPresenter;
import com.gwt.mvp.client.presenter.tab.TabItemPresenter.TabItemDisplay;

public class TabPresenter<D extends TabDisplay> extends ContainerPresenter<D, TabItemPresenter> {

    private boolean fireSelectionEvent;

    /**
     * Build a new instance of <code>TabPresenter</code>.
     * 
     * @param display
     * @param eventBus
     * @param presenters
     */
    public TabPresenter(final D display, final EventBus eventBus, final TabItemPresenter... presenters) {
        super(display, eventBus, presenters);
        fireSelectionEvent = true;
    }

    /**
     * Adds the presenter.
     * If container is revealed, the presenter will be revealed to.
     * 
     * @param presenter
     *            The presenter to add.
     * @return If added, returns <code>true</code>.
     */
    @Override
    public boolean addPresenter(final TabItemPresenter presenter) {
        if (!contains(presenter)) {
            children.add(presenter);
            /** bind if necessary */
            if (isBound()) {
                presenter.bind();
                if (isRevealed()) {
                    addChild(presenter);
                }
            }
        }
        return true;
    }

    /**
     * Adds the presenter.
     * If container is revealed, the presenter will be revealed to.
     * 
     * @param tabItemDisplay tab item display to use.
     * @param presenter
     *            The presenter to add.
     * @return If added, returns <code>true</code>.
     */
    public boolean addPresenter(final TabItemDisplay tabItemDisplay, final Presenter presenter) {
        return addPresenter(new TabItemPresenter(tabItemDisplay, eventBus, presenter));
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
            // if reveal, dispose
            if (isRevealed()) {
                display.remove(presenter.getDisplay());
                presenter.disposeDisplay();
            }
            // if bound, unbound
            if (isBound()) {
                presenter.unbind();
            }
            return true;
        }
        return false;
    }

    /**
     * @return true if this presenter fire a Selection event.
     */
    public boolean isFireSelectionEvent() {
        return fireSelectionEvent;
    }

    /**
     * Control selection event raising.
     * It could be useful to disable selection event firing, if none of this event his used.
     * 
     * @param fireSelectionEvent the fire selection event flag
     */
    public void setFireSelectionEvent(boolean fireSelectionEvent) {
        this.fireSelectionEvent = fireSelectionEvent;
    }

    /**
     * Reveal all child and add them on display.
     */
    @Override
    protected void onRevealDisplay() {
        for (TabItemPresenter presenter : getChildren()) {
            addChild(presenter);
        }
        // set a default selected tab
        if (display.getDisplayCount() > -1) {
            display.selectDisplay(0);
        }
        // set selection handler
        if (fireSelectionEvent) {
            if (display.getSelectionHandlers() != null) {
                display.getSelectionHandlers().addSelectionHandler(new SelectionHandler<Integer>() {

                    @Override
                    public void onSelection(SelectionEvent<Integer> selectionEvent) {
                        for (TabItemPresenter tabItemPresenter : getChildren()) {
                            if (selectionEvent.getSelectedItem().equals(display.getDisplayIndex(tabItemPresenter.getDisplay()))) {
                                eventBus.fireEvent(new SelectionPresenterEvent(tabItemPresenter.getPresenter()));
                            }
                        }
                    }
                });
            }
        }
    }

    private void addChild(final TabItemPresenter presenter) {
        presenter.revealDisplay();
        display.add(presenter.getDisplay(), presenter.getChildDisplay());
    }

    /**
     * On child presenter revealed, reveal ourself and select tab display.
     */
    @Override
    protected void onChildPresenterRevealed(final Presenter presenter) {
        revealDisplay();
        // set current
        int tabIndex = display.getDisplayIndex(presenter.getDisplay());
        if (tabIndex > -1 && (tabIndex != display.getSelectedDisplayIndex())) {
            display.selectDisplay(tabIndex);
        }
        firePresenterRevealedEvent(false);
    }

    /** Handle <code>TabItemClosedEvent</code> event */
    @Override
    protected void onBind() {
        super.onBind();
        // add TabItemClosedHander handler.
        registerHandler(eventBus.addHandler(TabItemClosedEvent.TYPE, new TabItemClosedHander() {

            @Override
            public void onTabItemClosed(TabItemPresenter presenter) {
                removePresenter(presenter);
            }
        }));
    }

    @Override
    protected void onUnbind() {
        removeChildren();
        super.onUnbind();
    }

    protected final void removeChildren() {
        for (TabItemPresenter presenter : children) {
            children.remove(presenter); // if bound, unbound
            if (presenter.isRevealed()) {
                display.remove(presenter.getDisplay());
            }
            presenter.unbind();

        }
        children.clear();
    }
}
