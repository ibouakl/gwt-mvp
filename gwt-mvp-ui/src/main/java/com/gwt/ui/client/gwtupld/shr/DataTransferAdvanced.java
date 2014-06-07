package com.gwt.ui.client.gwtupld.shr;

import com.google.gwt.dom.client.DataTransfer;
import com.gwt.ui.client.gwtupld.file.FileList;
import com.gwt.ui.client.gwtupld.file.impl.FileListImpl;

public class DataTransferAdvanced extends DataTransfer {
    protected DataTransferAdvanced() {
    }
    
    public final FileList getFiles() {
        return new FileList(getFileList());
    }
    
    private native FileListImpl getFileList() /*-{
                                              return this.files;
                                              }-*/;
    
    public final native String getEffectAllowed() /*-{
                                                  return this.effectAllowed;
                                                  }-*/;
    
    public final native void setDropEffect(String effect) /*-{
                                                          this.dropEffect = effect;
                                                          }-*/;
}
