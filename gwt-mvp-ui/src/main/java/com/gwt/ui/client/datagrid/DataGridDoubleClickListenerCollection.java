package com.gwt.ui.client.datagrid;
import java.util.ArrayList;

/**
 * 
 * @author ibouakl
 *
 */
public class DataGridDoubleClickListenerCollection extends ArrayList<DataGridDoubleClickListener> {
    /**
     * 
     */
    private static final long serialVersionUID = -250964780723563178L;

    /**
     * This method fires the double click event and invokes all the listeners
     * stored in this collection.
     *
     * @param sender is a sender table.
     * @param row is a row number.
     * @param cell is a cell number.
     */
    public void fireCellDoubleClicked(DataSource dataSource, int row, int cell) {
        for (DataGridDoubleClickListener listener : this) {
            try {
                listener.onCellDoubleClick(dataSource, row, cell);
            } catch (Throwable t) {
                //continue event dispatching whatever happened
            }
        }
    }
}