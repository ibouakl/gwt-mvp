package com.gwt.ui.client.supertable;

import com.google.gwt.user.client.ui.Label;
/**
 * 
 * @author ibouakl
 *
 */
public class LabelSuperTable extends Label {
    
    private String id;
    private String tooltipText;
   
    
    public LabelSuperTable() {
       this("","");
    }
    
    public LabelSuperTable(String id,String text) {
        this(id,text,null);
    }
    
    public LabelSuperTable(String id,String text,String tooltipText) {
        super(text);
        this.id=id;  
        this.tooltipText =tooltipText;
    }
    
    public String getId() {
        return id;
    }

    public String getTooltipText() {
        return tooltipText;
    }

    public void setTooltipText(String tooltipText) {
        this.tooltipText = tooltipText;
    }
    
}
