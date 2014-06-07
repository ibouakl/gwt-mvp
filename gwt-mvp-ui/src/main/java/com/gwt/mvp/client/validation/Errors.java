package com.gwt.mvp.client.validation;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 */
public class Errors {

    private static String NESTED_SEPARATOR = ".";
    private final List<ErrorItem> items = new LinkedList<ErrorItem>();
    private String nestedPath = "";
    private Stack<String> pathStack = new Stack<String>();

    public Errors() {
        super();
    }

    void reject(String field, String errorCode) {
        reject(field, errorCode, null, null);
    }

    void reject(String field, String errorCode, String defaultMessage) {
        reject(field, errorCode, null, defaultMessage);
    }

    /**
     * Register a field error for the specified field of the current object, using the given error
     * description.
     * <p>The field name may be <code>null</code> or empty String to indicate
     * the current object itself rather than a field of it. This may result
     * in a corresponding field error within the nested object graph or a
     * global error if the current object is the top object.
     * @param field the field name (may be <code>null</code> or empty String)
     * @param errorCode error code, interpretable as a message key
     * @param errorArgs error arguments, for argument binding via MessageFormat
     * (can be <code>null</code>)
     * @param defaultMessage fallback default message
     */
    void reject(String field, String errorCode, Object[] errorArgs, String defaultMessage) {
        items.add(new ErrorItem(fixedField(field), errorCode, errorArgs, defaultMessage));
    }

    /**
     * Return if there were any errors.
     */
    boolean hasErrors() {
        return !items.isEmpty();
    }

    /**
     * Allow context to be changed so that standard validators can validate
     * subtrees. Reject calls prepend the given path to the field names.
     * <p>For example, an address validator could validate the subobject
     * "address" of a customer object.
     * @param nestedPath nested path within this object,
     * e.g. "address" (defaults to "", <code>null</code> is also acceptable).
     * Can end with a dot: both "address" and "address." are valid.
     */
    public void setNestedPath(final String nestedPath) {
        doSetNestedPath(nestedPath);
        pathStack.clear();
    }

    /**
     * Return the current nested path of this {@link Errors} object.
     * <p>Returns a nested path with a dot, i.e. "address.", for easy
     * building of concatenated paths. Default is an empty String.
     */
    public String getNestedPath() {
        return nestedPath;
    }

    /**
     * Push the given sub path onto the nested path stack.
     * <p>A {@link #popNestedPath()} call will reset the original
     * nested path before the corresponding
     * <code>pushNestedPath(String)</code> call.
     * <p>Using the nested path stack allows to set temporary nested paths
     * for subobjects without having to worry about a temporary path holder.
     * <p>For example: current path "spouse.", pushNestedPath("child") ->
     * result path "spouse.child."; popNestedPath() -> "spouse." again.
     * @param subPath the sub path to push onto the nested path stack
     * @see #popNestedPath
     */
    public void pushNestedPath(final String subPath) {
        pathStack.push(getNestedPath());
        doSetNestedPath(getNestedPath() + subPath);
    }

    /**
     * Pop the former nested path from the nested path stack.
     * @throws IllegalStateException if there is no former nested path on the stack
     * @see #pushNestedPath
     */
    public void popNestedPath() throws IllegalStateException {
        try {
            doSetNestedPath(pathStack.pop());
        } catch (EmptyStackException ex) {
            throw new IllegalStateException("Cannot pop nested path: no nested path on stack");
        }
    }

    /**
     * Actually set the nested path.
     * Delegated to by setNestedPath and pushNestedPath.
     */
    protected void doSetNestedPath(final String nestedPath) {
        if (nestedPath == null) {
            this.nestedPath = "";
        } else {
            this.nestedPath = nestedPath;
            if (nestedPath.length() > 0 && !nestedPath.endsWith(NESTED_SEPARATOR)) {
                this.nestedPath += NESTED_SEPARATOR;
            }
        }
    }

    /**
     * Transform the given field into its full path,
     * regarding the nested path of this instance.
     */
    protected String fixedField(final String field) {
        if (field != null && !"".equals(field)) {
            return getNestedPath() + field;
        } else {
            String path = getNestedPath();
            return (path.endsWith(NESTED_SEPARATOR) ? path.substring(0, path.length() - NESTED_SEPARATOR.length()) : path);
        }
    }

    /**
     * Get all errors associated with the given field.
     * <p>Implementations should support not only full field names like
     * "name" but also pattern matches like "na*" or "address.*".
     * @param field the field name
     * @return a List of {@link FieldError} instances
     */
    private List<ErrorItem> getFieldErrors(String field) {
        List<ErrorItem> result = new LinkedList<ErrorItem>();
        String fixedField = fixedField(field);
        for (ErrorItem item : items) {
            if (isMatchingFieldError(fixedField, item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Check whether the given ErrorItem matches the given field.
     * @param field the field that we are looking up FieldErrors for
     * @param fieldError the candidate ErrorItem
     * @return whether the ErrorItem matches the given field
     */
    private boolean isMatchingFieldError(String field, ErrorItem item) {
        return (field.equals(item.getField()) || (field.endsWith("*") && item.getField().startsWith(field.substring(0, field.length() - 1))));
    }

    /**
     * Inner class for error management.
     */
    private class ErrorItem {

        private final String field;
        private final String errorCode;
        private final Object[] arguments;
        private final String defaultMessage;

        public ErrorItem(String field, String errorCode, Object[] arguments, String defaultMessage) {
            this.field = field;
            this.errorCode = errorCode;
            this.arguments = arguments;
            this.defaultMessage = defaultMessage;
        }

        public String getField() {
            return field;
        }
    }
}
