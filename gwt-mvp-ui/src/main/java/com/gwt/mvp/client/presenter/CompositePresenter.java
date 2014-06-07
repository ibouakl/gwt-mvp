package com.gwt.mvp.client.presenter;

import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Presenter;

/**
 * <code>CompositeContainerPresenter</code> implement composite behavior.
 * <p />
 * A <code>CompositeDisplay</code> declare one or many label. On each label, we can set specific <code>Display</code> instance.
 * 
 * @param <D>
 * @author jguibert
 */
public class CompositePresenter<D extends CompositeDisplay> extends ContainerPresenter<D, CompositePresenter<D>.LabeledPresenter> {
    
    /**
     * Build a new instance of <code>CompositePresenter</code>.
     * 
     * @param display display instance
     * @param eventBus event bus instance
     */
    public CompositePresenter(final D display, final EventBus eventBus) {
        super(display, eventBus);
    }
    
    /**
     * Adds presenter with the specified label.
     * 
     * @param label label associated with this presenter
     * @param presenter The presenter to Add
     * @return <code>true</code> if added
     */
    public boolean addPresenter(final String label, final Presenter presenter) {
        return addPresenter(new LabeledPresenter(label, presenter));
    }
    
    /**
     * Adds presenter with the specified label.
     * 
     * @param label label enum associated with this presenter
     * @param presenter The presenter to Add
     * @return <code>true</code> if added
     */
    public boolean addPresenter(final Enum<?> label, final Presenter presenter) {
        return addPresenter(label.toString(), presenter);
    }
    
    /**
     * Adds presenters with the specified label
     * 
     * @param label the label
     * @param presenters list of presenter to add
     * @return <code>true</code> if added
     */
    public boolean addPresenter(final String label, final Presenter... presenters) {
        if (!isBound()) {
            for (Presenter presenter : presenters) {
                addPresenter(new LabeledPresenter(label, presenter));
            }
            return true;
        }
        return false;
    }
    
    /**
     * Adds presenters with the specified label
     * 
     * @param label the label enum
     * @param presenters list of presenter to add
     * @return <code>true</code> if added
     */
    public boolean addPresenter(final Enum<?> label, final Presenter... presenters) {
        return addPresenter(label.toString(), presenters);
    }
    
    @Override
    protected void onChildPresenterRevealed(final Presenter presenter) {
        // reveal ourselves
        revealDisplay();
        // Reveal ourselves so that the child will be revealed.
        firePresenterRevealedEvent(false);
    }
    
    /**
     * Reveal all children.
     * 
     * @see BasePresenter#onRevealDisplay()
     */
    @Override
    protected void onRevealDisplay() {
        for (LabeledPresenter presenter : getChildren()) {
            presenter.revealDisplay();
            display.addDisplay(presenter.getLabel(), presenter.getDisplay());
        }
        display.show();
    }
    
    /**
     * <code>LabeledPresenter</code> is an inner class in order to manage a decoration on a <code>Presenter</code> instance.
     * 
     * @author Jerome Guibert
     */
    public class LabeledPresenter implements Presenter {
        /** label value. */
        private final String label;
        /** associated presenter. */
        private final Presenter presenter;
        
        /**
         * Build a new instance of <code>LabeledPresenter</code>.
         * 
         * @param label
         * @param presenter
         */
        public LabeledPresenter(final String label, final Presenter presenter) {
            super();
            this.label = label;
            this.presenter = presenter;
        }
        
        @Override
        public String toString() {
            return "LabeledPresenter [label=" + label + ", presenter=" + presenter + "]";
        }
        
        public String getLabel() {
            return label;
        }
        
        public Presenter getPresenter() {
            return presenter;
        }
        
        public void bind() {
            presenter.bind();
        }
        
        public void disposeDisplay() {
            presenter.disposeDisplay();
        }
        
        public Display getDisplay() {
            return presenter.getDisplay();
        }
        
        public boolean isBound() {
            return presenter.isBound();
        }
        
        public void revealDisplay() {
            presenter.revealDisplay();
        }
        
        public void unbind() {
            presenter.unbind();
        }
        
        @Override
        public boolean isRevealed() {
            return presenter.isRevealed();
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
            boolean result = presenter.equals(other);
            if (!result) {
                try {
                    return presenter.equals(((LabeledPresenter)other).getPresenter());
                } catch (ClassCastException e) {
                    // nothing to do
                }
            }
            return result;
        }
    }
    
    @Override
    public String toString() {
        return getClass().getName() + " [ children= " + children + "]";
    }
    
}
