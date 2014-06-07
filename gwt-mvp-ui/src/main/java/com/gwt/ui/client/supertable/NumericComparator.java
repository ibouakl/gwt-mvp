package com.gwt.ui.client.supertable;

import java.util.Comparator;

import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author ibouakl
 *
 */
public class NumericComparator implements Comparator<Widget[]> {
    private int col;
    
    private boolean ascending;
    
    /**
     * A constructor for this class.
     * 
     * @param col Specifies the column index in the table (whether it is column 0, 1 , 2 etc.)
     * @param ascending true if ascending comparator, false if this is a descending comparator.
     */
    public NumericComparator(int col, boolean ascending) {
        this.col = col;
        this.ascending = ascending;
    }
    
    /*
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Widget[] o1, Widget[] o2) {
        Widget w1 = o1[col];
        Widget w2 = o2[col];
        
        String t1 = StringComparator.convertToText(w1);
        String t2 = StringComparator.convertToText(w2);
        
        if (ascending) {
            return (int)(Float.parseFloat(t1) - Float.parseFloat(t2));
        } else {
            return (int)(Float.parseFloat(t2) - Float.parseFloat(t1));
        }
    }
}
