package com.gwt.ui.client.supertable;

import java.util.EventListener;

/**
 * 
 * @author ibouakl
 *
 */
public interface SuperTableColumnSelectorListener extends EventListener {
    public void columnSelectionChanged(ColumnProperty[] columns);
}
