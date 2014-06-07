/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gwt.ui.client;

import com.gwt.ui.client.BBCode;

import junit.framework.TestCase;

/**
 *
 * @author jguibert
 */
public class BBCodeTest extends TestCase {

    public void test() {
        String in = "[akljk]vd[g]qdsq";
        assertEquals(in, BBCode.transformToBbcode(BBCode.transformToHtml(in)));

        String in2 = "[akljk][[vd]][g]qdsq";
        String ein2 = "<akljk>[vd]<g>qdsq";

        assertEquals("aaaaaaaaaaaaaa", BBCode.transformToHtml("aaaaaaaaaaaaaa"));
        assertEquals(ein2, BBCode.transformToHtml(in2));

        System.out.println(in2 + " ==> : " + BBCode.transformToHtml(in2));

        assertEquals(in2, BBCode.transformToBbcode(BBCode.transformToHtml(in2)));

    }
}
