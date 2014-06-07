package com.gwt.ui.client;

import com.google.gwt.user.client.ui.FlowPanel;

/**
 * 
 * @author ibouakl
 *
 */
public class InfoLabel extends FlowPanel {
 
    private InfoHelp infoHelp;
    private LabelMarkup label;
    
    
    public InfoLabel(String text,String helpHtml){
        super();
        addStyleName("infoLabel");
        label = new LabelMarkup(text);
        label.addStyleName("infoLabel-label-ie");
        add(label);
        infoHelp = new InfoHelp(helpHtml);
        add(infoHelp);
       
    }
    

    
}
