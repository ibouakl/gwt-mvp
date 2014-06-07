package com.gwt.ui.client.gwtupld.shr;

import com.google.gwt.xhr.client.XMLHttpRequest;
import com.gwt.ui.client.gwtupld.file.File;

public class XMLHttpRequestAdvanced extends XMLHttpRequest {
    protected XMLHttpRequestAdvanced() {
    }
    
    public static XMLHttpRequestAdvanced create() {
        return (XMLHttpRequestAdvanced)XMLHttpRequest.create();
    }
    
    public final native void send(File file) /*-{
                                             this.send(file);
                                             }-*/;
    
    public final native void setOnUploadProgress(OnUploadProgressHandler handler) /*-{
                                                                                  var _this = this;
                                                                                  this.upload.onprogress = $entry(function(e) {
                                                                                  handler.@com.gwt.ui.client.gwtupld.shr.OnUploadProgressHandler::onUploadProgress(Lcom/gwt/ui/client/gwtupld/shr/OnUploadProgressEvent;)(e);
                                                                                  });
                                                                                  }-*/;
}
