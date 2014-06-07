package com.gwt.ui.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;

public class SimpleFlexTable extends FlexTable {
    
    private static final transient int HEADER_ROW_INDEX = 0;
    private int nextRow = 0;
    private final String styleName;
    private final String headerStyleName;
    private ColumnHeading[] columnHeadings;
    private SimpleToolbar toolbar = null;
    
    /**
     * Build a new instance of <code>SimpleFlexTable</code>.
     */
    public SimpleFlexTable() {
        this(null, null);
    }
    
    /**
     * Build a new instance of <code>SimpleFlexTable</code>.
     * 
     * @param styleName
     */
    public SimpleFlexTable(final String styleName) {
        this(styleName, null);
    }
    
    /**
     * Build a new instance of <code>SimpleFlexTable</code>.
     * 
     * @param styleName
     * @param headerStyleName
     */
    public SimpleFlexTable(final String styleName, final String headerStyleName) {
        super();
        this.styleName = styleName;
        this.headerStyleName = headerStyleName;
        columnHeadings = null;
        init();
    }
    
    /**
     * The Add Column method take a list of ColumnHeading. Each ColumnHeading contains a widget and a styleName. That method subsequently
     * Place the widget in a table cell in the table's first row with the FlexTable setWidget method. The setWidget method method take a
     * row,a column, and a widget.We take from the columnHeading the widget suitable for the flex table's setWidget method with the method
     * getWidget(). The addHeading method Also Add CSS Style to each new column into the FlexTable.
     * 
     * @param columnHeadings
     */
    public void addHeading(ColumnHeading... columnHeadings) {
        this.columnHeadings = columnHeadings;
        int columnIndex = 0;
        for (ColumnHeading columnHeading : columnHeadings) {
            setWidget(HEADER_ROW_INDEX, columnIndex, columnHeading.getWidget());
            if (columnHeading.getStyleName() != null) {
                getColumnFormatter().addStyleName(columnIndex, columnHeading.getStyleName());
            }
            columnIndex++;
        }
        nextRow = 1;
    }
    
    public void setTopToolbar(SimpleToolbar toolbar) {
        if (nextRow == 1 && toolbar != null) {
            this.toolbar = toolbar;
            insertRow(0);
            setWidget(0, 0, toolbar);
            getFlexCellFormatter().setColSpan(0, 0, columnHeadings.length);
            getFlexCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_BOTTOM);
            getRowFormatter().addStyleName(0, "ToolbarPanelColor"); 
            nextRow = 2;
        }
    }
    
    /**
     * return the number of columns into the FlexTable.
     * 
     * @return column count.
     */
    public int getColumnCount() {
        if (toolbar == null)
            return getRowCount() > 0 ? getCellCount(HEADER_ROW_INDEX) : 0;
        else
            return getRowCount() > 0 ? getCellCount(HEADER_ROW_INDEX + 1) : 0;
    }
    
    /**
     * After we've created and populated the header row, we iterate over the row data and add more rows to the table. this method takes a
     * list of widget that it places inside each subsequent row, where each widget in the list is placed in a table cell in the specified
     * row. setWidget() Method creates a row if no row corresponds to the specified row index.
     * 
     * @param widgets
     * @return
     */
    public int addRow(Widget... widgets) {
        int rowIndex = nextRow;
        for (int i = 0; i < widgets.length; i++) {
            setWidget(rowIndex, i, widgets[i]);
        }
        getRowFormatter().addStyleName(rowIndex, "SimpleFlexTable-Row");
        nextRow++;
        return rowIndex;
    }
    
    public void addAttributeToRow(final int rowIndex, final String attributeName, final String attributeValue) {
        getRowFormatter().getElement(rowIndex).setAttribute(attributeName, attributeValue);
    }
    
    public void clearData() {
        removeAllRows();
        clear();
        init();
        addHeading(columnHeadings);
        setTopToolbar(toolbar);
 
        
    }
    
    private void init() {
        if (styleName != null && !"".equals(styleName)) {
            addStyleName(styleName);
        }
        if (headerStyleName != null && !"".equals(headerStyleName)) {
            getRowFormatter().addStyleName(HEADER_ROW_INDEX, headerStyleName);
        }
    }
    
}
