package com.gwt.ui.client;

import java.util.EventListener;

/**
 * An interface that must be extended to create your own wizard panel listener.
 * 
 * @author Amit Chatterjee
 *
 */
public interface WizardPanelListener extends EventListener
{
    /**
     * Tis method is called after the page is switched as a result of clicking on the
     * Next/Previous buttons or programatically. You can implement this method to 
     * include your own custom logic (for example, disable the Next button) to perform
     * validations and/or modify appearances.
     * 
     * @param name name of panel that currently showing.
     */
    public void onPageSwitch(String name);
}
