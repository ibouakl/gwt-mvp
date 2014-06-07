package com.gwt.ui.client.datagrid;

/**
 * @author ibouakl
 */
public class EditableModelEvent {
    /** on update content */
    public static final EventType UPDATE_ALL = new EventType();
    /** on update paticular cell */
    public static final EventType UPDATE_CELL = new EventType();
    /** on update particular row */
    public static final EventType UPDATE_ROW = new EventType();
    /** on update particular column */
    public static final EventType UPDATE_COLUMN = new EventType();
    
    /** on add particular column */
    public static final EventType ADD_COLUMN = new EventType();
    /** on add particular row */
    public static final EventType ADD_ROW = new EventType();
    
    /** on add particular row */
    public static final EventType ADD_ROW_BEFORE = new EventType();
    
    /** on remove particular column */
    public static final EventType REMOVE_COLUMN = new EventType();
    /** on remove particular row */
    public static final EventType REMOVE_ROW = new EventType();
    /** on remove all data */
    public static final EventType CLEAN = new EventType();
    /** on sort rows */
    public static final EventType SORT_ALL = new EventType();
    
    /** is a source model of the event */
    private Editable source;
    /** row that produced this event (actual for {@link #UPDATE_CELL), {@link #UPDATE_ROW), {@link #ADD_ROW), {@link #REMOVE_ROW) */
    private int row = -1;
    /**
     * column that produced this event (actual for {@link #UPDATE_CELL), {@link #UPDATE_COLUMN}, {@link #ADD_COLUMN},
     * {@link #REMOVE_COLUMN}, {@link #SORT_ALL}
     */
    private int column = -1;
    /** is a type of the event */
    private EventType eventType;
    
    /**
     * Creates an instance of this class and initilizes mandatory fields.
     * 
     * @param eventType is an event type, mustn't be <code>null</code>.
     */
    public EditableModelEvent(EventType eventType) {
        this.eventType = eventType;
    }
    
    /**
     * Creates an instance of this class and initializes internal fields.
     * 
     * @param eventType is an event type, mustn't be <code>null</code>.
     * @param row is a row number that produced this event (<code>-1</code> of none).
     * @param column is a column number that produced this event (<code>-1</code> of none).
     */
    public EditableModelEvent(EventType eventType, int row, int column) {
        this(eventType);
        this.row = row;
        this.column = column;
    }
    
    /**
     * Gets an event type.
     * 
     * @return never <code>null</code>
     */
    public EventType getEventType() {
        return eventType;
    }
    
    /**
     * Gets an event source model.
     * 
     * @return never <code>null</code>.
     */
    public Editable getSource() {
        return source;
    }
    
    /**
     * Gets a number of row that produced this event.
     * 
     * @return <code>-1</code> if none.
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Gets a number of column that produced this event.
     * 
     * @return <code>-1</code> if none.
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * Sets the source model.
     * <p/>
     * This method MUST be invoked by the model before it's fired.
     * 
     * @param source is a source model.
     */
    protected void setSource(Editable source) {
        this.source = source;
    }
    
    /**
     * Sets the row that produced this event.
     * 
     * @param row is a row number.
     */
    protected void setRow(int row) {
        this.row = row;
    }
    
    /**
     * Sets the column that produced this event.
     * 
     * @param column is a column number.
     */
    protected void setColumn(int column) {
        this.column = column;
    }
    
    /**
     * This is an event safe type class.
     */
    protected static class EventType {
        /**
         * Protected since used in subclasses.
         */
        protected EventType() {
        }
    }
}
