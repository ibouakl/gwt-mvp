package com.gwt.ui.client.groupboxpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AdvancedGroupBoxPanel extends FieldSetPanel {
    
    private boolean expended;
    
    private final InlineHTML caption;
    
    private final Image collapseButton;
    
    private final Tooltip collapseButtonTooltip;
    
    private Widget container;
    
    private LegendPanel legend;
    
    public AdvancedGroupBoxPanel(boolean collapsible) {
        super();
        
        legend = new LegendPanel();
        super.add(legend);
        
        this.caption = new InlineHTML();
        Style captionStyle = caption.getElement().getStyle();
        Style borderStyle = this.getElement().getStyle();
        
        if (collapsible) {
            collapseButton = new Image();
            collapseButtonTooltip = Tooltip.tooltip(collapseButton, tooltipText());
            Element collapseButtonElement = collapseButton.getElement();
            collapseButtonElement.getStyle().setProperty("cursor", "pointer");
            collapseButtonElement.getStyle().setProperty("cursor", "hand");
            collapseButtonElement.getStyle().setProperty("verticalAlign", "bottom");
            captionStyle.setProperty("cursor", "hand");
            
            legend.add(collapseButton);
            ClickHandler expandClickHandler = new ClickHandler() {
                public void onClick(ClickEvent event) {
                    setExpanded(!isExpended());
                }
            };
            collapseButton.addClickHandler(expandClickHandler);
            caption.addClickHandler(expandClickHandler);
            
            setExpanded(true);
        } else {
            collapseButton = null;
            collapseButtonTooltip = null;
            borderStyle.setProperty("border", "1px solid");
           
        }
        legend.add(caption);
        
        captionStyle.setProperty("padding", "5px 2px 2px 2px");
        captionStyle.setProperty("verticalAlign", "top");
        
        borderStyle.setProperty("margin", "3px");
        
    }
    
    public void setTitleColor(String value) {
        legend.getElement().getStyle().setProperty("color", value);
    }
    
    public void setCaption(String text) {
        caption.setText(text);
    }
    
    public String getCaption() {
        return caption.getText();
    }
    
    public <T extends Widget & HasWidgets> void setContainer(T c) {
        container = c;
        super.add(c);
    }
    
    @Override
    public void add(Widget w) {
        if (container == null) {
            super.add(container = new VerticalPanel());
        }
        ((HasWidgets)container).add(w);
    }
    
    public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
        return addDomHandler(handler, KeyUpEvent.getType());
    }
    
    private String tooltipText() {
        return isExpended() ? "Collapse" : "Expand";
    }
    
    public boolean isExpended() {
        return expended;
    }
    
    public void setExpanded(boolean expended) {
        this.expended = expended;
        if (container != null) {
            container.setVisible(expended);
        }
        Style borderStyle = this.getElement().getStyle();
        if (expended) {
            borderStyle.setProperty("border", "1px solid");
         
        } else {
            borderStyle.setProperty("border", "none");
            borderStyle.setProperty("borderTop", "1px solid");
          
        }
        
        if (expended) {
            collapseButton.setUrl(GWT.getModuleBaseURL() + "images/groupBoxOpen.png");
        } else {
            collapseButton.setUrl(GWT.getModuleBaseURL() + "images/groupBoxClose.png");
        }
        collapseButtonTooltip.setTooltipText(tooltipText());
    }
}
