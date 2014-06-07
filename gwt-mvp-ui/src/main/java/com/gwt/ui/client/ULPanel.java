package com.gwt.ui.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Use this Class to render a list. the result should look like this
 * <ul>
 * <li></li>
 * <li></li>
 * <ul>
 * 
 * @author ibouakl
 */
public class ULPanel extends ComplexPanel {

    private UListElement list;

    public ULPanel() {
        list = Document.get().createULElement();// create the <ul> Element
        setElement(list);
    }

    public void addItem(Widget child, String classValue) {
        Element li = Document.get().createLIElement().cast();// create the <li> child element
        li.addClassName(classValue);// add a style name to the <li> child element
        list.appendChild(li); // append the <li> child element to the <ul> parent elemen
        super.add(child, li);
    }

} 
