package com.gwt.ui.client.draggablesupertable;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

/**
 * Class managing the coordinates of a widget in a FlexTable
 * @author ibouakl
 */
public class CoordFlexTable {

    private int row;
    private int col;
    public static final int UNDEFINED_COORD_INDEX = -1;
    private static final int COPY_MARGIN_PX = 7;
    public static final CoordFlexTable UNDEFINED_COORD = new CoordFlexTable();

    /**
     * Instantiation of an undefined coord object
     */
    public CoordFlexTable() {
        this(UNDEFINED_COORD_INDEX, UNDEFINED_COORD_INDEX);
    }

    /**
     * Initialization of the coordinates for a widget in the FlexTable
     * @param int row
     * @param int column
     */
    public CoordFlexTable(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Initialization of the coordinates for a widget in the FlexTable
     * @param coord : coordinates to be used for initialization
     */
    public CoordFlexTable(CoordFlexTable coord) {
        if (coord == null) {
            this.row = UNDEFINED_COORD_INDEX;
            this.col = UNDEFINED_COORD_INDEX;
        } else {
            this.row = coord.getRow();
            this.col = coord.getCol();
        }
    }

    /**
     * Gets the row index from the FlexTable
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index from the FlexTable
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets the column index
     * @param col
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Sets the row index
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Comparison between two CoordFlexTable objects
     * @param other - CoordFlexTable : object to compare with
     * @return true if objects are equals
     */
    public boolean equals(CoordFlexTable other) {
        if (this == null) {
            return (other == null);
        } else if (other != null) {
            return ((this.getRow() == other.getRow())
                    && (this.getCol() == other.getCol()));
        } else {
            return false;
        }
    }

    /**
     * Returns the coordinates under the following form : "(row,col)"
     */
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }

    /**
     * Looks for FlexTable cell associated to a mouse or keyboard event
     * @param ftContent : FlexTable to be analyzed
     * @param Event : the current event
     * @return CoordFlexTable structure with the row and column indexes of the
     *                        target cell. {-1,-1} if target is not a cell of the FlexTable
     */
    public static CoordFlexTable getCoordTarget(FlexTable ftContent, Event event) {
        // For mouse events, event position gives better results than EventTarget
        for (int i = 0; i < ftContent.getRowCount(); i++) {
            for (int j = 0; j < ftContent.getCellCount(i); j++) {
                if (ftContent.getCellFormatter().getElement(i, j) != null) {
                    // For each cell, take in account its coordinates in the browser
                    int xMin = ftContent.getCellFormatter().getElement(i, j).getAbsoluteLeft();
                    int xMax = xMin + ftContent.getCellFormatter().getElement(i, j).getOffsetWidth();
                    int yMin = ftContent.getCellFormatter().getElement(i, j).getAbsoluteTop();
                    int yMax = yMin + ftContent.getCellFormatter().getElement(i, j).getOffsetHeight();

                    // Also take in account the browser's scroll status
                    int scrollX = -event.getClientX();
                    int scrollY = -event.getClientY();

                    // Compare to the event coordinates
                    int xEvt = event.getClientX();
                    int yEvt = event.getClientY();
                    if ((xEvt >= xMin + scrollX) && (xEvt <= xMax + scrollX)) {
                        if ((yEvt >= yMin + scrollY) && (yEvt <= yMax + scrollY)) {
                            return new CoordFlexTable(i, j);
                        }
                    }
                }
            }
        }

        // For keyboard events, EventTarget must be used !
        Element target = Element.as(event.getEventTarget());
        for (int i = 0; i < ftContent.getRowCount(); i++) {
            for (int j = 0; j < ftContent.getCellCount(i); j++) {
                Widget wid = ftContent.getWidget(i, j);
                if (wid != null) {
                    if (wid.getElement().equals(target)) {
                        return (new CoordFlexTable(i, j));
                    }
                }
            }
        }

        return new CoordFlexTable();
    }

    /**
     * The bottom right angle of the widget can allow specific action
     * like copying by drag
     * The copying area is defined with a margin of some pixels inside the widget
     * @param ftContent : Flextable parent
     * @param coord : coordinates of the cell to be checked
     * @param event : the current event (mouse event only !)
     * @return : true if the mouse pointer is in the copying area of the widget
     */
    public static boolean isCopyAvailable(
            FlexTable ftContent, CoordFlexTable coord, Event event) {
        Widget wid = ftContent.getWidget(coord.getRow(), coord.getCol());
        if (wid != null) {
            // Gets the total coordinates of the widget
            int xMin = wid.getAbsoluteLeft();
            int xMax = xMin + wid.getOffsetWidth();
            int yMin = wid.getAbsoluteTop();
            int yMax = yMin + wid.getOffsetHeight();

            // Computes the coordinates of the copy area
            // This area is at the right bottom angle
            // taking a margin of some pixels
            xMin = xMax - COPY_MARGIN_PX;
            yMin = yMax - COPY_MARGIN_PX;

            // Also take in account the browser's scroll status
            int scrollX = -event.getClientX();
            int scrollY = -event.getClientY();

            // Compare to the event coordinates with
            int xEvt = event.getClientX();
            int yEvt = event.getClientY();
            if ((xEvt >= xMin + scrollX) && (xEvt <= xMax + scrollX)) {
                if ((yEvt >= yMin + scrollY) && (yEvt <= yMax + scrollY)) {
                    return true;
                }
            }
        }
        return false;
    }
}
