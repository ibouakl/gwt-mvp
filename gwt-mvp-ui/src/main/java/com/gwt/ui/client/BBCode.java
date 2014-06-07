/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gwt.ui.client;

import java.util.ArrayList;

/**
 *
 * @author jguibert
 */
public class BBCode {

    public BBCode() {
    }

    public static String transformToHtml(String bbcode) {
        if (bbcode != null && bbcode.length() > 0) {
            return replace(replace(bbcode, "[", "<"),"]", ">"); 
        }
        return bbcode;
    }

    public static String transformToBbcode(String bbcode) {
        if (bbcode != null && bbcode.length() > 0) {
            return bbcode.replaceAll("\\[", "[[").replaceAll("\\]", "]]").replaceAll("<", "[").replaceAll(">", "]");
        }
        return bbcode;
    }

    private static String replace(String source, String from, String to) {
        StringTokenizer tokenizer = new StringTokenizer(source, from.charAt(0), true);
        int count = tokenizer.countTokens();
        if (count <= 1) {
            return source;
        }
        StringBuffer buffer = new StringBuffer();
        ArrayList<String> tokens = tokenizer.getTokens();
        for (int i = 0; i < count; i++) {
            String token = tokens.get(i);

            if (token.equals(from)) {
                int j = i + 1;
                if (j < count) {
                    if (tokens.get(j).equals(from)) {
                        buffer.append(from);
                        i++;
                    } else {
                        buffer.append(to);
                    }
                } else {
                    buffer.append(to);
                }
            } else {
                buffer.append(token);
            }
        }
        return buffer.toString();
    }
}
