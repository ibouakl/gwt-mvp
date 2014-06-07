package com.gwt.ui.client.masterview;

import com.google.gwt.user.client.ui.HasText;

public class RegexpValidator implements Validator {

    protected String regexp;

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public RegexpValidator(String regexp) {
        this.regexp = regexp;
    }

    @Override
    public boolean isValid(Object object) {
        String s;
        if (object instanceof HasText) {
            s = ((HasText) object).getText();
        } else {
            s = object.toString();
        }
        if (regexp.contains("*") || regexp.contains("?")) {
            return s.matches(regexp);
        } else {
            return s.toLowerCase().startsWith(regexp.toLowerCase());
        }
    }
}
