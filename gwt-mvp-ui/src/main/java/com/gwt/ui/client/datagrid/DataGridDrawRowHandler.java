package com.gwt.ui.client.datagrid;
/**
 * 
 * @author ibouakl
 *
 */
public interface DataGridDrawRowHandler {
    /**
     * This method is invoked before the row is drawn.
     *
     * @param row is a row number.
     * @param pageSize is a page size.
     * @param grid is a grid instance.
     * @param rowData an array of row data.
     *
     * @return <code>false</code> if the row must be skipped.
     */
    boolean beforeDraw(int row,DataGrid grid, Object[] rowData);

    /**
     * This method is invoked after the row is drawn.
     *
     * @param row is a row number.
     * @param grid is a grid instance.
     * @param rowData an array of row data.
     *
     * @return <code>false</code> if drawing must be stopped when the row is drawn.
     */
    boolean afterDraw(int row,DataGrid grid, Object[] rowData);
}