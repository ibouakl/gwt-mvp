package com.gwt.ui.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gwt.core.client.JavaScriptObject;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.TextArea;

/**
 * From http://timursdev.blogspot.com/2010/06/full-fledged-gwt-rich-text-editor.html.
 * 
 * @author Jerome Guibert
 */
public class TextEditor extends TextArea {
    
    public static final transient Map<String, String> PRESET_BASIC = new HashMap<String, String>();
    public static final transient Map<String, String> PRESET_ADVANCED = new HashMap<String, String>();
    public static final transient Map<String, String> PRESET_SIMPLE = new HashMap<String, String>();
    public static final transient Map<String, String> PRESET_TITLE = new HashMap<String, String>();
    
    static {
        PRESET_BASIC.put("theme", "simple");
        /** advanced preset */
        PRESET_ADVANCED.put("theme", "advanced");
        PRESET_ADVANCED.put("plugins", "emotions,spellchecker,advhr,insertdatetime,preview");
        PRESET_ADVANCED.put("theme_advanced_buttons1", "newdocument, |, bold, italic, underline, |, justifyleft, justifycenter, justifyright, fontselect, fontsizeselect, formatselect");
        PRESET_ADVANCED.put("theme_advanced_buttons2", "cut, copy, paste, |, bullist, numlist, |, outdent, indent, |, undo, redo, |, link, unlink, anchor, |, code, preview");
        PRESET_ADVANCED.put("theme_advanced_buttons3", "insertdate, inserttime, |, spellchecker, advhr, ,removeformat, |, sub, sup, |, charmap");
        PRESET_ADVANCED.put("theme_advanced_toolbar_location", "top");
        PRESET_ADVANCED.put("theme_advanced_toolbar_align", "left");
        PRESET_ADVANCED.put("theme_advanced_statusbar_location", "bottom");
        PRESET_ADVANCED.put("theme_advanced_resizing", "true");
        /** PRESET_SIMPLE **/
        PRESET_SIMPLE.put("theme", "advanced");
        PRESET_SIMPLE.put("plugins", "");
        PRESET_SIMPLE.put("theme_advanced_buttons1", "bold, italic, underline, |, undo, redo , |, code");
        PRESET_SIMPLE.put("theme_advanced_buttons2", "");
        PRESET_SIMPLE.put("theme_advanced_buttons3", "");
        PRESET_SIMPLE.put("theme_advanced_toolbar_location", "top");
        PRESET_SIMPLE.put("theme_advanced_toolbar_align", "left");
        PRESET_SIMPLE.put("theme_advanced_statusbar_location", "none");
        PRESET_SIMPLE.put("theme_advanced_resizing", "true");
        PRESET_SIMPLE.put("force_br_newlines", "true");
        PRESET_SIMPLE.put("force_p_newlines", "false");
        PRESET_SIMPLE.put("forced_root_block", "");
        
        /** PRESET_TITLE */
        PRESET_TITLE.put("theme", "advanced");
        PRESET_TITLE.put("plugins", "");
        PRESET_TITLE.put("theme_advanced_buttons1", "");
        PRESET_TITLE.put("theme_advanced_buttons2", "");
        PRESET_TITLE.put("theme_advanced_buttons3", "");
        PRESET_TITLE.put("theme_advanced_toolbar_location", "top");
        PRESET_TITLE.put("theme_advanced_toolbar_align", "left");
        PRESET_TITLE.put("theme_advanced_statusbar_location", "none");
        PRESET_TITLE.put("theme_advanced_resizing", "true");
        PRESET_TITLE.put("forced_root_block", "");
        
        /*
             PRESET_ADVANCED.put("theme", "advanced");
        PRESET_ADVANCED.put("plugins", "emotions,spellchecker,advhr,insertdatetime,preview");
        PRESET_ADVANCED.put("theme_advanced_buttons1", "newdocument, |, bold, italic, underline, |, justifyleft, justifycenter, justifyright, fontselect, fontsizeselect, formatselect");
        PRESET_ADVANCED.put("theme_advanced_buttons2", "cut, copy, paste, |, bullist, numlist, |, outdent, indent, |, undo, redo, |, link, unlink, anchor, image, |, code, preview, |, forecolor, backcolor");
        PRESET_ADVANCED.put("theme_advanced_buttons3", "insertdate, inserttime, |, spellchecker, advhr, ,removeformat, |, sub, sup, |, charmap, emotions");
        PRESET_ADVANCED.put("theme_advanced_toolbar_location", "top");
        PRESET_ADVANCED.put("theme_advanced_toolbar_align", "left");
        PRESET_ADVANCED.put("theme_advanced_statusbar_location", "bottom");
        PRESET_ADVANCED.put("theme_advanced_resizing", "true");*/
        
    }
    private static final transient String DEFAULT_ELEMENT_ID = "textEditor";
    private boolean initialized = false;
    private String elementId;
    private Set<String> fixedOptions = new HashSet<String>(2); // options that can not be overwritten
    private JSONObject options = new JSONObject(); // all other TinyMCE options
    
    public TextEditor() {
        this(DEFAULT_ELEMENT_ID);
    }
    
    public TextEditor(String elementId) {
        this.elementId = elementId;
        getElement().setId(elementId);
        
        // fixed attributes
        addOption("mode", "exact");
        addOption("elements", elementId);
        fixedOptions.addAll(options.keySet());
        
        // load preset
        addOptions(PRESET_BASIC);
        
    }
    
    public TextEditor(String elementId, Map<String, String> preset) {
        this(elementId);
        applyPreset(preset);
    }
    
    @Override
    protected void onLoad() {
        super.onLoad();
        initialize();
    }
    
    public final void applyPreset(Map<String, String> preset) {
        addOptions(preset);
    }
    
    private void addOptions(Map<String, String> optionsToAdd) {
        if (optionsToAdd != null) {
            for (Entry<String, String> entry : optionsToAdd.entrySet()) {
                addOption(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public final void addOption(String key, String value) {
        // do not allow overriding fixed options
        if (fixedOptions.contains(key)) {
            return;
        }
        options.put(key, new JSONString(value));
    }
    
    private void initialize() {
        initTinyMce(options.getJavaScriptObject());
        initialized = true;
    }
    
    private native void initTinyMce(JavaScriptObject options) /*-{
                                                              $wnd.tinyMCE.init(options);
                                                              }-*/;
    
    @Override
    public String getText() {
        String result;
        if (initialized) {
            result = getContent(elementId);
        } else {
            result = super.getText();
        }
        return result;
    }
    
    @SuppressWarnings("unused")
    private static String escapeHtml(final String maybeHtml) {
        Element div = DOM.createDiv();
        DOM.setInnerText(div, maybeHtml);
        return DOM.getInnerHTML(div);
    }
    
    private native String getContent(String elementId) /*-{
                                                       return $wnd.tinyMCE.get(elementId).getContent();
                                                       }-*/;
    
    @Override
    public void setText(String text) {
        // String escaped = escapeHtml(text);
        // super.setText(text);
        if (initialized) {
            setContent(elementId, text);
        } else {
            super.setText(text);
        }
        
    }
    
    protected native String setContent(String elementId, String text) /*-{
                                                                      $wnd.tinyMCE.get(elementId).setContent(text);
                                                                      }-*/;
}
