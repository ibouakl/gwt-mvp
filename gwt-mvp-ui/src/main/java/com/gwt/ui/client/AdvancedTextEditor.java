package com.gwt.ui.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
public class AdvancedTextEditor extends TextArea {
    
    public static final transient Map<String, String> PRESET_ADVANCED_1 = new HashMap<String, String>();
    
    static {
        PRESET_ADVANCED_1.put("plugins", "advlist,autolink,lists,link,image,charmap, print,preview,anchor,searchreplace, visualblocks,code,fullscreen,insertdatetime, media, table, contextmenu, paste");
        PRESET_ADVANCED_1.put("toolbar", "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image");
        PRESET_ADVANCED_1.put("theme", "modern");
    }
    private static final transient String DEFAULT_ELEMENT_ID = "textEditor";
    private boolean initialized = false;
    private String elementId;
    private Set<String> fixedOptions = new HashSet<String>(2); // options that can not be overwritten
    private JSONObject options = new JSONObject(); // all other TinyMCE options
    
    public AdvancedTextEditor() {
        this(DEFAULT_ELEMENT_ID);
    }
    
    public AdvancedTextEditor(String elementId) {
        this.elementId = elementId;
        getElement().setId(elementId);
        // fixed attributes
        addOption("selector", "textarea#" + elementId);
        fixedOptions.addAll(options.keySet());
        
    }
    
    public AdvancedTextEditor(String elementId, Map<String, String> preset) {
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
    
    private native String getContent(String elementId) /*-{
                                                       return $wnd.tinyMCE.get(elementId).getContent();
                                                       }-*/;
    
    @Override
    public void setText(String text) {
        
        try {
            setContent(elementId, text);
        } catch (Exception e) {
            super.setText(text);
        }
          
    }
    
    protected native String setContent(String elementId, String text) /*-{
                                                         $wnd.tinyMCE.get(elementId).setContent(text);
                                                                      }-*/;
}
