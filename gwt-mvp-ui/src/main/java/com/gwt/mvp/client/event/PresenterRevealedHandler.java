package com.gwt.mvp.client.event;
import com.google.gwt.event.shared.EventHandler;

/**
 * Handles a presenter revelation event. <code>PresenterRevealedHandler</code> interface declare method
 * {@link PresenterRevealedHandler#onPresenterRevealed(PresenterRevealedEvent)} to handle {@link PresenterRevealedEvent}.
 * 
 * 
 * @author jguibert
 * @author David Peterson
 */
public interface PresenterRevealedHandler extends EventHandler {
    
    /**
     * Called when a presenter has been revealed.
     * 
     * @param event
     */
    public void onPresenterRevealed(final PresenterRevealedEvent event);
    
}
