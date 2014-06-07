package com.gwt.ui.client.groupboxpanel;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class FieldSetPanel extends ComplexPanel {

    public FieldSetPanel() {
        super();
        setElement(Document.get().createFieldSetElement());
    }

    @Override
    public void add(Widget w) {
        add(w, getElement());
    }
}
