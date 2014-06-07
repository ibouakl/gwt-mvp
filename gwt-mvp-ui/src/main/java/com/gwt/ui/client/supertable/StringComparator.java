package com.gwt.ui.client.supertable;

import java.util.Comparator;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author ibouakl
 *
 */
public class StringComparator implements Comparator<Widget[]> {
    private int col;
    
    private boolean ascending;
    
    /**
     * A constructor for this class.
     * 
     * @param col Specifies the column index in the table (whether it is column 0, 1 , 2 etc.)
     * @param ascending true if ascending comparator, false if this is a descending comparator.
     */
    public StringComparator(int col, boolean ascending) {
        this.col = col;
        this.ascending = ascending;
    }
    
    /*
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Widget[] o1, Widget[] o2) {
        Widget w1 = o1[col];
        Widget w2 = o2[col];
        
        String t1 = convertToText(w1);
        ;
        String t2 = convertToText(w2);
        
        if (ascending) {
            return t1.compareTo(t2);
        } else {
            return t2.compareTo(t1);
        }
    }
    
    protected static String convertToText(Widget w) {
        String t;
        if (w instanceof Label) {
            t = ((Label)w).getText();
        } else if (w instanceof HTML) {
            t = ((HTML)w).getHTML();
        } else {
            t = ((TextBox)w).getText() == null ? "" : ((TextBox)w).getText().trim();
        }
        return t;
    }
}
