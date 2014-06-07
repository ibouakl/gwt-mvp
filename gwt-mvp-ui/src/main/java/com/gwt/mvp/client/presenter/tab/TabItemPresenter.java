package com.gwt.mvp.client.presenter.tab;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Presenter;
import com.gwt.mvp.client.event.TabItemClosedEvent;
import com.gwt.mvp.client.presenter.BasePresenter;

/**
 * <code>TabItemPresenter</code> add necessary data on a <code>Presenter</code> added in a tab container.
 * 
 */
public class TabItemPresenter extends BasePresenter<TabItemPresenter.TabItemDisplay> {
    
    /**
     * <code>TabItemDisplay</code> add method to manage "close" handler.
     * 
     * @author Jerome Guibert
     */
    public interface TabItemDisplay extends Display {
        
        /**
         * @return an instance of <code>HasClickHandlers</code> if closed event is managed, null if not.
         */
        public HasClickHandlers getClose();
        
    }
    
    /** associated presenter. */
    private final Presenter presenter;
    
    /**
     * Build a new instance of <code>TabItemPresenter</code>.
     * 
     * @param tabItemDisplay
     * @param eventBus
     * @param presenter
     */
    public TabItemPresenter(final TabItemDisplay tabItemDisplay, final EventBus eventBus, final Presenter presenter) {
        super(tabItemDisplay, eventBus);
        this.presenter = presenter;
    }
    
    @Override
    public String toString() {
        return "TabItemPresenter [presenter=" + presenter + "]";
    }
    
    /**
     * @return Inner presenter instance.
     */
    public Presenter getPresenter() {
        return presenter;
    }
    
    /**
     * Handle inner presenter {@link #bind()}.
     */
    @Override
    protected void onBind() {
        presenter.bind();
    }
    
    /**
     * Handle inner presenter {@link #unbind()}.
     */
    @Override
    protected void onUnbind() {
        presenter.unbind();
    }
    
    /**
     * Handle inner presenter {@link #disposeDisplay()}.
     */
    @Override
    protected void onDisposeDisplay() {
        presenter.disposeDisplay();
    }
    
    /**
     * Handle inner presenter {@link #revealDisplay()}, and handle {@link HasClickHandlers} if <code>TabItemDisplay</code> return one.
     */
    @Override
    protected void onRevealDisplay() {
        presenter.revealDisplay();
        // if display return a ClickHandler for close tab, we add handler
        if (display.getClose() != null) {
            display.getClose().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    fireTabItemClosedEvent();
                }
            });
        }
    }
    
    /**
     * Utility method to fire a <code>TabItemClosedEvent</code> event.
     */
    protected void fireTabItemClosedEvent() {
        eventBus.fireEvent(new TabItemClosedEvent(this));
    }
    
    /**
     * @return a display instance of child presenter.
     */
    public Display getChildDisplay() {
        return presenter.getDisplay();
    }
    
    @Override
    public int hashCode() {
        return presenter.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Presenter))
            return false;
        Presenter other = (Presenter)obj;
        if (other instanceof TabItemPresenter) {
            TabItemPresenter tother = (TabItemPresenter) other;
            return presenter.equals(tother.getPresenter());
        }
        return presenter.equals(other);
    }
    
}
