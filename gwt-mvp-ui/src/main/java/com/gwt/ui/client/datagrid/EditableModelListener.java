package com.gwt.ui.client.datagrid;
public interface EditableModelListener {
    /**
     * This method is invoked on any data model change event.
     *
     * @param event is an event to be handled by the listeners.
     */
    void onModelEvent(EditableModelEvent event);
    
    void onSetSource();
}