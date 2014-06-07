package com.gwt.mvp.client.presenter;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.Presenter;
import com.gwt.mvp.client.event.ShortcutKeyboards;
import com.gwt.mvp.client.event.ShortcutKeyboardsEvent;

/**
 * <code>RootPresenter</code> class.
 * 
 */
public class RootPresenter extends CompositePresenter<RootDisplay> {
    
    private boolean isCtrl;
    
    /**
     * Build a new instance of RootPresenter.
     * 
     * @param display root display instance
     * @param eventBus event bus instance
     */
    public RootPresenter(final RootDisplay display, final EventBus eventBus) {
        super(display, eventBus);
        
        Event.addNativePreviewHandler(new NativePreviewHandler() {
            @Override
            public void onPreviewNativeEvent(NativePreviewEvent event) {
                switch (event.getTypeInt()) {
                    case Event.ONKEYDOWN:
                        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
                            eventBus.fireEvent(new ShortcutKeyboardsEvent(ShortcutKeyboards.Esc));
                        }
                        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_CTRL) {
                            isCtrl = true;
                            eventBus.fireEvent(new ShortcutKeyboardsEvent(ShortcutKeyboards.CtrlDown));
                        }
                        if (event.getNativeEvent().getKeyCode() == 83 && isCtrl) {
                            eventBus.fireEvent(new ShortcutKeyboardsEvent(ShortcutKeyboards.CtrlS));
                            isCtrl = false;
                            event.cancel();
                        }
                        
                        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_SHIFT) {
                            eventBus.fireEvent(new ShortcutKeyboardsEvent(ShortcutKeyboards.ShiftDown));
                        }
                        
                        break;
                    case Event.ONKEYUP:
                        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_CTRL) {
                            isCtrl = false;
                            eventBus.fireEvent(new ShortcutKeyboardsEvent(ShortcutKeyboards.CtrlUp));
                        }
                        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_SHIFT) {
                            eventBus.fireEvent(new ShortcutKeyboardsEvent(ShortcutKeyboards.ShiftUp));
                        }
                        
                        
                        break;
                    default:
                        break;
                }
                
            }
        });
    }
    
    public void addPresenter(final Presenter... presenters) {
        for (Presenter presenter : presenters) {
            super.addPresenter("root", presenter);
        }
    }
    
    /**
     * Utility method: bind and reveal <code>RootPresenter</code>.
     */
    public void revealRoot() {
        bind();
        revealDisplay();
    }
    
}
