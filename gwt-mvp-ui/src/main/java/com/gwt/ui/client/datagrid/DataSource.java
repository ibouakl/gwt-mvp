package com.gwt.ui.client.datagrid;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;

/**
 * @author ibouakl
 */
public class DataSource implements Editable {

    /** encapsulated data */
    private final List<DataGridRow> data;
    /** number of columns */
    private int totalColumnCount;
    private EditableModelListener listener;

    public DataSource() {
        super();
        this.data = new ArrayList<DataGridRow>(0);
        totalColumnCount = 0;
    }

    /**
     * This method returns the data set of the model.
     * 
     * @return a data set.
     */
    public Object[][] getData() {
        Object[][] rows = new Object[data.size()][totalColumnCount];
        for (int i = 0; i < rows.length; i++) {
            DataGridRow row = data.get(i);
            for (int j = 0; row != null && j < rows[i].length; j++) {
                rows[i][j] = row.get(j);
            }
        }
        return rows;
    }

    /**
     * This method creates a new identified list instance.
     * 
     * @param columnCount is a column count value.
     * @return a new identified list.
     */
    private DataGridRow createGridRow(int columnCount) {
        return new DataGridRow(columnCount);
    }



//    @Override
//    public DataGridColumn[] getColumns() {
//
//        return null;
//    }
//
//    @Override
//    public DataGridColumn getGridColumn(int index) {
//
//        return null;
//    }

    @Override
    public DataGridRow getRow(int index) {

        return null;
    }

    @Override
    public DataGridRow[] getRows() {
        return data.toArray(new DataGridRow[data.size()]);
    }

    @Override
    public void removeAll() {
        data.clear();
        fireEvent(createEvent(EditableModelEvent.CLEAN));
    }

    @Override
    public void removeColumn(int columnNumber) throws IllegalArgumentException {
    }

    @Override
    public void removeColumn(String name) {
    }

    @Override
    public void removeRow(int rowNumber) throws IllegalArgumentException {
        checkRowNumber(rowNumber, data.size());
        data.remove(rowNumber);
        fireRowEvent(EditableModelEvent.REMOVE_ROW, rowNumber);
    }

    @Override
    public void update(int row, int column, Object data) throws IllegalArgumentException {
    }

    @Override
    public void update(Object[][] data) {
        initializeData(data, data.length, data.length > 0 ? data[0].length : 0);
        fireEvent(createEvent(EditableModelEvent.UPDATE_ALL));
    }

    @Override
    public void updateRow(int rowNumber, Object[] row) throws IllegalArgumentException {
        checkRowNumber(rowNumber, data.size());
        if (row == null) {
            row = new Object[getTotalColumnCount()]; // Data can't not be null
        }
        DataGridRow oldRow = data.get(rowNumber);
        DataGridRow resultRow = normalizeColumnsCount(row);
        resultRow.setIdentifier(oldRow.getIdentifier());
        data.set(rowNumber, resultRow);
        fireRowEvent(EditableModelEvent.UPDATE_ROW, rowNumber);
    }

    /**
     * Fires a row change events.
     * 
     * @param eventType is a concrete row event type.
     * @param row is a row number.
     */
    protected void fireRowEvent(EditableModelEvent.EventType eventType, int row) {
        EditableModelEvent event = createEvent(eventType);
        event.setRow(row);
        event.setSource(this);
        fireEvent(event);
    }

    /**
     * This method fires the specified event.
     * 
     * @param event is an event to fire.
     */
    protected void fireEvent(EditableModelEvent event) {
        prepareEvent(event);
        if (listener != null) {
            try {
                listener.onModelEvent(event);
            } catch (Exception e) {
                GWT.log(e.getMessage(), e);
            }
        }
    }

    /**
     * This method prepares the specified event for sending, initilizing necessary fields.
     * 
     * @param event is an event to be prepared.
     */
    protected void prepareEvent(EditableModelEvent event) {
        event.setSource(this);
    }

    /**
     * Creates a new model event.
     * <p/>
     * Subclasses can override this method to support their own event classes.
     * 
     * @param eventType is an event type.
     * @return a newly constructed event.
     */
    protected EditableModelEvent createEvent(EditableModelEvent.EventType eventType) {
        return new EditableModelEvent(eventType);
    }

    /**
     * This method normalizes a number of columns in all rows adding empty cells.
     * <p>
     * If the specified row is shorter then the current columns count, it add empty cells to the row.
     *
     * @param row is a pattern row.
     * @return a result row.
     */
    private DataGridRow normalizeColumnsCount(Object[] row) {
        DataGridRow resultRow = createGridRow(0);
        resultRow.setData(row);

        // normalization
        if (row.length > totalColumnCount) {
            for (DataGridRow otherRow : data) {
                for (int i = totalColumnCount; i < row.length; i++) {
                    otherRow.add(null);
                }
            }
            totalColumnCount = row.length;
        } else {
            for (int i = row.length; i < totalColumnCount; i++) {
                resultRow.add(null);
            }
        }

        return resultRow;
    }

    private void checkRowNumber(int rowNumber, int max) {
        if (rowNumber < 0 || rowNumber >= max) {
            throw new IllegalArgumentException("Wrong row number. It must be in range [0, " + max + "). It is " + rowNumber);
        }
    }

    public int getSortColumn() {

        return 0;
    }

    @Override
    public int getEndRow() {
        return (getSize() > 0) ? getSize() - 1 : 0;
    }

    @Override
    public int getStartRow() {
        return 0;
    }

    @Override
    public int getTotalColumnCount() {
        return totalColumnCount;
    }

    @Override
    public int getSize() {
        return data != null ? data.size() : 0; //getData().length ;
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public Object[] getRowData(int rowNumber) {
        Object[] row = getData()[rowNumber];
        if (row == null) {
            row = new Object[0];
        }

        return row;
    }

    public EditableModelListener getListener() {
        return listener;
    }

    public void setListener(EditableModelListener listener) {
        this.listener = listener;
    }

    @Override
    public void addRow(Object[] row) throws IllegalArgumentException {
        if (totalColumnCount <= 0) {
            totalColumnCount = row.length;
        }
        if (row != null) {
            DataGridRow gridRow = createGridRow(getTotalColumnCount());
            gridRow.setData(row);
            data.add(gridRow);
            gridRow.setIndex(data.size() - 1);
            fireRowEvent(EditableModelEvent.ADD_ROW, gridRow.getIndex());
        }

    }
    
    @Override
    public void addRow(int beforeRow, Object[] row) throws IllegalArgumentException { 
        if (totalColumnCount <= 0) {
            totalColumnCount = row.length;
        }
        if (row != null) {
            DataGridRow gridRow = createGridRow(getTotalColumnCount());
            gridRow.setData(row);
            for(int i=beforeRow;i<data.size();i++){
                data.get(i).setIndex(data.get(i).getIndex()+1);
            }
            data.add(beforeRow,gridRow);
            gridRow.setIndex(beforeRow);
            fireRowEvent(EditableModelEvent.ADD_ROW_BEFORE, gridRow.getIndex());
        }

    }

    @Override
    public Object getData(int row, int col) {
        if (row >= 0 && row < getSize() && col >= 0 && col < totalColumnCount) {
            return getRowData(row)[col];
        }
        return null;
    }

    /**
     * @param data is a data to put into the data set.
     * @param rowCount is a row count of the data set.
     * @param columnCount is a column count of the data set.
     */
    private void initializeData(final Object[][] data, final int rowCount, final int columnCount) {
        int rows = Math.max(rowCount, (data != null ? data.length : 0));
        this.totalColumnCount = Math.max(columnCount, (data != null && data.length > 0 ? data[0].length : 0));
        this.data.clear();
        for (int i = 0; i < rows; i++) {
            DataGridRow row = createGridRow(totalColumnCount);
            for (int j = 0; j < totalColumnCount; j++) {
                if (data != null && data.length > i && data[0].length > j) {
                    row.add(data[i][j]);
                } else {
                    row.add(null);
                }
            }
            this.data.add(i, row);
            row.setIndex(i);
        }
    }
}
