package com.gwt.ui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;



public class TitleBar extends Composite {
    private FlexTable table;
    private String helpUrl = null;
    private String text = "Enter Heading";
    private Image image;
    private int level = 1;
    
    
    /**
     * A constructor for this class.
     * 
     */
    public TitleBar()
    {
        this(null, null, 1);
    }

    /**
     * A constructor for this class.
     * 
     * @param text
     *            header text
     * @param helpUrl
     *            URL of the help page. If set to null, no help icon is
     *            displayed.
     * @param level
     *            header level - used to set the header level to HTML tags -
     * 
     * <pre>
     *      &lt;h1&gt;, &lt;h2&gt; ...
     * </pre>
     * 
     * @wbp.parser.constructor
     */
    public TitleBar(String text, String helpUrl, int level)
    {
        super();
        this.helpUrl = helpUrl;
        this.text = text;
        this.level = level;

        table = new FlexTable();
        initWidget(table);

        init();
    }

    private void init()
    {
        setStyleName("gwtcomp-TitleBar");
        table.setBorderWidth(0);

        if (text != null)
        {
            setText(text);
        }

        table.getRowFormatter().setVerticalAlign(0,
                HasVerticalAlignment.ALIGN_MIDDLE);
        table.getCellFormatter().setAlignment(0, 1,
                HasHorizontalAlignment.ALIGN_LEFT,
                HasVerticalAlignment.ALIGN_MIDDLE);

        if (helpUrl != null)
        {
            setHelpUrl(helpUrl);
        }

        table.getCellFormatter().addStyleName(0, 1,
                "gwtcomp-TitleBar-HelpButton");
        table.getCellFormatter().setAlignment(0, 1,
                HasHorizontalAlignment.ALIGN_RIGHT,
                HasVerticalAlignment.ALIGN_MIDDLE);

    }

    /**
     * Returns the currently-set header text.
     * 
     * @return Returns the header text.
     */
    public String getText()
    {
        return text;
    }

    /**
     * Sets the header text.
     * 
     * @param text
     *            The text to set.
     */
    public void setText(String text)
    {
        this.text = text;
        table.setHTML(0, 0, "<h" + level + ">" + text + "</h" + level + ">");
        if (image != null)
        {
            setHelpIconTitle();
        }
    }

    /**
     * Returns the currently-set level.
     * 
     * @return Returns the helpUrl.
     */
    public String getHelpUrl()
    {
        return helpUrl;
    }

    /**
     * Sets the help URL. If a null value is supplied, the help icon is removed.
     * 
     * @param helpUrl
     *            sets the help icon.
     */
    public void setHelpUrl(String helpUrl)
    {
        this.helpUrl = helpUrl;

        image = new Image(GWT.getModuleBaseURL()
                + "gwtcomp-icons/help.png");
        setHelpIconTitle();

        image.setStyleName("gwtcomp-TitleBar-HelpButton");
        image.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent arg0) {
                popupHelp();
                
            }
        });
        
        table.setWidget(0, 1, image);
    }

    private void setHelpIconTitle()
    {
        if (text != null)
        {
            image.setTitle("More information on " + text);
        }
    }

    private void popupHelp()
    {
        Window
                .open(helpUrl, "_help",
                        "toolbar=no,menubar=no,width=400,height=300,scrollbars=yes,resizable=yes");
    }

    /**
     * Returns the currently-set level.
     * 
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * Sets the current level.
     * 
     * @param level
     *            The level to set.
     */
    public void setLevel(int level)
    {
        this.level = level;
    } 
}
