package com.gwt.ui.client;

import java.util.ArrayList;

public class StringTokenizer {

    private final ArrayList<String> tokens;

    /**
     * A constructor for this class.
     *
     * @param text text to be tokenized.
     * @param delimiter the delimiter character.
     */
    public StringTokenizer(String text, char delimiter, boolean addDelimiter) {
        tokens = analyse(text, delimiter, addDelimiter);
    }

    public StringTokenizer(String text, char delimiter) {
        this(text, delimiter, false);
    }

    private ArrayList<String> analyse(String text, char delimiter, boolean addDelimiter) {
        ArrayList<String> result = new ArrayList<String>();
        char[] chars = text.toCharArray();
        int sindex = 0;
        int i;
        for (i = 0; i < chars.length; i++) {
            if (chars[i] == delimiter) {
                // in case of a double delimiter we not have a substring
                if (sindex != i) {
                    result.add(text.substring(sindex, i));
                }
                if (addDelimiter) {
                    result.add(String.valueOf(delimiter));
                }
                sindex = i + 1;
            }
        }
        if (sindex < i) {
            result.add(text.substring(sindex));
        }
        return result;
    }

    public StringTokenizer(String text, String lim) {
        ArrayList<String> current = new ArrayList<String>();
        current.add(text);
        for (char c : lim.toCharArray()) {
            ArrayList<String> result = new ArrayList<String>();
            for (String item : current) {
                result.addAll(analyse(item, c, true));
            }
            current = result;
        }
        tokens = current;
    }

    public ArrayList<String> getTokens() {
        return tokens;
    }

    /**
     * Returns the token count.
     *
     * @return the number of tokens
     */
    public int countTokens() {
        return tokens.size();
    }

    /**
     * Returns the token at a given index.
     *
     * @param index index starts with 0
     * @return the token at the specified index
     */
    public String tokenAt(int index) {
        return tokens.get(index);
    }
}
