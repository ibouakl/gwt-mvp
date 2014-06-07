package com.gwt.ui.client;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.gwt.ui.client.button.ButtonPanel;
import com.gwt.ui.client.supertable.HTMLHelper;

public class SimpleWizard extends Composite {
    
    private FormPanel formPanel;
    
    private ButtonPanel buttonPanel;
    
    private MessageBar messageBar;
    
    private Button finishButton;
    
    private Button cancelButton;
    
    private Button nextButton;
    
    private DeckPanel panel;
    
    private Button previousButton;
    
    private int currentIndex = 0;
    
    private TitleBar titleBar;
    
    private ArrayList<String> panelList = new ArrayList<String>();
    private ArrayList<WizardPanelListener> listeners = new ArrayList<WizardPanelListener>();
    
    /**
     * A constructor for this class. Use this construction if the forms do not include any FileUpload widget.
     */
    public SimpleWizard() {
        formPanel = new FormPanel();
        initWidget(formPanel);
        
        init();
    }
    
    /**
     * A constructor for this class. Use this construction if the forms include a FileUpload widget.
     * 
     * @param action the action to invoke on form submission
     */
    public SimpleWizard(String action) {
        formPanel = new FormPanel();
        initWidget(formPanel);
        formPanel.setAction(action);
        formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
        formPanel.setMethod(FormPanel.METHOD_POST);
        init();
    }
    
    private void init() {
        final FlowPanel flowPanel = new FlowPanel();
        formPanel.setWidget(flowPanel);
        
        setStyleName("gwtcomp-WizardPanel");
        setSize("100%", "100%");
        
        flowPanel.setSize("100%", "100%");
        
        titleBar = new TitleBar();
        flowPanel.add(titleBar);
        titleBar.setText("[Wizard Panel]");
        
        messageBar = new MessageBar();
        flowPanel.add(messageBar);
        messageBar.setWidth("100%");
        
        panel = new DeckPanel();
        panel.addStyleName("gwtcomp-WizardPanel-ChildPanel");
        flowPanel.add(panel);
        panel.setWidth("100%");
        
        final HTML rule = new HTML(HTMLHelper.hr("#AAAAAA"));
        flowPanel.add(rule);
        rule.setWidth("100%");
        
        buttonPanel = new ButtonPanel();
        flowPanel.add(buttonPanel);
        buttonPanel.setWidth("100%");
        
        previousButton = new Button();
        buttonPanel.addButton(previousButton);
        previousButton.setHTML(HTMLHelper.imageWithText(GWT.getModuleBaseURL() + "gwtcomp-icons/1leftarrow.png", " Previous "));
        
        nextButton = new Button();
        buttonPanel.addButton(nextButton);
        nextButton.setHTML(HTMLHelper.imageWithText(GWT.getModuleBaseURL() + "gwtcomp-icons/1rightarrow.png", " Next "));
        
        finishButton = new Button();
        buttonPanel.addButton(finishButton);
        finishButton.setHTML(HTMLHelper.imageWithText(GWT.getModuleBaseURL() + "gwtcomp-icons/apply.png", " Finish "));
        
        cancelButton = new Button();
        buttonPanel.addButton(cancelButton);
        cancelButton.setHTML(HTMLHelper.imageWithText(GWT.getModuleBaseURL() + "gwtcomp-icons/cancel.png", " Cancel "));
    }
    
    /**
     * Add a panel to the wizard panel. One or more panels make up the data entry/information screen for the wizard. The panels are placed
     * in a "deck" and only one of them may be visible at a time. The ordering is important since the wizard panel automatically handles the
     * Next and Previous button actions.
     * 
     * @param name name of the panel
     * @param panel the panel itself.
     */
    public void addPanel(String name, Panel panel) {
        panelList.add(name);
        this.panel.add(panel);
    }
    
    /**
     * Inserts a panel to the "deck" at a given position.
     * 
     * @param name name of the panel
     * @param panel the panel itself
     * @param atPosition position at which the panel is inserted (starting with 0).
     */
    public void addPanel(String name, Panel panel, int atPosition) {
        panelList.add(atPosition, name);
        this.panel.add(panel);
    }
    
    /**
     * Display the next panel.
     */
    public void displayNextPanel() {
        if (currentIndex < panelList.size() - 1) {
            currentIndex++;
            switchPage();
        }
    }
    
    /**
     * Display the previous panel.
     */
    public void displayPreviousPanel() {
        if (currentIndex > 0) {
            currentIndex--;
            switchPage();
        }
    }
    
    private void switchPage() {
        panel.showWidget(currentIndex);
        enableButtons();

        Iterator<WizardPanelListener> i = listeners.iterator();
        String name = getDisplayedPanel();
        while (i.hasNext())
        {
            WizardPanelListener listener = i.next();
            listener.onPageSwitch(name);
        }
    }
    
    /**
     * Enable/disable automatically the buttons of wizard
     */
    private void enableButtons() {
        if (currentIndex == 0) // first form
        {
            previousButton.setEnabled(false);
            
            if (currentIndex == panelList.size() - 1) {
                nextButton.setEnabled(false);
            } else {
                nextButton.setEnabled(true);
            }
        } else if (currentIndex == panelList.size() - 1) // last form
        {
            nextButton.setEnabled(false);
            
            if (currentIndex == 0) {
                previousButton.setEnabled(false);
            } else {
                previousButton.setEnabled(true);
            }
        }

        else
        // pages in the middle
        {
            nextButton.setEnabled(true);
            previousButton.setEnabled(true);
        }
    }
    
    /**
     * Display a panel with the given name. The first panel is not automatically displayed. So, you must call this after creating the panel
     * object.
     * 
     * @param name name of the panel.
     */
    public void displayPanel(String name) {
        int len = panelList.size();
        for (int i = 0; i < len; i++) {
            String e = panelList.get(i);
            if (e.equals(name)) {
                this.panel.showWidget(i);
                currentIndex = i;
                switchPage();
                return;
            }
        }
    }
    
    /**
     * Get the deck panel. You can control the appearance, etc. although adding panels to the deck is not recommended. You should use the
     * addPanel() methods for that purpose for things are not going to work.
     * 
     * @return return the panel object
     */
    public DeckPanel getPanel() {
        return panel;
    }
    
    /**
     * Returns the name of the currently-displayed panel.
     * 
     * @return the name of the displayed panel
     */
    public String getDisplayedPanel() {
        return panelList.get(currentIndex);
    }
    
    /**
     * Returns the button panel. You can control the appearance, add new buttons, remove buttons, etc.
     * 
     * @return the button panel
     */
    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }
    
    /**
     * Returns the form panel used as a base widget. You can control the appearance, etc. Note the use of the FormPanel as it allows you to
     * have panels which include a file upload widget.
     * 
     * @return the form panel
     */
    public FormPanel getFormPanel() {
        return formPanel;
    }
    
    /**
     * Get the "Previous" button. Allows you to control the appearance, enable/disable, etc. You must also add your own click listener to
     * handle the click event. From the listener, you can call the displayPreviousPanel() method to go to the previous page.
     * 
     * @return the previous button
     */
    public Button getPreviousButton() {
        return previousButton;
    }
    
    /**
     * Get the "Next" button. Allows you to control the appearance, enable/disable, etc. You must also add your own click listener to handle
     * the click event. From the listener, you can call the displayNextPanel() method to go to the next page.
     * 
     * @return the next button
     */
    public Button getNextButton() {
        return nextButton;
    }
    
    /**
     * Get the "Finish" button. Allows you to control the appearance, enable/disable, etc. You may want to add your own click listener to
     * handle the Finish action.
     * 
     * @return the finish button
     */
    public Button getFinishButton() {
        return finishButton;
    }
    
    /**
     * Get the "Cancel" button. Allows you to control the appearance, enable/disable, etc. You may want to add your own click listener to
     * handle the Cancel action.
     * 
     * @return the cancel button
     */
    public Button getCancelButton() {
        return cancelButton;
    }
    
    /**
     * Get the title bar. Allows you to control the appearance, enable/disable, make invisible, etc.
     * 
     * @return the title bar object
     */
    public TitleBar getTitleBar() {
        return titleBar;
    }
    
    /**
     * Get the message bar. Allows you to control the appearance, enable/disable, make invisible, etc.
     * 
     * @return the message bar object
     */
    
    public MessageBar getMessageBar() {
        return messageBar;
    }
    
}
