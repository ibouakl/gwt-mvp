package com.gwt.mvp.client.presenter.notify;


import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.Timer;
import com.gwt.mvp.client.Display;
import com.gwt.mvp.client.EventBus;
import com.gwt.mvp.client.presenter.BasePresenter;
import com.gwt.mvp.client.presenter.RootDisplay;

public class NotifyPresenter extends BasePresenter<RootDisplay> {
    
    private final Set<Integer> stack;
    private final NotifyDisplayFactory notifyDisplayFactory;
    
    public NotifyPresenter(RootDisplay display, EventBus eventBus, NotifyDisplayFactory notifyDisplayFactory) {
        super(display, eventBus);
        stack = new HashSet<Integer>();
        this.notifyDisplayFactory = notifyDisplayFactory;
    }
    
    @Override
    protected void onBind() {
        // add handler for NotifyMessageEvent
        registerHandler(eventBus.addHandler(NotifyMessageEvent.TYPE, new NotifyMessageEventHandler() {
            @Override
            public void onMessage(String message, int delayMillis) {
                if (!isRevealed())
                    revealDisplay();
                // get stack index.
                final int stackIndex = getPlaceIndex();
                stack.add(stackIndex);
                final Display notifyDisplay = notifyDisplayFactory.build(message, stackIndex);
                notifyDisplay.init();
                display.addDisplay(notifyDisplay);
                // initialize a timer to destroy the Notification Panel after a delay in Milliseconds
                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        try {
                            stack.remove(stackIndex);
                            notifyDisplay.dispose();
                        } catch (Exception e) {
                            notifyDisplay.dispose();
                        }
                    }
                };
                timer.schedule(delayMillis);
            }
        }));
    }
    
    private int getPlaceIndex() {
        int index = 0;
        while (stack.contains(index)) {
            index++;
        }
        return index;
    }
    
    @Override
    protected void onDisposeDisplay() {
        
    }
    
    @Override
    protected void onRevealDisplay() {
        
    }
    
    @Override
    protected void onUnbind() {
        
    }
    
}
