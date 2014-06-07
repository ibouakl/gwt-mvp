package com.gwt.ui.client.masterview;

import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class GridRenderer {
    
    private static final String GRID_STYLE = "masterview-grid";
    private static final String GRID_HEADER_STYLE = "masterview-grid-header";
    private static final String GRID_ROW_HIGHLIGHT_STYLE = "masterview-grid-row-highlight";
    private static final String GRID_ROW_EVEN_STYLE = "masterview-grid-row-even";
    private static final String GRID_ROW_ODD_STYLE = "masterview-grid-row-odd";
    private static final String GRID_HEADER_CELL_STYLE = "masterview-grid-header-cell";
    private static final String GRID_ROW_CELL_STYLE = "masterview-grid-row-cell";
    private static final String GRID_CELL_ASCENDING_SORTING_STYLE = "masterview-grid-sorted-asc";
    private static final String GRID_CELL_DESCENDING_SORTING_STYLE = "masterview-grid-sorted-desc";
    private static final String GRID_FILTER_ROW_STYLE = "masterview-grid-filter";
    private static final String GRID_FILTER_TEXTBOX_STYLE = "masterview-grid-filter-textbox";
    private final DataSource dataSource;
    private final Grid grid;
    
    public GridRenderer(DataSource dataSource, Grid grid) {
        this.dataSource = dataSource;
        this.grid = grid;
    }
    
    public String getRowStyle(int row) {
        if (row % 2 == 0) {
            return GRID_ROW_EVEN_STYLE;
        } else {
            return GRID_ROW_ODD_STYLE;
        }
    }
    
    public void highlightRow(int rowNumber) {
        if (rowNumber >= grid.getFirstRowWithDataNumber()) {
            if (!(grid.isSelectionEnabled() && grid.getSelectedRow() == rowNumber))
                grid.getRowFormatter().setStyleName(rowNumber, getRowHighlightStyle());
        }
    }
    
    public void cancelRowHighlighting(int rowNumber) {
        if (rowNumber >= grid.getFirstRowWithDataNumber()) {
            if (!grid.isSelectionEnabled()) {
                grid.getRowFormatter().setStyleName(rowNumber, getRowStyle(rowNumber));
            } else {
                if (grid.getSelectedRow() != rowNumber)
                    grid.getRowFormatter().setStyleName(rowNumber, getRowStyle(rowNumber));
                
            }
            
        }
    }
    
    @SuppressWarnings("unchecked")
    public void renderHeader(List columns) {
        int baseColumn = 0;
        
        for (int i = 0; i < columns.size(); i++) {
            Column column = (Column)columns.get(i);
            if (!column.isWidget()) {
                grid.setText(0, baseColumn + i, column.getTitle());
            } else {
                grid.setWidget(0, baseColumn + i, column.getWidget());
            }
            grid.getCellFormatter().setStyleName(0, baseColumn + i, GRID_HEADER_CELL_STYLE);
            
            if (!column.getWidth().equals("")) {
                grid.getCellFormatter().setWidth(0, baseColumn + i, column.getWidth());
            }
        }
        
        grid.getRowFormatter().setStyleName(0, getHeaderStyle());
    }
    
    @SuppressWarnings("unchecked")
    public void renderFilters(List columns) {
        int baseColumn = 0;
        for (int i = 0; i < columns.size(); i++) {
            final Column column = (Column)columns.get(i);
            final TextBox filterTextBox = new TextBox();
            filterTextBox.setWidth("100%");
            filterTextBox.setText("Filter is not set...");
            filterTextBox.addFocusHandler(new FocusHandler() {
                
                @Override
                public void onFocus(FocusEvent arg0) {
                    if (filterTextBox.getText().equals("Filter is not set...")) {
                        filterTextBox.setText("");
                    }
                }
            });
            filterTextBox.addBlurHandler(new BlurHandler() {
                
                @Override
                public void onBlur(BlurEvent arg0) {
                    if (filterTextBox.getText().matches("[*]*")) {
                        filterTextBox.setText("Filter is not set...");
                        grid.removeFilter(column);
                    } else {
                        grid.applyFilter(column, filterTextBox.getText());
                    }
                }
            });
            filterTextBox.addValueChangeHandler(new ValueChangeHandler<String>() {
                
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    if (filterTextBox.getText().matches("[*]*")) {
                        grid.removeFilter(column);
                    } else {
                        grid.applyFilter(column, filterTextBox.getText());
                    }
                }
            });
            filterTextBox.setStyleName(getFilterTextBoxStyle());
            if (column.hideFiltre) {
                filterTextBox.setVisible(false);
            }
            grid.setWidget(1, baseColumn + i, filterTextBox);
            /*grid.getCellFormatter().setStyleName(0, baseColumn + i, GRID_HEADER_CELL_STYLE);*/

            if (!column.getWidth().equals("")) {
                grid.getCellFormatter().setWidth(1, baseColumn + i, column.getWidth());
            }
        }
        
        grid.getRowFormatter().setStyleName(1, getFilterStyle());
    }
    
    private String getFilterTextBoxStyle() {
        return GRID_FILTER_TEXTBOX_STYLE;
        
    }
    
    public String getFilterStyle() {
        return GRID_FILTER_ROW_STYLE;
    }
    
    @SuppressWarnings("unchecked")
    protected void renderEmptyBody(List columns) {
        
        grid.setText(grid.getFirstRowWithDataNumber(), 0, "");
        grid.getRowFormatter().setStyleName(grid.getFirstRowWithDataNumber(), getRowStyle(1));
        grid.getFlexCellFormatter().setColSpan(grid.getFirstRowWithDataNumber(), 0, columns.size());
    }
    
    @SuppressWarnings("unchecked")
    public int renderItems(List columns, DataSource dataSource) {
        if (dataSource.isEmpty()) {
            renderEmptyBody(columns);
            return 1;
        }
        int baseColumn = 0;
        
        int itemsCount = dataSource.getDisplayData().size();
        int rowNumber = grid.getFirstRowWithDataNumber();
        
        for (int itemNumber = 0; itemNumber < itemsCount; itemNumber++) {
            Object item = dataSource.getDisplayData().get(itemNumber);
            
            for (int columnNumber = 0; columnNumber < columns.size(); columnNumber++) {
                Column column = (Column)columns.get(baseColumn + columnNumber);
                
                Object cellContent = dataSource.getPropertyMapper().getProperty(item, column.getPropertyName());
                if (cellContent instanceof Widget) {
                    grid.setWidget(rowNumber, baseColumn + columnNumber, (Widget)cellContent);
                } else {
                    grid.setText(rowNumber, baseColumn + columnNumber, cellContent.toString());
                }
                
                grid.getCellFormatter().setStyleName(rowNumber, baseColumn + columnNumber, GRID_ROW_CELL_STYLE);
                
                if (!column.getWidth().equals("")) {
                    grid.getCellFormatter().setWidth(rowNumber, baseColumn + columnNumber, column.getWidth());
                }
            }
            
            grid.getRowFormatter().setStyleName(rowNumber, getRowStyle(rowNumber));
            rowNumber++;
        }
        
        return itemsCount;
    }
    
    /** override this for cutomization */
    public String getGridStyle() {
        return GRID_STYLE;
    }
    
    /** override this for cutomization */
    public String getHeaderStyle() {
        return GRID_HEADER_STYLE;
    }
    
    /** override this for cutomization */
    public String getRowHighlightStyle() {
        return GRID_ROW_HIGHLIGHT_STYLE;
    }
    
    /** override this for cutomization */
    public String getCellAscendingSortingStyle() {
        return GRID_CELL_ASCENDING_SORTING_STYLE;
    }
    
    /** override this for cutomization */
    public String getHeaderCellStyle() {
        return GRID_HEADER_STYLE;
    }
    
    /** override this for cutomization */
    public String getCellDescendingSortingStyle() {
        return GRID_CELL_DESCENDING_SORTING_STYLE;
    }
}
