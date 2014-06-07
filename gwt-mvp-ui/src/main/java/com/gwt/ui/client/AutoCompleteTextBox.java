package com.gwt.ui.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class AutoCompleteTextBox extends TextBox {
    
    protected PopupPanel choicesPopup = new PopupPanel(true);
    protected ListBox choices = new ListBox();
    protected CompletionItems items = new SimpleAutoCompletionItems(new String[] {});
    protected boolean popupAdded = false;
    protected boolean visible = false;
    
    /**
     * Default Constructor
     */
    public AutoCompleteTextBox() {
        super();
        
        addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.isDownArrow()) {
                    int selectedIndex = choices.getSelectedIndex();
                    selectedIndex++;
                    if (selectedIndex >= choices.getItemCount()) {
                        selectedIndex = 0;
                    }
                    choices.setSelectedIndex(selectedIndex);
                    return;
                }
                
                if (event.isUpArrow()) {
                    int selectedIndex = choices.getSelectedIndex();
                    selectedIndex--;
                    if (selectedIndex < 0) {
                        selectedIndex = choices.getItemCount()-1;
                    }
                    choices.setSelectedIndex(selectedIndex);
                    return;
                }
                
                if (event.getNativeKeyCode() == 13) {
                    if (visible) {
                        complete();
                    }
                    return;
                }
                
                if (event.getNativeKeyCode() == 27) {
                    choices.clear();
                    choicesPopup.hide();
                    visible = false;
                    return;
                }
                
                String text = getText();
                String[] matches = new String[] {};
                if (text.length() > 0) {
                    matches = items.getCompletionItems(text);
                }
                
                if (matches.length > 0) {
                    choices.clear();
                    
                    for (int i = 0; i < matches.length; i++) {
                        choices.addItem((String)matches[i]);
                    }
                    
                    // if there is only one match and it is what is in the
                    // text field anyways there is no need to show autocompletion
                    if (matches.length == 1 && matches[0].compareTo(text) == 0) {
                        choicesPopup.hide();
                    } else {
                        choices.setSelectedIndex(0);
                        choices.setVisibleItemCount(matches.length + 1);
                        
                        if (!popupAdded) {
                            RootPanel.get().add(choicesPopup);
                            popupAdded = true;
                        }
                        choicesPopup.show();
                        visible = true;
                        choicesPopup.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop() + getOffsetHeight());
                        // choicesPopup.setWidth(this.getOffsetWidth() + "px");
                        choices.setWidth(getOffsetWidth() + "px");
                    }
                    
                } else {
                    visible = false;
                    choicesPopup.hide();
                }
                
            }
        });
        
        this.setStyleName("AutoCompleteTextBox");
        
        choicesPopup.add(choices);
        choicesPopup.addStyleName("AutoCompleteChoices");
        
        choices.setStyleName("list");
        
        choices.addChangeHandler(new ChangeHandler() {
            
            @Override
            public void onChange(ChangeEvent arg0) {
                complete();
                
            }
        });
        
        choices.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent arg0) {
                complete();
                
            }
        });
    }
    
    /**
     * Sets an "algorithm" returning completion items You can define your own way how the textbox retrieves autocompletion items by
     * implementing the CompletionItems interface and setting the according object
     * 
     * @see SimpleAutoCompletionItem
     * @param items CompletionItem implementation
     */
    public void setCompletionItems(CompletionItems items) {
        this.items = items;
    }
    
    /**
     * Returns the used CompletionItems object
     * 
     * @return CompletionItems implementation
     */
    public CompletionItems getCompletionItems() {
        return this.items;
    }
    
    // add selected item to textbox
    protected void complete() {
        if (choices.getItemCount() > 0) {
                this.setText(choices.getItemText(choices.getSelectedIndex()));
        }
        
        choices.clear();
        choicesPopup.hide();
    }
}
