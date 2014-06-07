package com.gwt.ui.client.datagrid;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.ui.client.LabelMarkup;

/**
 * @author ibouakl
 */
public class CellWidget extends Composite {
    private int row;
    private int col;
    private Object content;
    
    public CellWidget(Object content, int row, int col) {
        this.row = row;
        this.col = col;
        this.content = content;
        if (content !=null && content instanceof String){
            LabelMarkup l = new LabelMarkup((String)content);
            initWidget(l);
        }else if(content !=null && content instanceof Integer){
            LabelMarkup l = new LabelMarkup(((Integer)content).toString());
            initWidget(l);
        }else if(content!=null && content instanceof Widget){
            initWidget((Widget)content);
        }else{
            initWidget(new LabelMarkup(""));
        }
    }
    
    public Object getContent() {
        return content;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }

}

