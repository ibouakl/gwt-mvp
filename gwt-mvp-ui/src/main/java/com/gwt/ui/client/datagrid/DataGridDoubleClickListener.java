package com.gwt.ui.client.datagrid;

/**
 * 
 * @author ibouakl
 *
 */
public interface DataGridDoubleClickListener {
    
    /**
     * This method is invoked if the double click event occurs in one of
     * sender cells.
     * @param dataSource is  a DataSource
     * @param row is a row number.
     * @param cell is a cell number.
     */
    void onCellDoubleClick(DataSource dataSource, int row, int cell);
}
