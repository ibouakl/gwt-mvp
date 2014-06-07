package com.gwt.mvp.client.presenter.notify;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.mvp.client.Display;

/**
 * <code>NotifyDisplay</code> must be a implementation of <code>Display</code> in order to be able to add it on a RootDisplay.
 * 
 */
public class NotifyDisplay implements Display {
    
    private FlowPanel panel;
    private String message;
    private int stackIndex;
    
    /**
     * Build a new instance of <code>NotifyDisplay</code>.
     * 
     * @param message message to show
     * @param stackIndex stack index
     */
    public NotifyDisplay(final String message, final int stackIndex) {
        super();
        this.message = message;
        this.stackIndex = stackIndex;
    }
    
    @Override
    public Widget asWidget() {
        return panel;
    }
    
    @Override
    public void dispose() {
        if (panel != null) {
            panel.removeFromParent();
        }
        panel = null;
    }
    
    @Override
    public void init() {
        panel = new FlowPanel();
        
        panel.addStyleName("notificationPanel");
        panel.getElement().setAttribute("style", "top:" + (RootPanel.get().getElement().getClientHeight() - (stackIndex + 1) * 150) + "px; " + "left:" + (RootPanel.get().getElement().getClientWidth() - 200) + "px;");
        HTML html = new HTML(message);
        panel.add(html);
    }
    
}
