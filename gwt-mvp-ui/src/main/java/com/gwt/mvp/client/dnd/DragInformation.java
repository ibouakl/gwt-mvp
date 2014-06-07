package com.gwt.mvp.client.dnd;

import java.io.Serializable;

/**
 * 
 * @author jguibert
 * @author ibouakl
 * 
 */
public class DragInformation  implements Serializable {
    
    private static final long serialVersionUID = -2308247152619174355L;
    private final Clipboard source;
    private final Clipboard dropTarget;
    
    public DragInformation(final Clipboard source, final Clipboard dropTarget) {
        this.source = source;
        this.dropTarget = dropTarget;
    }
    
    public Clipboard getDropTarget() {
        return dropTarget;
    }
    
    public Clipboard getSource() {
        return source;
    }
    
}
