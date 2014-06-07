package com.gwt.ui.client.button;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

public class ButtonPanel extends FlowPanel
{
    /**
     * A constructor for this class.
     * 
     */
    public ButtonPanel()
    {
        super();
        setWidth("100%");
        setStyleName("gwtcomp-ButtonPanel");
    }

    /**
     * Add a button.
     * 
     * @param button
     *            button to add
     */
    public void addButton(Button button)
    {
        button.setStyleName("gwtcomp-ButtonPanel-Button");
        add(button);
    }

    /**
     * Remove a button.
     * 
     * @param button
     *            button to remove.
     */
    public void removeButton(Button button)
    {
        remove(button);
    }
}

