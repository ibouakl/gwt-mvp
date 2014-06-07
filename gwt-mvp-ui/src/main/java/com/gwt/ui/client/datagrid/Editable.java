package com.gwt.ui.client.datagrid;


/**
 * 
 * @author ibouakl
 *
 */
public interface Editable {
    /**
     * This method adds a row into the model.
     * 
     * @param beforeRow is a row number.
     * @param row is row data.
     * @throws IllegalArgumentException if the row number is invalid.
     */
    void addRow(int beforeRow, Object[] row) throws IllegalArgumentException;
    
    
    void addRow(Object[] row) throws IllegalArgumentException;
    
    /**
     * This method updates a row with the specified data set.
     * 
     * @param rowNumber is a row number.
     * @param row is row data.
     * @throws IllegalArgumentException if the row number is invalid.
     */
    void updateRow(int rowNumber, Object[] row) throws IllegalArgumentException;
    
    /**
     * This method removes a row from the model.
     * 
     * @param rowNumber is a row number.
     * @throws IllegalArgumentException if the row number is invalid.
     */
    void removeRow(int rowNumber) throws IllegalArgumentException;
        
    
    /**
     * This method removes a column from the model.
     * 
     * @param columnNumber a column number.
     * @throws IllegalArgumentException if the column number is invalid.
     */
    void removeColumn(int columnNumber) throws IllegalArgumentException;
    
    /**
     * This method removes a column from the model.
     * 
     * @param name a column name.
     */
    void removeColumn(String name);
    
    /**
     * This method removes all rows from the model.
     */
    void removeAll();
    
    /**
     * This method updates the specified cell with the value.
     * 
     * @param row is a row number.
     * @param column is a column number.
     * @param data is a data to be applied.
     * @throws IllegalArgumentException if row and / or column number is invalid.
     */
    void update(int row, int column, Object data) throws IllegalArgumentException;
    
   
    /**
     * This method updates data in the model using the specified value.
     * 
     * @param data is a data to be placed.
     */
    void update(Object[][] data);
    
    /**
     * This method returns a total row count.
     * 
     * @return a total row count.
     */
    int getTotalColumnCount();
      
    /**
     * This method returns all rows of the model.
     * 
     * @return a grid row array.
     */
    DataGridRow[] getRows();
//
//    /**
//     * Gets an array of all model columns.
//     *
//     * @return a columns array.
//     */
//    DataGridColumn[] getColumns();
//    
    /**
     * Gets a grid row by index.
     * 
     * @param index is an index value.
     * @return a grid row.
     */
    DataGridRow getRow(int index);
    
//    /**
//     * Gets a grid column by index.
//     *
//     * @param index is an index value.
//     * @return a grid column.
//     */
//    DataGridColumn getGridColumn(int index);
    
  
    
    public int getEndRow();
    
    public int getStartRow();
    
    public int getSize();
    
    public boolean isEmpty() ;
    
    public Object[] getRowData(int rowNumber);
    
    public Object getData(int row,int col);
    
}
