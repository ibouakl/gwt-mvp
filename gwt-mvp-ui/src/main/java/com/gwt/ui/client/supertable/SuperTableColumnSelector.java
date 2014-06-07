package com.gwt.ui.client.supertable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @author ibouakl
 *
 */
public class SuperTableColumnSelector extends PopupPanel {
    private CheckBox[] checkboxes;
    
    private boolean[] checked;
    
    private ColumnProperty[] columns;
    
    private SuperTableColumnSelectorListener listener;
    
    /**
     * A constructor for this class.
     * 
     * @param columns
     * @param listener
     */
    public SuperTableColumnSelector(ColumnProperty[] columns, SuperTableColumnSelectorListener listener) {
        super(true);
        this.columns = columns;
        this.listener = listener;
        setStyleName("gwtcomp-SuperTableColumnSelector");
        
        init();
    }
    
    private void init() {
        VerticalPanel p = new VerticalPanel();
        checkboxes = new CheckBox[columns.length];
        checked = new boolean[columns.length];
        for (int i = 0; i < checkboxes.length; i++) {
            checkboxes[i] = new CheckBox(columns[i].getHeader());
            checkboxes[i].setValue(columns[i].isDisplayed());
            checkboxes[i].setVisible(columns[i].isAddToColumnsSelection());
            checked[i] = columns[i].isDisplayed();
            
            class CheckboxClickListener implements ClickHandler {
                private int i;
                
                public CheckboxClickListener(int i) {
                    this.i = i;
                }
                
                @Override
                public void onClick(ClickEvent sender) {
                    checked[i] = ((CheckBox)sender.getSource()).getValue();
                }
            }
            
            checkboxes[i].addClickHandler(new CheckboxClickListener(i));
            p.add(checkboxes[i]);
        }
        
        add(p);
        
        addCloseHandler(new CloseHandler<PopupPanel>() {
            
            @Override
            public void onClose(CloseEvent<PopupPanel> arg0) {
                
                boolean changed = false;
                for (int i = 0; i < SuperTableColumnSelector.this.columns.length; i++) {
                    if (SuperTableColumnSelector.this.columns[i].isDisplayed() != checked[i]) {
                        
                        changed = true;
                        SuperTableColumnSelector.this.columns[i].setDisplayed(checked[i]);
                    }
                }
                
                if ((changed) && (SuperTableColumnSelector.this.listener != null)) {
                    SuperTableColumnSelector.this.listener.columnSelectionChanged(SuperTableColumnSelector.this.columns);
                }
                
            }
        });
        
    }
    
    public boolean[] getChecked() {
        return checked;
    }
}
