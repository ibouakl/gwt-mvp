package com.gwt.ui.client.supertable;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.ui.client.resources.Images;
import com.gwt.ui.client.toolbar.Toolbar;
import com.gwt.ui.client.toolbar.ToolbarPanel;
/**
 * 
 * @author ibouakl
 *
 */
public class SuperTable extends FlexTable implements HasDoubleClickHandlers {
    private static final String SORT_BY_SELECTED_ROWS = "Sort by selected rows";
    
    private ColumnProperty[] columnsProperty;
    
    private ListBox commandList;
    
    private int currentPage = 0;
    
    private int headerRowCount = 0;
    
    private ArrayList<Widget> headers = new ArrayList<Widget>();
    
    private ArrayList<SuperTableListener> listeners = new ArrayList<SuperTableListener>();
    
    private Label pageNumLabel;
    
    private TextBox rpp;
    
    private int[] selectedCell = new int[] {
        -1, -1
    };
    
    private ArrayList<Integer> selectedRowIndices = new ArrayList<Integer>();
    
    private ArrayList<Widget[]> tableContent = new ArrayList<Widget[]>();
    
    private SuperTableProperty tableProperty;
    
    private ToolbarPanel toolbarPanel;
    
    private Toolbar userBar;
    
    private SuperTableColumnSelector sel;
    
    /**
     * An empty constructor for this class. If you use this constructor, please use the setter methods for setting the table and column
     * properties before adding any rows to the table.
     */
    public SuperTable() {
        this(null, new SuperTableProperty());
    }
    
    /**
     * A constructor for this class. The default value for the table properties is user if this constructor is used.
     * 
     * @param columnsProperty column property object
     */
    public SuperTable(ColumnProperty[] columnsProperty) {
        this(columnsProperty, new SuperTableProperty());
    }
    
    /**
     * A constructor for this class with the column and table property objects supplied as parameters.
     * 
     * @param columnsProperty tableProperty for each column in the table.
     * @param tableProperty table tableProperty.
     */
    public SuperTable(ColumnProperty[] columnsProperty, SuperTableProperty properties) {
        super();
        this.columnsProperty = columnsProperty;
        this.tableProperty = properties;
        properties.setParent(this);
        
        setStyleName("gwtcomp-SuperTable");
        if (columnsProperty != null) {
            populateHeader();
        }
        
        initTableListener();
        
        sinkEvents(Event.ONMOUSEOVER);
        sinkEvents(Event.ONMOUSEOUT);
    }
    
    public void clearData() {
        removeAllRows();
        clear();
        init();
        if (columnsProperty != null) {
            populateHeader();
        }
        
        initTableListener();
        
    }
    
    private void init() {
        headerRowCount = 0;
        currentPage = 0;
        selectedRowIndices = new ArrayList<Integer>();
        headers = new ArrayList<Widget>();
        tableContent = new ArrayList<Widget[]>();
        selectedCell = new int[] {
            -1, -1
        };
        setStyleName("gwtcomp-SuperTable");
    }
    
    /**
     * Add a row to the table
     * 
     * @param row widgets belonging to the row, each element in the array contains a column entry for the row.
     */
    public void addRow(Widget[] row) {
        addRow(row, false);
    }
    
    /**
     * Add a row to the table and set it to selected.
     * 
     * @param row widgets belonging to the row, each element in the array contains a column entry for the row.
     * @param selected whether the row is selected (will only be set to selected if SuperTableProperty.rowSelectionEnabled() is set to
     *            true).
     */
    public void addRow(Widget[] row, boolean selected) {
        int count = tableContent.size();
        int index = getRowCount();
        tableContent.add(row);
        if (tableProperty.isRowSelectionEnabled() && selected) {
            selectedRowIndices.add(new Integer(count));
        } else {
            selected = false;
        }
        
        setCurrentPageLabel();
        
        int start = getFirstRowIndex();
        if ((tableProperty.isPagingEnabled()) && (count >= start + tableProperty.getRecordsPerPage())) {
            if (selected) {
                dispatchRowSelectionEvent(count, selected, row);
            }
            return;
        }
        
        String rownum = Integer.toString(count + 1);
        HTML rowindex = new HTML(tableProperty.isRowSelectionEnabled() ? HTMLHelper.boxedText(rownum) : rownum);
        
        if (tableProperty.isRowSelectionEnabled()) {
            CheckBox checkBox = new CheckBox();
            
            checkBox.addClickHandler(new ClickHandler() {
                
                @Override
                public void onClick(ClickEvent event) {
                    
                    int row = getCellForEvent(event).getRowIndex();
                    int rindex = 0;
                    
                    if (tableProperty.isPagingEnabled()) {
                        rindex = ((currentPage * tableProperty.getRecordsPerPage()) + (row - headerRowCount));
                    } else {
                        rindex = row - headerRowCount;
                    }
                    
                    int loc = findInSelectedIndices(rindex);
                    boolean selected = false;
                    if (loc >= 0) // already selected
                    {
                        selected = false;
                        selectedRowIndices.remove(loc);
                        
                    } else
                    // not selected
                    {
                        
                        selected = true;
                        selectedRowIndices.add(new Integer(rindex));
                    }
                    
                    setRowStyle(selected, row);
                    Widget[] w = tableContent.get(rindex);
                    dispatchRowSelectionEvent(rindex, selected, w);
                    
                }
            });
            
            setWidget(index, 0, checkBox);
        } else {
            setWidget(index, 0, rowindex);
        }
        
        getCellFormatter().setStyleName(index, 0, "gwtcomp-SuperTableCell");
        getCellFormatter().setHorizontalAlignment(index, 0, HasHorizontalAlignment.ALIGN_CENTER);
        getCellFormatter().setVerticalAlignment(index, 0, HasVerticalAlignment.ALIGN_MIDDLE);
        if (tableProperty.isRowSelectionEnabled()) {
            rowindex.setStyleName("gwtcomp-SuperTableRowSelector");
        }
        
        int col = 1;
        for (int i = 0; i < row.length; i++) {
            if (columnsProperty[i].isDisplayed() == false) {
                continue;
            }
            
            setWidget(index, col, row[i]);
            getCellFormatter().setStyleName(index, col, "gwtcomp-SuperTableCell");
            getCellFormatter().setHorizontalAlignment(index, col, columnsProperty[i].getHorizontalAlignment());
            getCellFormatter().setVerticalAlignment(index, col, columnsProperty[i].getVerticalAlignment());
            col++;
        }
        
        setRowStyle(selected, index);
        
        if (selected == true) {
            dispatchRowSelectionEvent(count, selected, row);
        }
    }
    
    /**
     * Add a table listener. The listener can be used by the application to get notified of various events.
     * 
     * @param listener the listener object.
     */
    public void addTableListener(SuperTableListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Add an entry into the user tool bar. The user toolbar is added at the top of the table header. It is to the right of the table
     * navigation (next, previous, first, etc.) toolbar. An application can add one or more widgets into this toolbar.
     * 
     * @param w the widget to add.
     */
    public void addToUserToolbar(Widget w) {
        if (userBar == null) {
            return;
        }
        
        userBar.addWidget(w);
        toolbarPanel.redraw();
    }
    
    /**
     * Adds a user header. The application may add zero or more application-specific header. The header is added in a new row immediately
     * above the table header.
     * 
     * @param widget the widget to add. Normally, a panel of some type is added.
     */
    public void addUserHeader(Widget widget) {
        headers.add(widget);
        removeAllColumns(false);
        populateBody();
    }
    
    /*
     * Removes all columns except the header columns.
     * 
     * @see com.google.gwt.user.client.ui.HTMLTable#clear()
     */
    public void clear() {
        removeAllColumns(true);
    }
    
    /**
     * Delete a row from the table.
     * 
     * @param index the index starting with 0.
     */
    public void deleteRow(int index) {
        // if the cell was selected, unselect it
        if (selectedCell[0] == index) {
            dispatchCellSelectionEvent(index, selectedCell[1], false, tableContent.get(selectedCell[0])[selectedCell[1]]);
            selectedCell[0] = -1;
            selectedCell[1] = -1;
        }
        
        int sel = findInSelectedIndices(index);
        if (sel >= 0) {
            dispatchRowSelectionEvent(index, false, tableContent.get(sel));
            // adjust the rest of the selected items
            int size = selectedRowIndices.size();
            for (int i = sel + 1; i < size; i++) {
                int n = selectedRowIndices.get(i).intValue();
                selectedRowIndices.set(i, new Integer(n - 1));
            }
            
            selectedRowIndices.remove(sel);
        }
        
        tableContent.remove(index);
        int start = getFirstRowIndex();
        if ((tableProperty.isPagingEnabled()) && (index >= start + tableProperty.getRecordsPerPage())) {
            // outside of the current page, no display action required except
            // displaying the page.
            setCurrentPageLabel();
            return;
        }
        
        removeAllColumns(false);
        populateBody();
    }
    
    /**
     * Returns the column property object.
     * 
     * @return Returns the columnsProperty.
     */
    public ColumnProperty[] getColumnsProperty() {
        return columnsProperty;
    }
    
    /**
     * Returns the list box containing the application-defined list action items. This list box is located inside the command toolbar.
     * Applications can add their own items and listeners to the list.
     * 
     * @return Returns the commandList.
     */
    public ListBox getCommandList() {
        return commandList;
    }
    
    /**
     * Returns the row count in a current page. If paging is not enabled, the record count is the number of rows. If paging is enabled, the
     * size is determined by the rows per page table property.
     * 
     * @return record count
     */
    public int getCurrentPageRecordCount() {
        int records = getRowCount() - headerRowCount;
        return records;
    }
    
    /**
     * Returns the index of the first row in the current page. If paging is not enabled, the index is zero.
     * 
     * @return the first row index
     */
    public int getFirstRowIndex() {
        if (!tableProperty.isPagingEnabled()) {
            return 0;
        }
        
        int start = tableProperty.getRecordsPerPage() * currentPage;
        return start;
    }
    
    /**
     * Returns all the rows entries (and columns)
     * 
     * @return the rows
     */
    public Widget[][] getRows() {
        return (Widget[][])tableContent.toArray();
    }
    
    /**
     * Return the number of rows.
     * 
     * @return the number of rows
     */
    public int getRowSize() {
        return tableContent.size();
    }
    
    /**
     * Returns the user-selected cell. The first element in the returned array contains the row index and the second element contains the
     * column index.
     * 
     * @return A null is returned if the user has not selected a row.
     */
    public int[] getSelectedCell() {
        if ((selectedCell[0] == -1) || (selectedCell[1] == -1)) {
            return null;
        }
        
        return selectedCell;
    }
    
    /**
     * Returns the selected row index. If multiple rows are selected, only the first one is returned.
     * 
     * @return the row index starting with 0, -1 if no rows are selected.
     */
    public int getSelectedRowIndex() {
        int size = selectedRowIndices.size();
        if (size == 0) {
            return -1;
        }
        
        return selectedRowIndices.get(0).intValue();
    }
    
    /**
     * Returns the indicies of the selected rows.
     * 
     * @return If no rows are selected, an empty array is returned.
     */
    public int[] getSelectedRowIndices() {
        int size = selectedRowIndices.size();
        int[] ret = new int[size];
        for (int i = 0; i < size; i++) {
            ret[i] = selectedRowIndices.get(i).intValue();
        }
        return ret;
    }
    
    /**
     * Returns the content of the table in array list format.
     * 
     * @return Returns the tableContent.
     */
    public ArrayList<Widget[]> getTableContent() {
        return tableContent;
    }
    
    /**
     * Returns the table property.
     * 
     * @return Returns the SuperTableProperty object associated with this table.
     */
    public SuperTableProperty getTableProperty() {
        return tableProperty;
    }
    
    /**
     * Returns the toolbar panel object on the first row. Applications can add their own toolbars into this panel. However, applications can
     * also use the user tool bar which is part of this panel.
     * 
     * @return Returns the toolbarPanel.
     */
    public ToolbarPanel getToolbarPanel() {
        return toolbarPanel;
    }
    
    /**
     * Returns the user tool bar.
     * 
     * @return Returns the userBar.
     */
    public Toolbar getUserBar() {
        return userBar;
    }
    
    /**
     * Is the given cell selected?
     * 
     * @param row row index
     * @param col column index
     * @return true if the cell is selected.
     */
    public boolean isCellSelected(int row, int col) {
        return ((row == selectedCell[0]) && (col == selectedCell[1])) ? true : false;
    }
    
    /**
     * Check if a row is in the current page. If the table property has the paging set, the table content is displayed one page at a time.
     * Therefore a given row may not be in the currently-displayed page.
     * 
     * @param row the row number starting with 0.
     * @return true if the row is in the current page.
     */
    public boolean isRowInPage(int row) {
        return (row >= getFirstRowIndex()) && (row < getFirstRowIndex() + getCurrentPageRecordCount());
    }
    
    /**
     * Returns if the row is selected.
     * 
     * @param row row number starting with 0.
     * @return true if the row is selected, false otherwise.
     */
    public boolean isRowSelected(int row) {
        int index = findInSelectedIndices(row);
        return index >= 0 ? true : false;
    }
    
    /**
     * Remove a table listener.
     * 
     * @param listener the listener object to remove.
     */
    public void removeTableListener(SuperTableListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Replaces the widget in a given cell with a new one.
     * 
     * @param w the new widget object.
     * @param row row index
     * @param col column index
     */
    public void setCell(Widget w, int row, int col) {
        Widget[] columns = tableContent.get(row);
        columns[col] = w;
        setRow(columns, row);
    }
    
    /**
     * Select/deselect a cell. This method is not yet implemented.
     * 
     * @param row
     * @param col
     * @param selected
     */
    public void setCellSelected(int row, int col, boolean selected) {
        if (!tableProperty.isCellSelectionEnabled()) {
            return;
        }
        Window.alert("setCellSelected() is not yet implemented. Sorry!");
    }
    
    /**
     * Sets the column property. This method must be called prior to adding the first row.
     * 
     * @param columnsProperty The columnsProperty to set.
     */
    public void setColumnsProperty(ColumnProperty[] columnsProperty) {
        this.columnsProperty = columnsProperty;
        populateHeader();
    }
    
    /**
     * Replaces a row with a new row. All row and cell attributes remain the same.
     * 
     * @param row the new row
     * @param index the row index.
     */
    public void setRow(Widget[] row, int index) {
        setRow(row, index, null);
    }
    
    public boolean downRow(int index) {
        
        if (index < headerRowCount + getRowSize() - 1) {
            Widget[] x = tableContent.get(index - headerRowCount);
            Widget[] y = tableContent.get(index + 1 - headerRowCount);
            tableContent.set(index - headerRowCount, y);
            tableContent.set(index + 1 - headerRowCount, x);
            populateBody();       
            return true;
        }
        return false;
    }
    
    public boolean upRow(int index){
        
        if(index> headerRowCount){
            Widget[] x = tableContent.get(index - headerRowCount);
            Widget[] y = tableContent.get(index - 1 - headerRowCount);
            tableContent.set(index - headerRowCount, y);
            tableContent.set(index - 1 - headerRowCount, x);
            populateBody(); 
            return true;
        }
        return false;
    }
    
    
    /**
     * Replaces a row with a new row.
     * 
     * @param row the new row
     * @param index the row index
     * @param selected if set to null, the row attribute is not modified; if not null, the row is set selected or not according to the
     *            value.
     */
    public void setRow(Widget[] row, int index, Boolean selected) {
        tableContent.set(index, row);
        boolean dispatch = false;
        if (selected != null) {
            int sel = findInSelectedIndices(index);
            if (tableProperty.isRowSelectionEnabled() && selected.booleanValue()) {
                if (sel < 0) {
                    dispatch = true;
                    selectedRowIndices.add(new Integer(index));
                }
            } else {
                if (sel >= 0) {
                    dispatch = true;
                    selectedRowIndices.remove(sel);
                }
            }
        }
        
        int start = getFirstRowIndex();
        if ((tableProperty.isPagingEnabled()) && (index >= start + tableProperty.getRecordsPerPage())) {
            if (dispatch) {
                dispatchRowSelectionEvent(index, selected.booleanValue(), row);
            }
            return;
        }
        
        int ind = index + headerRowCount - start;
        
        String rownum = Integer.toString(index + 1);
        HTML rowindex = new HTML(tableProperty.isRowSelectionEnabled() ? HTMLHelper.boxedText(rownum) : rownum);
        setWidget(ind, 0, rowindex);
        getCellFormatter().setStyleName(ind, 0, "gwtcomp-SuperTableCell");
        getCellFormatter().setHorizontalAlignment(ind, 0, HasHorizontalAlignment.ALIGN_CENTER);
        getCellFormatter().setVerticalAlignment(ind, 0, HasVerticalAlignment.ALIGN_MIDDLE);
        
        if (tableProperty.isRowSelectionEnabled()) {
            rowindex.setStyleName("gwtcomp-SuperTableRowSelector");
        }
        
        int col = 1;
        for (int i = 0; i < row.length; i++) {
            if (columnsProperty[i].isDisplayed() == false) {
                continue;
            }
            
            setWidget(ind, col, row[i]);
            if ((selectedCell[0] == index) && (selectedCell[1] == i)) {
                getCellFormatter().setStyleName(ind, col, "gwtcomp-SuperTableCellSelected");
            } else {
                getCellFormatter().setStyleName(ind, col, "gwtcomp-SuperTableCell");
            }
            
            getCellFormatter().setHorizontalAlignment(ind, col, columnsProperty[i].getHorizontalAlignment());
            getCellFormatter().setVerticalAlignment(ind, col, columnsProperty[i].getVerticalAlignment());
            
            col++;
        }
        
        if (selected != null) {
            setRowStyle(selected.booleanValue(), ind);
        }
        
        if (dispatch) {
            dispatchRowSelectionEvent(index, selected.booleanValue(), row);
        }
    }
    
    /**
     * Select or deselect a row.
     * 
     * @param row row index
     * @param selected true or false.
     */
    public void setRowSelected(int row, boolean selected) {
        if (!tableProperty.isRowSelectionEnabled()) {
            return;
        }
        Widget[] w = tableContent.get(row);
        setRow(w, row, new Boolean(selected));
    }
    
    /**
     * Sets the table property. This method must be called prior to adding the first row and after calling the setColumnsProperty() method
     * for it to work properly.
     * 
     * @param tableProperty The tableProperty to set.
     */
    public void setTableProperty(SuperTableProperty tableProperty) {
        this.tableProperty = tableProperty;
        tableProperty.setParent(this);
        populateHeader();
    }
    
    private int countPages() {
        if (!tableProperty.isPagingEnabled()) {
            return 1;
        }
        
        if (tableProperty.getRecordsPerPage() == 0) {
            return 0;
        }
        
        int pages = tableContent.size() / tableProperty.getRecordsPerPage();
        if (tableContent.size() % tableProperty.getRecordsPerPage() > 0) {
            pages++;
        }
        
        return pages;
    }
    
    private int countVisibleColumns() {
        int size = columnsProperty.length;
        for (int i = 0; i < columnsProperty.length; i++) {
            if (!columnsProperty[i].isDisplayed()) {
                size--;
            }
        }
        
        return size;
    }
    
    private void dispatchCellSelectionEvent(int row, int col, boolean select, Widget w) {
        if (!tableProperty.isCellSelectionEnabled()) {
            return;
        }
        
        for (int i = 0; i < listeners.size(); i++) {
            SuperTableListener listener = listeners.get(i);
            
            listener.cellSelected(select, row, col, w);
        }
    }
    
    private void dispatchRowSelectionEvent(int index, boolean selected, Widget[] w) {
        if (!tableProperty.isRowSelectionEnabled()) {
            return;
        }
        
        for (int i = 0; i < listeners.size(); i++) {
            SuperTableListener listener = listeners.get(i);
            listener.rowSelected(selected, index, w);
        }
    }
    
    private int findInSelectedIndices(int index) {
        int loc = -1;
        for (int i = 0; i < selectedRowIndices.size(); i++) {
            Integer sel = selectedRowIndices.get(i);
            if (sel.intValue() == index) {
                loc = i;
                break;
            }
        }
        return loc;
    }
    
    private void initColumnHeaders() {
        getRowFormatter().setVerticalAlign(headerRowCount, HasVerticalAlignment.ALIGN_TOP);
        
        class SortListener implements ClickHandler {
            private boolean asc;
            
            private int column;
            
            public SortListener(boolean asc, int column) {
                this.asc = asc;
                this.column = column;
            }
            
            @Override
            public void onClick(ClickEvent arg0) {
                processSort(column, asc);
                
            }
        }
        
        getCellFormatter().setStyleName(headerRowCount, 0, "gwtcomp-SuperTableHeader");
        
        initAllRowSelector();
        
        int col = 1;
        for (int i = 0; i < columnsProperty.length; i++) {
            if (!columnsProperty[i].isDisplayed()) {
                continue;
            }
            
            HorizontalPanel hp = new HorizontalPanel();
            
            if (columnsProperty[i].getAscendingSortComparator() != null) {
                Image asc_link = new Image(Images.IMAGES.downIcon());
                asc_link.addStyleName("gwtcomp-SortArrow");
                asc_link.setTitle("Sort in ascending order");
                asc_link.addClickHandler(new SortListener(true, i));
                hp.add(asc_link);
            }
            
            if (columnsProperty[i].getDescendingSortComparator() != null) {
                Image dsc_link = new Image(Images.IMAGES.upIcon());
                dsc_link.addStyleName("gwtcomp-SortArrow");
                dsc_link.setTitle("Sort in descending order");
                dsc_link.addClickHandler(new SortListener(false, i));
                hp.add(dsc_link);
            }
            
            hp.add(new HTML(HTMLHelper.bold(columnsProperty[i].getHeader())));
            
            getCellFormatter().setStyleName(headerRowCount, col, "gwtcomp-SuperTableHeader");
            
            if (columnsProperty[i].getWidth() != null) {
                getCellFormatter().setWidth(headerRowCount, col, columnsProperty[i].getWidth());
            }
            getCellFormatter().setWordWrap(headerRowCount, col, false);
            
            setWidget(headerRowCount, col++, hp);
        }
        
        headerRowCount++;
    }
    
    private void initAllRowSelector() {
        if (!tableProperty.isRowSelectionEnabled()) {
            return;
        }
        
        CheckBox selectall = new CheckBox();
        selectall.setTitle("Select all rows");
        setWidget(headerRowCount, 0, selectall);
        getCellFormatter().setHorizontalAlignment(headerRowCount, 0, HasHorizontalAlignment.ALIGN_CENTER);
        selectall.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent sender) {
                int start = getFirstRowIndex();
                int records = 0;
                
                if (tableProperty.isPagingEnabled()) {
                    records = tableProperty.getRecordsPerPage();
                    if (start + tableProperty.getRecordsPerPage() > tableContent.size()) {
                        records = tableContent.size() - start;
                    }
                } else {
                    records = tableContent.size();
                }
                
                CheckBox box = (CheckBox)sender.getSource();
                if (box.getValue()) // select all
                {
                    for (int i = 0; i < records; i++) {
                        // go through all the visible items and select them
                        int loc = findInSelectedIndices(start + i);
                        if (loc >= 0) // already selected
                        {
                            continue;
                        }
                        
                        processRowSelectionEvent(i + headerRowCount, start + i);
                        
                    }
                    
                } else
                // unselect all
                {
                    // go through all the visible items and unselect them
                    for (int i = 0; i < records; i++) {
                        // go through all the visible items and unselect
                        // them
                        int loc = findInSelectedIndices(start + i);
                        if (loc < 0) // already unselected
                        {
                            continue;
                        }
                        
                        processRowSelectionEvent(i + headerRowCount, start + i);
                        
                    }
                }
                
            }
        });
        
    }
    
    private void initColumnSelector() {
        Toolbar bar = new Toolbar();
        bar.setHorizontalSpacing(10);
        
        Image img = new Image(Images.IMAGES.rebuildIcon());
        bar.addWidget(img);
        img.setTitle("Show/Hide columns");
        
        img.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent sender) {
                sel = new SuperTableColumnSelector(columnsProperty, new SuperTableColumnSelectorListener() {
                    public void columnSelectionChanged(ColumnProperty[] columns) {
                        columnsProperty = columns;
                        removeAllColumns(false);
                        populateBody();
                    }
                });
                sel.setPopupPosition(sender.getRelativeElement().getAbsoluteLeft(), sender.getRelativeElement().getAbsoluteTop() + 10);
                sel.show();
                
            }
        });
        
        toolbarPanel.addToolbar(bar);
    }
    
    private void initCommandBar() {
        Toolbar sbar = new Toolbar();
        sbar.setHorizontalSpacing(10);
        
        commandList = new ListBox();
        commandList.setWidth("100px");
        commandList.addChangeHandler(new ChangeHandler() {
            
            @Override
            public void onChange(ChangeEvent arg0) {
                if (isSortBySelected()) {
                    ArrayList<Widget[]> selected = new ArrayList<Widget[]>();
                    for (int i = 0; i < selectedRowIndices.size(); i++) {
                        int index = selectedRowIndices.get(i).intValue();
                        Widget[] row = tableContent.get(index);
                        selected.add(row);
                    }
                    
                    int size = selected.size();
                    selectedRowIndices.clear();
                    for (int i = 0; i < size; i++) {
                        tableContent.remove(selected.get(i));
                        tableContent.add(0, selected.get(i));
                        selectedRowIndices.add(new Integer(i));
                    }
                    
                    currentPage = 0;
                    removeAllColumns(false);
                    populateBody();
                }
                
            }
        });
        
        sbar.addWidget(commandList);
        commandList.addItem("None");
        toolbarPanel.addToolbar(sbar);
    }
    
    private void initPagingBar() {
        Toolbar pbar = new Toolbar();
        pbar.setHorizontalSpacing(10);
        
        Image first = new Image(Images.IMAGES.startIcon());
        first.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent arg0) {
                currentPage = 0;
                removeAllColumns(false);
                populateBody();
                
            }
        });
        
        pbar.addWidget(first);
        first.setTitle("First page");
        
        Image next = new Image(Images.IMAGES.nextIcon());
        next.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent arg0) {
                if (currentPage >= countPages() - 1) {
                    return;
                }
                currentPage++;
                removeAllColumns(false);
                populateBody();
                
            }
        });
        
        pbar.addWidget(next);
        next.setTitle("Next Page");
        
        Image prev = new Image(Images.IMAGES.previousIcon());
        prev.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent arg0) {
                if (currentPage <= 0) {
                    return;
                }
                
                currentPage--;
                removeAllColumns(false);
                populateBody();
                
            }
        });
        
        pbar.addWidget(prev);
        prev.setTitle("Previous Page");
        
        Image last = new Image(Images.IMAGES.finishIcon());
        last.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent arg0) {
                currentPage = countPages() - 1;
                removeAllColumns(false);
                populateBody();
                
            }
        });
        
        pbar.addWidget(last);
        last.setTitle("Last Page");
        
        HorizontalPanel records = new HorizontalPanel();
        records.setSpacing(2);
        records.add(new Label("Records/Page"));
        rpp = new TextBox();
        rpp.addKeyDownHandler(new KeyDownHandler() {
            
            @Override
            public void onKeyDown(KeyDownEvent arg0) {
                // TODO Auto-generated method stub
                
            }
        });
        rpp.addKeyUpHandler(new KeyUpHandler() {
            
            @Override
            public void onKeyUp(KeyUpEvent arg0) {
                // TODO Auto-generated method stub
                
            }
        });
        
        rpp.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode()) {
                    processRecordsPerPageChange();
                }
                
            }
        });
        
        rpp.setMaxLength(3);
        rpp.setVisibleLength(3);
        rpp.setText(tableProperty.getRecordsPerPage() + "");
        records.add(rpp);
        
        Button display = new Button("Display");
        display.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent arg0) {
                processRecordsPerPageChange();
                
            }
        });
        
        records.add(display);
        pageNumLabel = new Label("000/000");
        records.add(pageNumLabel);
        pbar.addWidget(records);
        
        toolbarPanel.addToolbar(pbar);
    }
    
    private void initTableActions() {
        if (tableProperty.isCommandBarEnabled()) {
            commandList.addItem(SORT_BY_SELECTED_ROWS);
        }
    }
    
    /**
     * Method to add css (highlight) on mouse over and on mouse out. When the user move the mouse on a row, we change the color.
     * 
     * @author ibouakl
     */
    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        
        switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEOVER: {
                Element td = getEventTargetCell(event);
                if (td == null) {
                    return;
                }
                
                Element tr = DOM.getParent(td);
                Element body = DOM.getParent(tr);
                int row = DOM.getChildIndex(body, tr);// get the index row
                
                getRowFormatter().addStyleName(row, "gwtcomp-grid-row-highlight");// add the style gwtcomp-grid-row-highlight
                
                break;
            }
            case Event.ONMOUSEOUT: {
                Element td = getEventTargetCell(event);
                if (td == null) {
                    return;
                }
                
                Element tr = DOM.getParent(td);
                Element body = DOM.getParent(tr);
                int row = DOM.getChildIndex(body, tr);// get the index row
                getRowFormatter().removeStyleName(row, "gwtcomp-grid-row-highlight");// remove the style gwtcomp-grid-row-highlight
                
                break;
            }
            default: {
                // Do nothing
            }
        }
    }
    
    /**
     * init the listeners
     * 
     * @author ibouakl
     */
    private void initTableListener() {
        
        addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                
                Cell cell = getCellForEvent(event);
                
                processTableEvent(cell.getRowIndex(), cell.getCellIndex());
                
            }
        });
        
    }
    
    private void initToolbar() {
        if (!tableProperty.isToolbarEnabled()) {
            return;
        }
        
        toolbarPanel = new ToolbarPanel();
        getFlexCellFormatter().setColSpan(0, 0, countVisibleColumns() + 1);
        setWidget(headerRowCount++, 0, toolbarPanel);
        
        if (tableProperty.isColumnSelectorEnabled()) {
            initColumnSelector();
        }
        
        if (tableProperty.isPagingEnabled()) {
            initPagingBar();
        }
        
        if (tableProperty.isCommandBarEnabled()) {
            initCommandBar();
        }
        
        initUserBar();
        
        if (tableProperty.isRowSelectionEnabled()) {
            initTableActions();
        }
    }
    
    private void initUserBar() {
        if (userBar == null) {
            userBar = new Toolbar();
            userBar.setHorizontalSpacing(10);
        }
        toolbarPanel.addToolbar(userBar);
    }
    
    private void initUserHeaders() {
        for (int i = 0; i < headers.size(); i++) {
            Widget w = headers.get(i);
            setWidget(headerRowCount++, 0, w);
            getFlexCellFormatter().setColSpan(headerRowCount - 1, 0, countVisibleColumns() + 1);
            w.setWidth("100%");
        }
    }
    
    private boolean isSortBySelected() {
        if (commandList == null) {
            return false;
        }
        
        int index = commandList.getSelectedIndex();
        if (index < 0) {
            return false;
        }
        
        if (!commandList.getItemText(index).equals(SORT_BY_SELECTED_ROWS)) {
            return false;
        }
        
        return true;
    }
    
    private void populateBody() {
        int start = getFirstRowIndex();
        int records = 0;
        
        if (tableProperty.isPagingEnabled()) {
            records = tableProperty.getRecordsPerPage();
            if (start + tableProperty.getRecordsPerPage() > tableContent.size()) {
                records = tableContent.size() - start;
            }
        } else {
            records = tableContent.size();
        }
        
        for (int i = 0; i < records; i++) {
            String rownum = Integer.toString(start + i + 1);
            HTML rowindex = new HTML(tableProperty.isRowSelectionEnabled() ? HTMLHelper.boxedText(rownum) : rownum);
            // setWidget(i + headerRowCount, 0, rowindex);
            getCellFormatter().setStyleName(i + headerRowCount, 0, "gwtcomp-SuperTableCell");
            getCellFormatter().setHorizontalAlignment(i + headerRowCount, 0, HasHorizontalAlignment.ALIGN_CENTER);
            getCellFormatter().setVerticalAlignment(i + headerRowCount, 0, HasVerticalAlignment.ALIGN_MIDDLE);
            
            if (tableProperty.isRowSelectionEnabled()) {
                rowindex.setStyleName("gwtcomp-SuperTableRowSelector");
                
                CheckBox checkBox = new CheckBox();
                
                checkBox.addClickHandler(new ClickHandler() {
                    
                    @Override
                    public void onClick(ClickEvent event) {
                        
                        int row = getCellForEvent(event).getRowIndex();
                        int rindex = 0;
                        
                        if (tableProperty.isPagingEnabled()) {
                            rindex = ((currentPage * tableProperty.getRecordsPerPage()) + (row - headerRowCount));
                        } else {
                            rindex = row - headerRowCount;
                        }
                        
                        int loc = findInSelectedIndices(rindex);
                        boolean selected = false;
                        if (loc >= 0) // already selected
                        {
                            selected = false;
                            selectedRowIndices.remove(loc);
                            
                        } else
                        // not selected
                        {
                            
                            selected = true;
                            selectedRowIndices.add(new Integer(rindex));
                        }
                        
                        setRowStyle(selected, row);
                        Widget[] w = tableContent.get(rindex);
                        dispatchRowSelectionEvent(rindex, selected, w);
                        
                    }
                });
                
                setWidget(i + headerRowCount, 0, checkBox);
                
            } else {
                
                setWidget(i + headerRowCount, 0, rowindex);
            }
            
            Widget[] row = tableContent.get(i + start);
            int col = 1;
            for (int j = 0; j < row.length; j++) {
                if (columnsProperty[j].isDisplayed() == false) {
                    continue;
                }
                
                setWidget(i + headerRowCount, col, row[j]);
                getCellFormatter().setHorizontalAlignment(i + headerRowCount, col, columnsProperty[j].getHorizontalAlignment());
                getCellFormatter().setVerticalAlignment(i + headerRowCount, col, columnsProperty[j].getVerticalAlignment());
                
                if ((selectedCell[0] == i + start) && (selectedCell[1] == j)) {
                    getCellFormatter().setStyleName(i + headerRowCount, col, "gwtcomp-SuperTableCellSelected");
                } else {
                    getCellFormatter().setStyleName(i + headerRowCount, col, "gwtcomp-SuperTableCell");
                }
                
                col++;
            }
            
            int selected = findInSelectedIndices(i + start);
            setRowStyle((selected >= 0 ? true : false), i + headerRowCount);
            if (getWidget(i + headerRowCount, 0) instanceof CheckBox)
                ((CheckBox)getWidget(i + headerRowCount, 0)).setValue((selected >= 0 ? true : false));
            
        }
        
        setCurrentPageLabel();
    }
    
    private void populateHeader() {
        initToolbar();
        
        initUserHeaders();
        
        initColumnHeaders();
    }
    
    private void processCellSelectionEvent(int row, int cell, int rindex) {
        // some columns may be hidden because of the column selector.
        // Find out the correct column number.
        int cindex = 0;
        for (int i = 0; i < columnsProperty.length; i++) {
            if (!columnsProperty[i].isDisplayed()) {
                cindex++;
            }
            
            if (i == (cell - 1)) {
                break;
            }
        }
        cindex += (cell - 1);
        
        boolean select = false;
        if ((rindex == selectedCell[0]) && (cindex == selectedCell[1])) {
            select = false;
            getCellFormatter().setStyleName(row, cell, "gwtcomp-SuperTableCell");
            selectedCell[0] = -1;
            selectedCell[1] = -1;
        } else {
            select = true;
            
            // remove the style of the previously selected cell
            if ((selectedCell[0] >= 0) && (selectedCell[1] >= 0)) {
                if (isRowInPage(selectedCell[0])) {
                    int r = headerRowCount + selectedCell[0] - getFirstRowIndex();
                    getCellFormatter().setStyleName(r, selectedCell[1] + 1, "gwtcomp-SuperTableCell");
                }
            }
            
            selectedCell[0] = rindex;
            selectedCell[1] = cindex;
            getFlexCellFormatter().setStyleName(row, cell, "gwtcomp-SuperTableCellSelected");
        }
        
        Widget w = tableContent.get(rindex)[cindex];
        dispatchCellSelectionEvent(rindex, cindex, select, w);
    }
    
    private void processRecordsPerPageChange() {
        int r = tableProperty.getRecordsPerPage();
        try {
            r = Integer.parseInt(rpp.getText().trim());
            if (r == 0) {
                // set it back to what it was before
                rpp.setText(tableProperty.getRecordsPerPage() + "");
                return;
            }
        } catch (NumberFormatException e) {
            // set it back to what it was before
            rpp.setText(r + "");
            return;
        }
        
        currentPage = 0; // reset the page number
        tableProperty.setRecordsPerPage(r);
        removeAllColumns(false);
        populateBody();
    }
    
    private void processRowSelectionEvent(int row, int rindex) {
        int loc = findInSelectedIndices(rindex);
        boolean selected = false;
        if (loc >= 0) // already selected
        {
            selected = false;
            selectedRowIndices.remove(loc);
            
        } else
        // not selected
        {
            
            selected = true;
            selectedRowIndices.add(new Integer(rindex));
        }
        
        setRowStyle(selected, row);
        if (getWidget(row, 0) instanceof CheckBox)
            ((CheckBox)getWidget(row, 0)).setValue(selected);
        
        Widget[] w = tableContent.get(rindex);
        dispatchRowSelectionEvent(rindex, selected, w);
    }
    
    private void processSort(int col, boolean asc) {
        int start = getFirstRowIndex();
        int records = getCurrentPageRecordCount();
        
        Widget selcell = null;
        if ((selectedCell[0] >= 0) && (selectedCell[1] >= 0)) {
            if (isRowInPage(selectedCell[0])) {
                selcell = tableContent.get(selectedCell[0])[selectedCell[1]];
            }
        }
        
        // Copy the table content of this page into a separate array.
        // Also, save information on the selected rows
        ArrayList<Widget[]> selected = new ArrayList<Widget[]>();
        ArrayList<Widget[]> list = new ArrayList<Widget[]>();
        for (int i = 0; i < records; i++) {
            list.add(tableContent.get(start + i));
            int sel = findInSelectedIndices(start + i);
            if (sel >= 0) {
                Widget[] row = tableContent.get(start + i);
                dispatchRowSelectionEvent(start + i, false, row);
                
                selected.add(row);
                selectedRowIndices.remove(sel);
            }
        }
        
        // sort it
        Collections.sort(list, asc == true ? columnsProperty[col].getAscendingSortComparator() : columnsProperty[col].getDescendingSortComparator());
        
        // replace the sorted list in the table content and adjust the selected
        // rows
        for (int i = 0; i < list.size(); i++) {
            Widget[] w = (Widget[])list.get(i);
            tableContent.set(start + i, w);
            
            if ((selcell != null) && (selcell == w[selectedCell[1]])) {
                selectedCell[0] = start + i;
                dispatchCellSelectionEvent(selectedCell[0], selectedCell[1], true, selcell);
            }
            
            for (int j = 0; j < selected.size(); j++) {
                Widget[] sw = (Widget[])selected.get(j);
                if (w == sw) {
                    selectedRowIndices.add(new Integer(start + i));
                    dispatchRowSelectionEvent(start + i, true, w);
                    break;
                }
            }
        }
        
        removeAllColumns(false);
        populateBody();
    }
    
    private void processTableEvent(int row, int cell) {
        if (tableProperty == null) {
            return;
        }
        
        if (row < headerRowCount) {
            return;
        }
        
        int rindex = 0;
        
        if (tableProperty.isPagingEnabled()) {
            rindex = ((currentPage * tableProperty.getRecordsPerPage()) + (row - headerRowCount));
        } else {
            rindex = row - headerRowCount;
        }
        if ((cell == 0) && !(getWidget(row, 0) instanceof CheckBox) && (tableProperty.isRowSelectionEnabled())) {
            processRowSelectionEvent(row, rindex);
            
        } else if (tableProperty.isCellSelectionEnabled()) {
            processCellSelectionEvent(row, cell, rindex);
        }
    }
    
    private void removeAllColumns(boolean clearHistory) {
        int count = getRowCount();
        for (int i = count - 1; i >= 0; i--) {
            removeRow(i);
        }
        
        if (clearHistory == true) {
            selectedRowIndices.clear();
            selectedCell[0] = -1;
            selectedCell[1] = -1;
            tableContent.clear();
        }
        
        headerRowCount = 0;
        populateHeader();
    }
    
    private void setCurrentPageLabel() {
        if (tableProperty.isPagingEnabled()) {
            pageNumLabel.setText((currentPage + 1) + "/" + countPages());
        }
    }
    
    private void setRowStyle(boolean selected, int index) {
        if (tableProperty.isRowSelectionEnabled() && selected) {
            getRowFormatter().setStyleName(index, "gwtcomp-SuperTableRowSelected");
        } else if (((index - headerRowCount) % 2) == 0) {
            getRowFormatter().setStyleName(index, "gwtcomp-SuperTableRowEven");
        } else {
            getRowFormatter().setStyleName(index, "gwtcomp-SuperTableRowOdd");
        }
    }
    
    /**
     * In some cases, the table does not display the toolbar panel properly for very complex reasons. This method is provided to manually
     * redraw the panel. (This is a temporary solution until a completely transparent solution is found.)
     */
    public void redraw() {
        if (toolbarPanel != null) {
            toolbarPanel.redraw();
        }
    }
    
    @Override
    public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
        return addDomHandler(handler, DoubleClickEvent.getType());
    }
    
    public SuperTableColumnSelector getSel() {
        return sel;
    }
    
}
