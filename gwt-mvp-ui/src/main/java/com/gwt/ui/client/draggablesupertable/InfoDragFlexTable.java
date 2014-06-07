package com.gwt.ui.client.draggablesupertable;

/**
 * Management of rows when moving from to another in a FlexTable
 * Just contains the rows indexes
 * Used for event dispatching from a DraggableFlexTable
 * 
 * @author Philippe M.
 * @version 1.0
 * @date : august 2010
 */
public class InfoDragFlexTable {
	private int oldRow;
	private int newRow;
	public InfoDragFlexTable(int oldRow, int newRow)
	{
		this.oldRow = oldRow;
		this.newRow = newRow;
	}
	public int getOldRow()
	{
		return oldRow;
	}
	public int getNewRow()
	{
		return newRow;
	}
}
