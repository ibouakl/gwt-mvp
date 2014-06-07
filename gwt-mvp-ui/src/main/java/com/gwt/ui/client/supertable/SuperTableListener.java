package com.gwt.ui.client.supertable;

import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author ibouakl
 *
 */
public interface SuperTableListener {
    /**
     * Invoked if a row is selected or deselected. Multiple rows can be set in a super table.
     * 
     * @param selected selected or deselected.
     * @param row row index
     * @param columns the row including all the columns.
     */
    public void rowSelected(boolean selected, int row, Widget[] columns);
    
    /**
     * Invoked if a cell is selected. Only one cell can remain selected at a time. Therefore, when this method is invoked and the selected
     * parameter is set to true, the previous selection is assumed to cleared. There is no separate invocation for the previously-selected
     * cell. A deselection invocation is only made if the selected row has been deselected by the user by clicking on it again.
     * 
     * @param selected selected or deselected.
     * @param row row index
     * @param col column index
     * @param column the column widget.
     */
    public void cellSelected(boolean selected, int row, int col, Widget column);
}
