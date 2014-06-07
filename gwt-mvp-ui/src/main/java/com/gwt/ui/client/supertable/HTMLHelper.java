package com.gwt.ui.client.supertable;

/**
 * 
 * @author ibouakl
 *
 */
public class HTMLHelper
{
    /**
     * HTML rendition of bold text.
     * 
     * @param text
     *            text to be rendered in bold.
     * @return the HTMLized string
     */
    public static String bold(String text)
    {
        return "<b>" + text + "</b>";
    }

    /**
     * HTML rendition of italicized text.
     * 
     * @param text
     *            text to be rendered in italicized format.
     * @return the HTMLized string
     */
    public static String italics(String text)
    {
        return "<i>" + text + "</i>";
    }

    /**
     * HTML rendition of newline.
     * 
     * @return the HTMLized string
     */
    public static String newline()
    {
        return "<b/>";
    }

    /**
     * HTML rendition of a horizontal rule.
     * 
     * @param color
     *            Color of the line. Use the HTML color-definition conventions.
     * @return the HTMLized string
     */
    public static String hr(String color)
    {
        StringBuffer buffer = new StringBuffer("<hr");

        if (color != null)
        {
            buffer.append(" style=\"color: " + color + ";\"");
        }

        buffer.append("/>");
        return buffer.toString();
    }

    /**
     * HTML rendition of boxed text. The style is controlled by the style
     * element: <blockquote class="css"> .gwtcomp-BoxedText </blockquote>
     * 
     * @param text
     *            text to be boxed.
     * @return the HTMLized string
     */
    public static String boxedText(String text)
    {
        return "<span class=\"gwtcomp-BoxedText\">" + text + "</span>";
    }

    /**
     * HTML rendition of a image next to a text. You can create image buttons
     * using this method.
     * 
     * @param url
     *            URL of the image
     * @param text
     *            text
     * @return the HTMLized string
     */
    public static String imageWithText(String url, String text)
    {
        return "<div style='white-space: nowrap;'><img border='0' align='top' src='"
                + url + "'/>&nbsp;" + text + "</div>";
    }

    private static String list(String[] items, String listStartTag,
            String listEndTag)
    {
        StringBuffer buffer = new StringBuffer(listStartTag);

        for (int i = 0; i < items.length; i++)
        {
            buffer.append("<li>");
            buffer.append(items[i]);
            buffer.append("</li>");
        }

        buffer.append(listEndTag);
        return buffer.toString();
    }

    /**
     * HTML rendition of a numbered (ordered) list.
     * 
     * @param items
     *            items in the list
     * @return the HTMLized string
     */
    public static String orderedList(String[] items)
    {
        return list(items, "<ol>", "</ol>");
    }

    /**
     * HTML rendition of a bullet (unordered) list.
     * 
     * @param items
     *            items in the list.
     * @return the HTMLized string
     */
    public static String bulletList(String[] items)
    {
        return list(items, "<ul>", "</ul>");
    }

    /**
     * HTML rendition of a header element.
     * 
     * @param i
     *            header level (1, 2, 3, 4, etc.)
     * @param title
     *            header text
     * @return the HTMLized string
     */
    public static String header(int i, String title)
    {
        return "<h" + i + ">" + title + "</h" + i + ">";
    }
}

