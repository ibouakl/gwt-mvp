package com.gwt.ui.client.masterview;

public class ValidatorBuilder {

    public static Validator buildFromFilterExpression(String expression) {
        String regexp;
        regexp = expression.replaceAll("[*]", ".*").replaceAll("[?]", ".");
        return new RegexpValidator(regexp);
    }
}
