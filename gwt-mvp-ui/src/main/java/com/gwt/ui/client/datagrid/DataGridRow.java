package com.gwt.ui.client.datagrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataGridRow {
    /** list identifier */
    private String identifier;
    /** this index number */
    private int index;
    /** delegate list */
    private List<Object> delegate;

    /**
     * Constructs a new GridRow.
     *
     * @param initialCapacity is an initial capacity value.
     */
    protected DataGridRow(int initialCapacity) {
        this.delegate =  new ArrayList<Object>(initialCapacity);
        this.identifier = generateUniqueString();
    }

    /** Constructs a new GridRow. */
    protected DataGridRow() {
        this.delegate = new ArrayList<Object>();
        this.identifier = generateUniqueString();
    }

    /**
     * Getter for property 'identifier'.
     *
     * @return Value for property 'identifier'.
     */
    public String getIdentifier () {
        return identifier;
    }

    /**
     * This method gets data of this row.
     *
     * @return a data array.
     */
    public Object[] getData() {
        return getDelegate().toArray(new Object[getDelegate().size()]);
    }
    
    /**
     * This method sets data into the row.
     *
     * @param data is row data.
     */
    public void setData(Object[] data) {
        getDelegate().clear();
        getDelegate().addAll(Arrays.asList(data));
    }

    /**
     * Setter for property 'identifier'.
     *
     * @param identifier Value to set for property 'identifier'.
     */
    protected void setIdentifier (String identifier) {
        this.identifier = identifier;
    }

    /**
     * This method generates an unique string.
     *
     * @return is a unique string.
     */
    protected final String generateUniqueString() {
        StringBuffer result = new StringBuffer(String.valueOf(System.currentTimeMillis()));
        result.append(Math.random());
        return result.toString();
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!super.equals(o)) return false;

        DataGridRow that = (DataGridRow) o;

        return !(identifier != null ? !identifier.equals(that.identifier) : that.identifier != null);
    }

 
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        return result;
    }


    public int getIndex() {
        return index;
    }

 
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter for property 'delegate'.
     *
     * @return Value for property 'delegate'.
     */
    protected List<Object> getDelegate() {
        return delegate;
    }

    /**
     * Adds a new cell value at the end of row.
     *
     * @param cell is a cell value to add.
     */
    protected void add(Object cell) {
        getDelegate().add(cell);
    }

    /**
     * This method adds a new value before the specified cell.
     *
     * @param beforeCell is a cell index.
     * @param data is cell data to be added.
     */
    protected void add(int beforeCell, Object data) {
        getDelegate().add(beforeCell, data);
    }

    /**
     * This method gets data placed in the specified cell.
     *
     * @param index a cell index.
     * @return cell data.
     */
    protected Object get(int index) {
        return getDelegate().get(index);
    }

    /**
     * Removes specified cell.
     *
     * @param cellNumber is a cell index.
     */
    protected void remove(int cellNumber) {
        getDelegate().remove(cellNumber);
    }

    /**
     * Sets the specified data to the cell.
     *
     * @param column is a cell index.
     * @param data is data to be set.
     */
    protected void set(int column, Object data) {
        getDelegate().set(column, data);
    }
}