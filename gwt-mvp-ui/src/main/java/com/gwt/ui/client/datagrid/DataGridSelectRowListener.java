package com.gwt.ui.client.datagrid;
/**
 * 
 * @author ibouakl
 *
 */
public interface DataGridSelectRowListener {
    /**
     * This method is invoked every time when row selection is done.
     *
     * @param grid is a grid where the row has been selected.
     * @param row is a selected row number.
     */
    void onSelect(DataSource dataSource, int row,int col);
}
