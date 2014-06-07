package com.gwt.mvp.client;

/**
 * Presenter Interface.
 * <p />
 * Based on initial work of David Peterson (@see project gwt-presenter-1.1.1-replace)).
 * 
 * 
 * @author jguibert
 * @author ibouakl
 * 
 * @author David Peterson
 */
public interface Presenter {
    
    /**
     * Called when the presenter is initialized. This is called before any other methods. Any event handlers and other setup should be done
     * here rather than in the constructor.
     */
    public void bind();
    
    /**
     * Called after the presenter and display have been finished with for the moment.
     */
    public void unbind();
    
    /**
     * Returns true if the presenter is currently in a 'bound' state. That is, the {@link #bind()} method has completed and
     * {@link #unbind()} has not been called.
     * 
     * @return <code>true</code> if bound.
     */
    public boolean isBound();
    
    /**
     * Returns the {@link Display} for the current presenter.
     * 
     * @return The display.
     */
    public Display getDisplay();
    
    /**
     * Requests the presenter to reveal the display on screen. It should automatically ask any parent displays/presenters to reveal
     * themselves also. It should <b>not</b> trigger a refresh.
     */
    public void revealDisplay();
    
    /**
     * Requests the presenter to dispose the display on screen. It should automatically ask any child displays/presenters to dispose
     * themselves also. It should <b>not</b> trigger a refresh.
     */
    public void disposeDisplay();
    
    /**
     * Checks if the presenter has been revealed. Will be set to false after a call
     * to {@link #disposeDisplay()}.
     * 
     * @return The current revealed status.
     */
    public boolean isRevealed();
}
