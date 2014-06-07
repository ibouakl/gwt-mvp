package com.gwt.ui.client.draggablesupertable;

import java.util.ArrayList;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.ui.client.supertable.SuperTable;

public class DraggableSuperTable extends SuperTable implements HasValueChangeHandlers<InfoDragFlexTable> {
    
    private HandlerRegistration hEventPere = null;
    private boolean isDragging = false;
    // Popup and FlexTable used for drag and drop object
    PopupPanel pDrag = new PopupPanel();
    FlexTable ftDrag = new FlexTable();
    private CoordFlexTable coordDrag = new CoordFlexTable();
    private int colDragButton = CoordFlexTable.UNDEFINED_COORD_INDEX;
    private int rowSource = CoordFlexTable.UNDEFINED_COORD_INDEX;
    private ArrayList<Boolean> enabled = null;
    private int rowDest = CoordFlexTable.UNDEFINED_COORD_INDEX;
    
    public DraggableSuperTable() {
        super();
        
        // Give the desired style for the popup used when dragging a row
        pDrag.setStyleName("SYST_DragnDrop");
        // Disable animation because of a GWT issue : when animation is enabled,
        // it's impossible to move the popup !!!
        pDrag.setAnimationEnabled(false);
        colDragButton = CoordFlexTable.UNDEFINED_COORD_INDEX;
        isDragging = false;
        coordDrag = new CoordFlexTable();
        
        // Initialization of the popup row
        pDrag.setWidget(ftDrag);
        pDrag.hide();
        
        // Used events
        sinkEvents(Event.MOUSEEVENTS | Event.ONCONTEXTMENU);
    }
    
    public void onBrowserEvent(Event event) {
        
        // Disallow the default context menu in order to manage right-click
        if (event.getTypeInt() == Event.ONCONTEXTMENU) {
            event.stopPropagation();
            event.preventDefault();
            return;
        }
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
                
            case Event.ONMOUSEDOWN: {
                if (!isDragging) {
                    // Take into account only if right-click or if the left-click
                    // is on a dedicated widget
                    if (!coordDrag.equals(CoordFlexTable.UNDEFINED_COORD)) {
                        if ((coordDrag.getCol() == colDragButton) || (event.getButton() == Event.BUTTON_RIGHT)) {
                            isDragging = true;
                            rowSource = coordDrag.getRow();
                            setDragStyleCursor(true);
                            
                            // Remove the dragged row from the FlexTable and show it into a popup
                            ftDrag.clear();
                            pDrag.remove(ftDrag);
                            for (int i = 0; i < this.getCellCount(coordDrag.getRow()); i++)
                                if (this.getWidget(coordDrag.getRow(), i) != null) {
                                    Widget wid = this.getWidget(coordDrag.getRow(), i);
                                    wid.addStyleName("SYST_NoWrap");
                                    int width = wid.getElement().getOffsetWidth();
                                    int height = wid.getElement().getOffsetHeight();
                                    ftDrag.setCellPadding(this.getCellPadding());
                                    ftDrag.setCellSpacing(this.getCellSpacing());
                                    ftDrag.setWidget(0, i, wid);
                                    if (width > 0)
                                        ftDrag.getCellFormatter().setWidth(0, i, "" + width + "px");
                                    if (height > 0)
                                        ftDrag.getCellFormatter().setHeight(0, i, "" + height + "px");
                                    ftDrag.getFlexCellFormatter().setColSpan(0, i, this.getFlexCellFormatter().getColSpan(coordDrag.getRow(), i));
                                    ftDrag.getFlexCellFormatter().setRowSpan(0, i, this.getFlexCellFormatter().getRowSpan(coordDrag.getRow(), i));
                                    if (this.getCellFormatter().getStyleName(coordDrag.getRow(), i) != null)
                                        ftDrag.getCellFormatter().setStyleName(0, i, this.getCellFormatter().getStyleName(coordDrag.getRow(), i));
                                }
                            this.removeRow(coordDrag.getRow());
                            // Memorize enable status for each widget
                            // Be sure to call it after removing row
                            getEnabled();
                            // Set enabled false on all the FlexTable to avoid
                            // any unexpected action during dragging
                            setEnabled(false);
                            addLineDragStyle(coordDrag.getRow());
                            pDrag.setWidget(ftDrag);
                            pDrag.setPopupPosition(event.getClientX() + 5 + event.getClientX(), event.getClientY() + 5 + event.getClientY());
                            pDrag.show();
                        }
                    }
                }
                break;
                
            }
                
            case Event.ONMOUSEUP: {
                if (isDragging) {
                    isDragging = false;
                    rowDest = coordDrag.getRow();
                    removeLineDragStyle(coordDrag.getRow());
                    setDragStyleCursor(false);
                    
                    // Reset enabled widget
                    // Be sure to call it before inserting row
                    setEnabled(true);
                    
                    // Take the content of the popup and insert it into the FlexTable at the dropped row index
                    this.insertRow(coordDrag.getRow());
                    for (int i = 0; i < ftDrag.getCellCount(0); i++)
                        if (ftDrag.getWidget(0, i) != null) {
                            this.setWidget(coordDrag.getRow(), i, ftDrag.getWidget(0, i));
                            this.getFlexCellFormatter().setColSpan(coordDrag.getRow(), i, ftDrag.getFlexCellFormatter().getColSpan(0, i));
                            this.getFlexCellFormatter().setRowSpan(coordDrag.getRow(), i, ftDrag.getFlexCellFormatter().getRowSpan(0, i));
                            if (ftDrag.getCellFormatter().getStyleName(0, i) != null)
                                this.getCellFormatter().setStyleName(coordDrag.getRow(), i, ftDrag.getCellFormatter().getStyleName(0, i));
                        }
                    pDrag.hide();
                    
                    fireEvent();
                }
                break;
            }
                
            case Event.ONMOUSEMOVE: {
                CoordFlexTable coord = CoordFlexTable.getCoordTarget(this, event);
                // Not dragging : just listen to the visited cell and memorize it for next drag (mouse down)
                if (!isDragging) {
                    if (isDraggable(coord.getRow())) {
                        coordDrag = new CoordFlexTable(coord);
                    } else {
                        coordDrag = new CoordFlexTable();
                    }
                }
                // Dragging : move the popup following the mouse cursor, show the currently visited row
                // for eventual next drop position and memorize it for next drop (mouse up)
                else {
                    if (isDroppable(coord.getRow())) {
                        setDragStyleCursor(true);
                        pDrag.hide();
                        pDrag.setPopupPosition(event.getClientX() + 5 + event.getClientY(), event.getClientY() + 5 + event.getClientX());
                        pDrag.show();
                        // New row : show it by a top border line and memorize the new row index
                        if (coord.getRow() != coordDrag.getRow()) {
                            removeLineDragStyle(coordDrag.getRow());
                            addLineDragStyle(coord.getRow());
                            coordDrag = new CoordFlexTable(coord);
                        }
                    }
                }
                break;
            }
            default: {
                // Do nothing
            }
        }
    }
    
    /**
     * Checks if a row can be dragged or not depending on the header and footer defined
     * 
     * @param int row index to be tested
     * @return boolean true if the row is draggable
     */
    public boolean isDraggable(int row) {
        // To be draggable, at least two rows must exist in addition to header and footer
        if (this.getRowCount() > (2))
            if (row != CoordFlexTable.UNDEFINED_COORD_INDEX)
                if ((row >= 1) && (row <= (this.getRowCount() - 1)))
                    return true;
        return false;
    }
    
    /**
     * Checks if a row can be droppable or not depending on the header and footer defined
     * 
     * @param int row index to be tested
     * @return boolean true if the row is droppable
     */
    public boolean isDroppable(int row) {
        // One row is needed in addition to header and footer to allow drop
        if (this.getRowCount() > (1))
            if (row != CoordFlexTable.UNDEFINED_COORD_INDEX)
                if ((row >= 1) && (row <= (this.getRowCount())))
                    return true;
        return false;
    }
    
    /**
     * Removes the style of the droppable row
     * 
     * @param int row index which style must be removed
     */
    private void removeLineDragStyle(int row) {
        for (int i = 0; i < this.getCellCount(row); i++) {
            this.getCellFormatter().removeStyleName(row, i, "SYST_DragnDropLine");
        }
    }
    
    /**
     * Fire a InfoDragFlexTable event width memorized data
     */
    public void fireEvent() {
        ValueChangeEvent.fire(this, new InfoDragFlexTable(rowSource, rowDest));
    }
    
    /**
     * Sets the style of the droppable row
     * 
     * @param int row index which style must be set
     */
    private void addLineDragStyle(int row) {
        for (int i = 0; i < this.getCellCount(row); i++) {
            this.getCellFormatter().addStyleName(row, i, "SYST_DragnDropLine");
        }
    }
    
    /**
     * Sets the style of the cursor
     * 
     * @param boolean drag : true if the cursor must be forced to a move one
     */
    private void setDragStyleCursor(boolean drag) {
        if (drag) {
            RootPanel.get().addStyleName("globalMoveCursor");
        } else
            RootPanel.get().removeStyleName("globalMoveCursor");
    }
    
    /**
     * Memorize the enabled status of each widget of the FlexTable
     */
    public void getEnabled() {
        if (enabled == null)
            enabled = new ArrayList<Boolean>();
        else
            enabled.clear();
        for (int i = 0; i < this.getRowCount(); i++)
            for (int j = 0; j < this.getCellCount(i); j++)
                if (this.getWidget(i, j) != null)
                    if (this.getWidget(i, j) instanceof FocusWidget)
                        enabled.add(new Boolean(((FocusWidget)this.getWidget(i, j)).isEnabled()));
    }
    
    /**
     * Disabled or reset the status for each widget of the FlexTable
     * 
     * @param possibleEnabled
     */
    public void setEnabled(boolean possibleEnabled) {
        if (enabled == null)
            return;
        
        // Reset the previous status
        if (possibleEnabled) {
            int index = 0;
            for (int i = 0; i < this.getRowCount(); i++)
                for (int j = 0; j < this.getCellCount(i); j++)
                    if (this.getWidget(i, j) != null)
                        if (this.getWidget(i, j) instanceof FocusWidget)
                            if (index < enabled.size()) {
                                ((FocusWidget)this.getWidget(i, j)).setEnabled(enabled.get(index).booleanValue());
                                index++;
                                
                            }
            
        } else
            for (int i = 0; i < this.getRowCount(); i++)
                for (int j = 0; j < this.getCellCount(i); j++)
                    if (this.getWidget(i, j) != null)
                        if (this.getWidget(i, j) instanceof FocusWidget)
                            ((FocusWidget)this.getWidget(i, j)).setEnabled(false);
    }
    
    /**
     * Add a listener on a InfoDragFlexTable event used when drop is finished
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<InfoDragFlexTable> handler) {
            if ( hEventPere != null )
                    hEventPere.removeHandler();
            hEventPere = addHandler (handler, ValueChangeEvent.getType()); 
            return hEventPere;
    }
}
