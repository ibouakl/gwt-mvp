package com.gwt.ui.client.datagrid;

/**
 * @author ibouakl
 */
public interface DataGridEvent {
    public void removeSelectRowListener(DataGridSelectRowListener selectRowListener);
    
    public void addSelectRowListener(DataGridSelectRowListener selectRowListener);
    
    /**
     * Registers the specified listener that will be invoked on double click event.
     *
     * @param listener is a listener to register.
     */
    void addDoubleClickListener(DataGridDoubleClickListener listener);

    /**
     * Removes (unregisters) the specified listener.
     *
     * @param listener is a listener to remove.
     */
    void removeDoubleClickListener(DataGridDoubleClickListener listener);
    
}
