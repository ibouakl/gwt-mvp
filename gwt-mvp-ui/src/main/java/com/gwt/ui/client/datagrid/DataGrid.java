package com.gwt.ui.client.datagrid;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author ibouakl
 */
public class DataGrid extends FlexTable implements DataGridEvent {
    
    private Element head;
    private Element headerTr;
    private static final String GRID_ROW_HIGHLIGHT_STYLE = "highlight";
    private static final String GRID_ROW_EVEN_STYLE = "even";
    private static final String GRID_ROW_ODD_STYLE = "odd";
    /**
     * maximal timeout between two clicks in double click
     */
    private static final int CLICK_TIMEOUT = 300;
    /**
     * list of double click listeners registered in this widget
     */
    private DataGridDoubleClickListenerCollection doubleClikcListeners = new DataGridDoubleClickListenerCollection();
    /**
     * a list of select row listeners
     */
    private List<DataGridSelectRowListener> selectRowListeners;
    /**
     * List of columns
     */
    private List<DataGridColumn> dataGridColumns = new ArrayList<DataGridColumn>();
    /**
     * flag meaning that ONCLICK event is already sank
     */
    private boolean clickEnabled;
    /**
     * enables double clicks, must be switched to <code>false</code> if there are no double click listeners
     */
    private boolean doubleClickEnabled = false;
    /**
     * latest cell clicked
     */
    private Element cell;
    /**
     * count of clicks
     */
    private int clickCount;
    /**
     * the timer that is activated if the second click hasn't been done
     */
    private Timer clickTimer = new ClickTimer();
    /**
     * a callback handler for the row drawing events
     */
    private DataGridDrawRowHandler rowDrawHandler;
    /**
     * a list of invisible columns
     */
    private List<Integer> invisibleColumns = new ArrayList<Integer>();
    /**
     * a list of unselected columns
     */
    private List<Integer> unSelectedColumns = new ArrayList<Integer>();
    /**
     * this is a grid data model
     */
    private DataSource dataSource;
    /**
     * grid row selection model
     */
    private GridRowSelectDataModel selectionModel;
    
    public DataGrid() {
        super();
        addStyleName("data-Grid ");
        head = DOM.createTHead();
        headerTr = DOM.createTR();
        DOM.insertChild(this.getElement(), head, 0);
        DOM.insertChild(head, headerTr, 0);
        // Remove the child "colgroup" from the DOM
        DOM.removeChild(this.getElement(), DOM.getChild(this.getElement(), 1));
        sinkEvents(Event.ONMOUSEOVER);
        sinkEvents(Event.ONMOUSEOUT);
        
    }
    
    public void hideColumn(String key) {
        for (DataGridColumn column : dataGridColumns) {
            if (column.getKey().equals(key)) {
                column.setVisible(Boolean.FALSE);
                invisibleColumns.add(column.getIndex());
            }
        }
    }
    
    public void setId(String id) {
        DOM.setElementAttribute(getElement(), "id", id);
    }
    
    @Override
    public void setHeight(String height) {
        DOM.setElementAttribute(getBodyElement(), "height", height);
    }
    
    @SuppressWarnings("unused")
    private void setHeader(int column, String text) {
        prepareHeader(column);
        if (text != null) {
            DOM.setInnerText(DOM.getChild(headerTr, column), text);
        }
    }
    
    private void prepareHeader(int column) {
        if (column < 0) {
            throw new IndexOutOfBoundsException("Cannot create a column with a negative index: " + column);
        }
        int cellCount = DOM.getChildCount(headerTr);
        int required = column + 1 - cellCount;
        if (required > 0) {
            addCells(head, 0, required);
        }
    }
    
    private void setHeaderWidget(DataGridColumn dataGridColumn, int column, Widget widget, boolean hide, String width) {
        prepareHeader(column);
        if (widget != null) {
            widget.removeFromParent();
            // Physical attach.
            DOM.appendChild(DOM.getChild(headerTr, column), widget.getElement());
            adopt(widget);
            Element th = DOM.getChild(headerTr, DOM.getChildCount(headerTr) - 1);
            if (dataGridColumn != null) {
                dataGridColumn.setTh(th);
            }
            if (hide) {
                th.addClassName("hide");
            }
            if (width != null) {
                th.setAttribute("style", "width:" + width);
            }
            
        }
    }
    
    /**
     * Invokes the <code>getWidget()</code> method of the <code>FlexTable</code>.
     * 
     * @param row is a row number.
     * @param column is a column number.
     * @return an original widget.
     */
    @Override
    public Widget getWidget(int row, int column) {
        return super.getWidget(row, column);
    }
    
    private native void addCells(Element table, int row, int num)/*-{ 
                                                                 var rowElem = table.rows[row];
                                                                 for(var i = 0; i < num; i++){
                                                                 var cell = $doc.createElement("th");
                                                                 rowElem.appendChild(cell);
                                                                 }
                                                                 }-*/;
    
    @Override
    public void addDoubleClickListener(DataGridDoubleClickListener listener) {
        removeDoubleClickListener(listener);
        getDoubleClickListeners().add(listener);
        if (!clickEnabled) {
            DOM.sinkEvents(getElement(), Event.ONCLICK);
            clickEnabled = true;
        }
        doubleClickEnabled = true;
        
    }
    
    public DataGrid addColumn(String key, String name) {
        return addColumn(key, name, false);
    }
    
    public DataGrid addColumn(String key, String name, boolean hide) {
        return addColumn(key, name, hide, true);
    }
    
    public DataGrid addColumn(String key, String name, boolean hide, boolean isSelected) {
        return addColumn(key, name, hide, isSelected, null);
    }
    
    public DataGrid addColumn(String key, String name, boolean hide, boolean isSelected, String width) {
        DataGridColumn dataGridColumn = new DataGridColumn(key, name, dataGridColumns.size(), hide, isSelected, width);
        setHeaderWidget(dataGridColumn, dataGridColumns.size(), dataGridColumn.getWidget(), hide, width);
        if (hide) {
            invisibleColumns.add(dataGridColumn.getIndex());
        }
        if (!isSelected) {
            unSelectedColumns.add(dataGridColumn.getIndex());
        }
        dataGridColumns.add(dataGridColumn);
        return this;
    }
    
    public DataGrid addColumn(String key, Widget widget, boolean hide, boolean isSelected, String width) {
        DataGridColumn dataGridColumn = new DataGridColumn(key, widget, dataGridColumns.size(), hide, isSelected, width);
        setHeaderWidget(dataGridColumn, dataGridColumns.size(), dataGridColumn.getWidget(), hide, width);
        if (hide) {
            invisibleColumns.add(dataGridColumn.getIndex());
        }
        if (!isSelected) {
            unSelectedColumns.add(dataGridColumn.getIndex());
        }
        dataGridColumns.add(dataGridColumn);
        return this;
    }
    
    @Override
    public void removeDoubleClickListener(DataGridDoubleClickListener listener) {
        getDoubleClickListeners().remove(listener);
        if (getDoubleClickListeners().isEmpty()) {
            doubleClickEnabled = false;
        }
        
    }
    
    private DataGridDoubleClickListenerCollection getDoubleClickListeners() {
        return doubleClikcListeners;
    }
    
    /**
     * Fires double click events.
     */
    private void fireDoubleClickEvent() {
        Element td = getCellElement(getCellClicked());
        if (td == null) {
            return;
        }
        Element tr = DOM.getParent(td);
        Element tbody = DOM.getParent(tr);
        if (!("thead").equals(tbody.getNodeName().toLowerCase())) {
            getDoubleClickListeners().fireCellDoubleClicked(this.getDataSource(), DOM.getChildIndex(tbody, tr), DOM.getChildIndex(tr, td));
        }
        setCellClicked(null);
    }
    
    @Override
    public void onBrowserEvent(Event event) {
        
        switch (event.getTypeInt()) {
            
            case Event.ONCLICK: {
                setCellClicked(DOM.eventGetTarget(event));
                if (getClickCount() % 2 == 0 && doubleClickEnabled) {
                    setClickCount(getClickCount() + 1);
                    getClickTimer().schedule(CLICK_TIMEOUT);
                } else if (getClickCount() % 2 == 0 && !doubleClickEnabled) {
                    fireClickEvent();
                } else if (doubleClickEnabled) {
                    setClickCount(0);
                    fireDoubleClickEvent();
                    getClickTimer().cancel();
                }
                break;
            }
                
            case Event.ONMOUSEOVER: {
                Element td = getEventTargetCell(event);
                if (td == null) {
                    return;
                }
                Element tr = DOM.getParent(td);
                Element tBody = DOM.getParent(tr);
                int row = DOM.getChildIndex(tBody, tr);
                if (!("thead").equals(tBody.getNodeName().toLowerCase())) {
                    highlightRow(row);
                }
                
                break;
            }
            case Event.ONMOUSEOUT: {
                Element td = getEventTargetCell(event);
                if (td == null) {
                    return;
                }
                
                Element tr = DOM.getParent(td);
                Element tBody = DOM.getParent(tr);
                int row = DOM.getChildIndex(tBody, tr);
                if (!("thead").equals(tBody.getNodeName().toLowerCase())) {
                    cancelRowHighlighting(row);
                }
                break;
            }
            default: {
                // Do nothing
            }
        }
        
        super.onBrowserEvent(event);
    }
    
    private void highlightRow(int row) {
        if (getRowFormatter().getStyleName(row).equals(GRID_ROW_EVEN_STYLE) || getRowFormatter().getStyleName(row).equals(GRID_ROW_ODD_STYLE))
            getRowFormatter().setStyleName(row, getRowHighlightStyle());
    }
    
    public String getRowHighlightStyle() {
        return GRID_ROW_HIGHLIGHT_STYLE;
    }
    
    private void cancelRowHighlighting(int row) {
        if (getRowFormatter().getStyleName(row).equals(GRID_ROW_HIGHLIGHT_STYLE))
            getRowFormatter().setStyleName(row, getRowStyle(row));
    }
    
    public String getRowStyle(int row) {
        if (row % 2 == 0) {
            return GRID_ROW_EVEN_STYLE;
        } else {
            return GRID_ROW_ODD_STYLE;
        }
    }
    
    private void setCellClicked(Element cell) {
        this.cell = cell;
    }
    
    private int getClickCount() {
        return clickCount;
    }
    
    private void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
    
    /**
     * This timer is invoked if the first click is received bu t the second one isn't till the {@link DataGrid#CLICK_TIMEOUT} exceded.
     * <p/>
     * It drops clicks count and fires onclick event.
     */
    private class ClickTimer extends Timer {
        
        @Override
        public void run() {
            setClickCount(0);
            
        }
    }
    
    /**
     * Fires click events.
     */
    private void fireClickEvent() {
        Element td = getCellElement(getCellClicked());
        if (td == null) {
            return;
        }
        Element tr = DOM.getParent(td);
        Element tbody = DOM.getParent(tr);
        
        if (!("thead").equals(tbody.getNodeName().toLowerCase())) {
            for (DataGridSelectRowListener dataGridSelectRowListener : getSelectRowListeners()) {
                if (!unSelectedColumns.contains(DOM.getChildIndex(tr, td))) {
                    dataGridSelectRowListener.onSelect(this.getDataSource(), DOM.getChildIndex(tbody, tr), DOM.getChildIndex(tr, td));
                }
            }
            
        }
        setCellClicked(null);
    }
    
    protected Timer getClickTimer() {
        return clickTimer;
    }
    
    protected void setClickTimer(Timer clickTimer) {
        this.clickTimer = clickTimer;
    }
    
    private Element getCellClicked() {
        return cell;
    }
    
    /**
     * Searches for the td element strting from the clicked element to upper levels of the DOM tree.
     * 
     * @param clickElement is an element that is clicked.
     * @return a found element or <code>null</code> if the clicked element is not the td tag and not nested into any td.
     */
    private Element getCellElement(Element clickElement) {
        while (clickElement != null && !"td".equalsIgnoreCase(clickElement.getTagName())) {
            clickElement = DOM.getParent(clickElement);
        }
        
        if (clickElement == null) {
            return null;
        }
        
        Element tr = DOM.getParent(clickElement);
        Element tbody = DOM.getParent(tr);
        Element table = DOM.getParent(tbody);
        
        if (getElement().equals(table)) {
            return clickElement;
        } else {
            return getCellElement(table);
        }
    }
    
    /**
     * This method retuns a list of select row listeners.
     * 
     * @return a list of the listeners.
     */
    private List<DataGridSelectRowListener> getSelectRowListeners() {
        if (selectRowListeners == null) {
            selectRowListeners = new ArrayList<DataGridSelectRowListener>();
        }
        return selectRowListeners;
    }
    
    public DataSource getDataSource() {
        return dataSource;
    }
    
    private void drawContent() {
        int end = dataSource.getEndRow();
        int start = 0;
        boolean empty = dataSource.isEmpty();
        
        if (!empty && getRowDrawHandler() != null) {
            DeferredCommand.addCommand(new DrawRowCommand(start, end));
        } else {
            for (int i = start; !empty && i <= end; i++) {
                drawRow(dataSource.getRowData(i), i);
            }
        }
        
        for (int i = getRowCount() - 1; i >= end - start + 1; i--) {
            removeRow(i);
        }
        
    }
    
    private void drawRow(Object[] data, int row) {
        if (row < 0) {
            row = 0;
        }
        for (int i = 0; i < data.length; i++) {
            drawCell(data[i], row, i);
            
        }
        
        if (row % 2 == 0) {
            getRowFormatter().setStyleName(row, "even");
        } else {
            getRowFormatter().setStyleName(row, "odd");
        }
    }
    
    private void drawCell(Object data, int row, int column) {
        setWidget(row, column, new CellWidget(data, row, column));
        if (invisibleColumns.contains(column)) {
            getCellFormatter().setVisible(row, column, false);
        }
        
    }
    
    /**
     * Getter for property 'invisibleColumns'.
     * 
     * @return Value for property 'invisibleColumns'.
     */
    public List<Integer> getInvisibleColumns() {
        if (invisibleColumns == null) {
            invisibleColumns = new ArrayList<Integer>();
        }
        return invisibleColumns;
    }
    
    /**
     * This method returns a row draw handler instance.
     * 
     * @return a link to the handler.
     */
    public DataGridDrawRowHandler getRowDrawHandler() {
        return rowDrawHandler;
    }
    
    /**
     * This method fires row drawing event before the row is drawn.
     * 
     * @param row is a row number.
     * @param pageSize is a page size.
     * @param data is row data.
     * @return <code>true</code> if the row must be drawn.
     */
    private boolean fireBeforeDrawEvent(int row, Object[] data) {
        DataGridDrawRowHandler handler = getRowDrawHandler();
        return handler == null || handler.beforeDraw(row, this, data);
    }
    
    /**
     * This method sets a row draw handler for this grid.
     * 
     * @param rowDrawHandler a row draw handler instance.
     */
    public void addDrawRowHandler(DataGridDrawRowHandler rowDrawHandler) {
        if (rowDrawHandler != null) {
            this.rowDrawHandler = rowDrawHandler;
        }
    }
    
    /**
     * This method fires row drawing event after the row is drawn.
     * 
     * @param row is a row number.
     * @param pageSize is a page size.
     * @param data is row data.
     * @return <code>true</code> if drawing must be continued.
     */
    private boolean fireAfterDrawEvent(int row, Object[] data) {
        DataGridDrawRowHandler handler = getRowDrawHandler();
        return handler == null || handler.afterDraw(row, this, data);
    }
    
    /**
     * Gets a currently selected row number
     * <p/>
     * If there are several rows selected it returns only the first row number.
     * 
     * @return a selected row number.
     */
    public int getCurrentRow() {
        return getSelectionModel().firstIndex();
    }
    
    /**
     * Gets a currently selected grid row.
     * <p/>
     * If there are several rows selected it returns only the first row.
     * 
     * @return a current grid row.
     */
    public DataGridRow getCurrentGridRow() {
        return getSelectionModel().firstRow();
    }
    
    /**
     * Gets a list of selected row numbers.
     * 
     * @return a list of selected row numbers.
     */
    public int[] getCurrentRows() {
        return getSelectionModel().getIndexes();
    }
    
    /**
     * Gets a list of selected grid rows.
     * 
     * @return a list of selected grid rows.
     */
    public DataGridRow[] getCurrentGridRows() {
        return getSelectionModel().getGridRows();
    }
    
    /**
     * Gets a selection model.
     * 
     * @return a selection model.
     */
    public GridRowSelectDataModel getSelectionModel() {
        if (selectionModel == null) {
            selectionModel = new GridRowSelectDataModel();
        }
        return selectionModel;
    }
    
    /**
     * Sets the selection model.
     * 
     * @param selectionModel is a row selection model.
     */
    public void setSelectionModel(GridRowSelectDataModel selectionModel) {
        this.selectionModel = selectionModel;
    }
    
    private class DrawRowCommand implements IncrementalCommand {
        
        /** start row number */
        private int start;
        /** end row number */
        private int end;
        /** current row number */
        private int current;
        
        /**
         * Creates an instance of this class and initialiazes internal variables.
         * 
         * @param start is a start row number.
         * @param end is an end row number.
         */
        public DrawRowCommand(int start, int end) {
            this.start = start;
            this.end = end;
            this.current = this.start;
            
        }
        
        /**
         * This method stops when the end row reached.
         * 
         * @return <code>true</code> if drawing must be continued.
         */
        @Override
        public boolean execute() {
            int row = getCurrent() - getStart();
            
            boolean cont = false;
            for (int i = 0; getCurrent() <= getEnd() && i < 1; i++) {
                Object[] data = getDataSource().getRowData(getCurrent());
                cont = fireBeforeDrawEvent(row, data);
                
                if (cont) {
                    drawRow(data, row);
                    cont = fireAfterDrawEvent(row, data);
                }
                
                if (!cont) {
                    break;
                }
                setCurrent(getCurrent() + 1);
            }
            
            return cont && getCurrent() <= getEnd();
        }
        
        /**
         * Getter for property 'start'.
         * 
         * @return Value for property 'start'.
         */
        private int getStart() {
            return start;
        }
        
        /**
         * Getter for property 'end'.
         * 
         * @return Value for property 'end'.
         */
        private int getEnd() {
            return end;
        }
        
        /**
         * Setter for property 'current'.
         * 
         * @param current Value to set for property 'current'.
         */
        public void setCurrent(int current) {
            this.current = current;
        }
        
        /**
         * Getter for property 'current'.
         * 
         * @return Value for property 'current'.
         */
        private int getCurrent() {
            return current;
        }
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.dataSource.setListener(new EditableModelListener() {
            
            @Override
            public void onSetSource() {
                drawContent();
            }
            
            @Override
            public void onModelEvent(EditableModelEvent event) {
                if (event.getEventType().equals(EditableModelEvent.UPDATE_ROW)) {
                    drawRow(event.getSource().getRowData(event.getRow()), event.getRow());
                } else if (event.getEventType().equals(EditableModelEvent.REMOVE_ROW)) {
                    removeRow(event.getRow());
                    updateCssRows();
                } else if (event.getEventType().equals(EditableModelEvent.CLEAN)) {
                    removeAllRows();
                } else if (event.getEventType().equals(EditableModelEvent.ADD_ROW)) {
                    drawRow(event.getSource().getRowData(event.getRow()), event.getRow());
                } else if (event.getEventType().equals(EditableModelEvent.UPDATE_ALL)) {
                    drawContent();
                } else if (event.getEventType().equals(EditableModelEvent.ADD_ROW_BEFORE)) {
                    int beforeRow = event.getRow();
                    if (beforeRow < 0) {
                        beforeRow = 0;
                    }
                    insertRow(beforeRow);
                    drawRow(event.getSource().getRowData(event.getRow()), beforeRow);
                }
                
            }
        });
    }
    
    private void updateCssRows() {
        int end = dataSource.getEndRow();
        boolean empty = dataSource.isEmpty();
        for (int i = 0; !empty && i <= end; i++) {
            if (i % 2 == 0) {
                getRowFormatter().setStyleName(i, "even");
            } else {
                getRowFormatter().setStyleName(i, "odd");
            }
        }
    }
    
    public void addStyleRow(int row, String style, boolean add) {
        if (add) {
            getRowFormatter().addStyleName(row, style);
        } else {
            getRowFormatter().removeStyleName(row, style);
        }
    }
    
    /**
     * This method adds a selected row listener into the list.
     * 
     * @param selectRowListener is a select row listener to be added.
     */
    @Override
    public void addSelectRowListener(DataGridSelectRowListener selectRowListener) {
        removeSelectRowListener(selectRowListener);
        if (!clickEnabled) {
            DOM.sinkEvents(getElement(), Event.ONCLICK);
            clickEnabled = true;
        }
        getSelectRowListeners().add(selectRowListener);
    }
    
    /**
     * This method removes a selected row listener from the list.
     * 
     * @param selectRowListener a select row listener to be removed.
     */
    @Override
    public void removeSelectRowListener(DataGridSelectRowListener selectRowListener) {
        getSelectRowListeners().remove(selectRowListener);
        
    }
}
