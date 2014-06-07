package com.gwt.ui.client.toolbar;


import com.google.gwt.user.client.ui.Image;

/**
 * Helper class for the ToolbarPanel class. The users of the components do not
 * need to use this class.
 * 
 * 
 */
public class ToolbarSplitterInfo
{
    private Toolbar toobar;

    private Image splitter;

    /**
     * A constructor for this class.
     * 
     * @param toobar
     * @param splitter
     */
    public ToolbarSplitterInfo(Toolbar toolbar, Image splitter)
    {
        this.toobar = toolbar;
        this.splitter = splitter;
    }

    /**
     * @return Returns the splitter.
     */
    public Image getSplitter()
    {
        return splitter;
    }

    /**
     * @param splitter
     *            The splitter to set.
     */
    public void setSplitter(Image splitter)
    {
        this.splitter = splitter;
    }

    /**
     * @return Returns the toobar.
     */
    public Toolbar getToobar()
    {
        return toobar;
    }

    /**
     * @param toobar
     *            The toobar to set.
     */
    public void setToobar(Toolbar toolbar)
    {
        this.toobar = toolbar;
    }

}