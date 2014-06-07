package com.gwt.ui.client.supertable;
/*
 * 
 */
public class SuperTableProperty {
    private boolean rowSelectionEnabled = false;
    
    private boolean pagingEnabled = true;
    
    private int recordsPerPage = 25;
    
    private boolean commandBarEnabled = true;
    
    private boolean cellSelectionEnabled = false;
    
    private boolean columnSelectorEnabled = true;
    
    private boolean toolbarEnabled = true;
    
    private SuperTable parent;
    
    /**
     * A constructor for this class.
     */
    public SuperTableProperty() {
    }
    
    /**
     * Returns true if paging is enabled.
     * 
     * @return Returns the pagingEnabled.
     */
    public boolean isPagingEnabled() {
        return pagingEnabled;
    }
    
    /**
     * Enables/disables paging. Paging is enabled by default.
     * 
     * @param pagingEnabled The pagingEnabled to set.
     */
    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }
    
    /**
     * Returns the records per page. If paging has not been enabled, it returns the total number of records in the table.
     * 
     * @return Returns the recordsPerPage.
     */
    public int getRecordsPerPage() {
        if ((parent == null) || (!pagingEnabled)) {
            // return the table size
            return parent.getTableContent().size();
        } else {
            return recordsPerPage;
        }
    }
    
    /**
     * Sets the number of records per page. This takes effect only if paging is enabled.
     * 
     * @param recordsPerPage The recordsPerPage to set.
     */
    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }
    
    /**
     * Returns true if row selection is enabled.
     * 
     * @return Returns the rowSelectionEnabled.
     */
    public boolean isRowSelectionEnabled() {
        return rowSelectionEnabled;
    }
    
    /**
     * Enable/disable row selection. By default, row selection is not enabled.
     * 
     * @param rowSelectionEnabled The rowSelectionEnabled to set.
     */
    public void setRowSelectionEnabled(boolean rowSelectionEnabled) {
        this.rowSelectionEnabled = rowSelectionEnabled;
    }
    
    /**
     * Returns true if the command tool bar is enabled.
     * 
     * @return Returns the commandBarEnabled.
     */
    public boolean isCommandBarEnabled() {
        return commandBarEnabled;
    }
    
    /**
     * Enable/disable the command bar. By default, it is enabled.
     * 
     * @param commandBarEnabled The commandBarEnabled to set.
     */
    public void setCommandBarEnabled(boolean commandBarEnabled) {
        this.commandBarEnabled = commandBarEnabled;
    }
    
    /**
     * Returns true if cell selection is enabled.
     * 
     * @return Returns the cellSelectionEnabled.
     */
    public boolean isCellSelectionEnabled() {
        return cellSelectionEnabled;
    }
    
    /**
     * Enables/disables cell selection By default, it is disabled.
     * 
     * @param cellSelectionEnabled The cellSelectionEnabled to set.
     */
    public void setCellSelectionEnabled(boolean cellSelectionEnabled) {
        this.cellSelectionEnabled = cellSelectionEnabled;
    }
    
    /**
     * @return Returns the parent.
     */
    protected SuperTable getParent() {
        return parent;
    }
    
    /**
     * @param parent The parent to set.
     */
    protected void setParent(SuperTable parent) {
        this.parent = parent;
    }
    
    /**
     * Returns true if the colum selector is enabled.
     * 
     * @return Returns the columnSelectorEnabled.
     */
    public boolean isColumnSelectorEnabled() {
        return columnSelectorEnabled;
    }
    
    /**
     * Enable/disable column selector. By default, it is enabled.
     * 
     * @param columnSelectorEnabled The columnSelectorEnabled to set.
     */
    public void setColumnSelectorEnabled(boolean columnSelectorEnabled) {
        this.columnSelectorEnabled = columnSelectorEnabled;
    }
    
    /**
     * Returns true if the tool bar is enabled.
     * 
     * @return Returns the toolbarEnabled.
     */
    public boolean isToolbarEnabled() {
        return toolbarEnabled;
    }
    
    /**
     * Enable/disable the tool bar. By default, it is enabled.
     * 
     * @param toolbarEnabled The toolbarEnabled to set.
     */
    public void setToolbarEnabled(boolean toolbarEnabled) {
        this.toolbarEnabled = toolbarEnabled;
    }
}
