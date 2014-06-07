package com.gwt.ui.client.gwtupld;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.gwt.ui.client.gwtupld.file.FileList;
import com.gwt.ui.client.gwtupld.file.impl.FileListImpl;

public class FileInputElement extends InputElement {
    protected FileInputElement() {
    }
    
    public final FileList getFiles() {
        return new FileList(getFiles(this));
    }
    
    private native FileListImpl getFiles(Element element) /*-{
                                                          return element.files;
                                                          }-*/;
}
