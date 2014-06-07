package com.gwt.ui.client.masterview;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;

public class Grid extends FlexTable implements HasDoubleClickHandlers {
    
    private int sortedColumn = -1;
    private String sortType;
    private final List<Column> columns = new ArrayList<Column>();
    private boolean filteringEnabled;
    private final DataSource dataSource;
    private final GridRenderer renderer;
    private boolean selectionEnabled = false;
    private int selectedRow = -1;
    
    /**
     * Public constructor for the grid.
     * 
     * @param dataSource source of the initialData items for the grid to render.
     */
    @SuppressWarnings("unchecked")
    public Grid(DataSource dataSource) {
        this.dataSource = dataSource;
        dataSource.addDataSourceListener(new DataSourceListener() {
            
            @Override
            public void onDataChanged(int readItemsCount, int allItemsCount) {
                renderItems();
            }
        });
        renderer = new GridRenderer(dataSource, this);
        setStyleName(renderer.getGridStyle());
        
        sortType = SortConstants.NO_SORTING;
        super.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = getCellForEvent(event);
                if (cell.getRowIndex() >= 1) {
                    return;
                }
                sortColumn(cell.getCellIndex());
            }
        });
        sinkEvents(Event.ONMOUSEOVER);
        sinkEvents(Event.ONMOUSEOUT);
        addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                if (selectionEnabled) {
                    int row = getCellForEvent(event).getRowIndex();
                    if (row >= getFirstRowWithDataNumber()) {
                        if (selectedRow >= getFirstRowWithDataNumber()) {
                            getRowFormatter().removeStyleName(selectedRow, "gwtcomp-SuperTableRowSelected");
                        }
                        selectedRow = row;
                        getRowFormatter().setStyleName(selectedRow, "gwtcomp-SuperTableRowSelected");
                        
                    }
                    
                }
                
            }
        });
    }
    
    /**
     * Renders the grid.
     */
    public void render() {
        renderer.renderHeader(columns);
        if (isFilteringEnabled()) {
            renderer.renderFilters(columns);
        }
        renderItems();
    }
    
    public void renderItems() {
        int rowNumber = getFirstRowWithDataNumber();
        int rowsForItems = renderer.renderItems(columns, dataSource);
        int neededRowsCount = rowNumber + rowsForItems;
        deleteExcessRows(neededRowsCount);
    }
    
    /**
     * <p>
     * Deletes redundant rows left from previous rendering (if necessary).
     * </p>
     * 
     * @param neededRowsCount
     */
    protected void deleteExcessRows(int neededRowsCount) {
        int rowsToDelete = getRowCount() - neededRowsCount;
        while (rowsToDelete > 0) {
            removeRow(getRowCount() - 1);
            rowsToDelete--;
        }
    }
    
    public int getFirstRowWithDataNumber() {
        if (isFilteringEnabled()) {
            return 2;
        } else {
            return 1;
        }
    }
    
    /**
     * <p>
     * Method is overriden to render the grid after loading.
     * </p>
     */
    @Override
    protected void onLoad() {
        render();
    }
    
    @Override
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
                int row = DOM.getChildIndex(body, tr);
                
                renderer.highlightRow(row);
                
                break;
            }
            case Event.ONMOUSEOUT: {
                Element td = getEventTargetCell(event);
                if (td == null) {
                    return;
                }
                
                Element tr = DOM.getParent(td);
                Element body = DOM.getParent(tr);
                int row = DOM.getChildIndex(body, tr);
                
                renderer.cancelRowHighlighting(row);
                break;
            }
            default: {
                // Do nothing
            }
        }
    }
    
    public void append(Column column) {
        columns.add(column);
    }
    
    public void sortColumn(int columnIndex) {
        if (sortedColumn != -1) {
            getCellFormatter().setStyleName(0, sortedColumn, renderer.getHeaderCellStyle());
        }
        Column columnToSort = (Column)columns.get(columnIndex);
        if (!columnToSort.isSortable()) {
            return;
        }
        dataSource.setPropertyToSort(columnToSort.getPropertyName());
        if (sortType.equals(SortConstants.NO_SORTING)) {
            getCellFormatter().setStyleName(0, columnIndex, renderer.getCellAscendingSortingStyle());
            sortType = SortConstants.SORT_ASC;
            dataSource.setSortType(SortConstants.SORT_ASC);
        } else if (sortType.equals(SortConstants.SORT_ASC)) {
            getCellFormatter().removeStyleName(0, sortedColumn, renderer.getCellAscendingSortingStyle());
            getCellFormatter().setStyleName(0, columnIndex, renderer.getCellDescendingSortingStyle());
            sortType = SortConstants.SORT_DESC;
            dataSource.setSortType(SortConstants.SORT_DESC);
        } else if (sortType.equals(SortConstants.SORT_DESC)) {
            getCellFormatter().removeStyleName(0, sortedColumn, renderer.getCellDescendingSortingStyle());
            sortType = SortConstants.NO_SORTING;
            dataSource.setSortType(SortConstants.NO_SORTING);
        }
        sortedColumn = columnIndex;
    }
    
    /**
     * <p>
     * Applies a filter (just a plain expression string) to a column.
     * </p>
     * 
     * @param column a column to which a filter will be applied.
     * @param text a filter expression to apply.
     */
    public void applyFilter(Column column, String text) {
        dataSource.clearFiltersByProperty(column.getPropertyName());
        dataSource.addFilter(new Filter(column.getPropertyName(), text));
    }
    
    public void removeFilter(Column column) {
        dataSource.clearFiltersByProperty(column.getPropertyName());
    }
    
    public boolean isFilteringEnabled() {
        return filteringEnabled;
    }
    
    public void setFilteringEnabled(boolean filteringEnabled) {
        this.filteringEnabled = filteringEnabled;
    }
    
    public DataSource getDataSource() {
        return dataSource;
    }
    
    public int getRowDataCount() {
        return dataSource.isEmpty() ? 0 : getRowCount();
    }
    
    public boolean isSelectionEnabled() {
        return selectionEnabled;
    }
    
    public void setSelectionEnabled(boolean selectionEnabled) {
        this.selectionEnabled = selectionEnabled;
    }
    
    public int getSelectedRow() {
        return selectedRow;
    }
    
    @Override
    public HandlerRegistration addDoubleClickHandler(DoubleClickHandler handler) {
        return addDomHandler(handler, DoubleClickEvent.getType());
    }
    
}
