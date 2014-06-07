package com.gwt.ui.client.datagrid;

import com.google.gwt.user.client.ui.CheckBox;

/**
 * A check box to be used into the dataGrid for selection of row
 * 
 * @author ibouakl
 */
public class SelectionCheckBox extends CheckBox {
    private String id;
    
    /**
     * Constructor, id represents a ID of bean
     * 
     * @param id
     */
    public SelectionCheckBox(String id) {
        super();
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
}
