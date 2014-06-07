package com.gwt.mvp.client;

import java.util.Enumeration;

/**
 * Session interface declares methods to deal with attribute on session object.
 * 
 * @author jguibert
 * @author ibouakl
 */
public interface Session {

    /**
     * Returns the object bound with the specified name in this session, or null if no object is bound under the name.
     * @param name
     * @return
     */
    public Object getAttribute(String name);

    /**
     * Returns an Enumeration of String objects containing the names of all the objects bou
     * @return
     */
    public Enumeration<String> getAttributeNames();

    /**
     * Returns the time when this session was created, measured in milliseconds since midnight January 1, 1970 GMT.
     * @return
     */
    public long getCreationTime();

    /**
     * Invalidates this session and unbinds any objects bound to it.
     */
    public void invalidate();

    /**
     * Binds an object to this session, using the name specified.
     * @param name
     * @param value
     */
    public void setAttribute(String name, Object value);
}
