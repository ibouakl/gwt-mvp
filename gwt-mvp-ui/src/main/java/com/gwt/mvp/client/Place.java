package com.gwt.mvp.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A place represents a particular 'bookmark' or location inside the
 * application. A place is stateful - it may represent a location with it's
 * current settings, such as a particular ID value, or other unique indicators
 * that will allow a user to track back to that location later, either via a
 * browser bookmark, or by clicking the 'back' button.
 * <p/>
 * However, there may be more than one instance of concrete Place classes, so the state should be shared between all instances of any given
 * class. Usually this is done via a shared class, such as a {@link Presenter} instance.
 * <p/>
 * Merge a Place and old Place Request in the same class, and add this concept in main package.
 * 
 * 
 * @author jguibert
 * @author David Peterson
 */
public class Place {
    
    /** place identifier */
    private final String placeId;
    /** list or pair[name, value] */
    private final Map<String, String> params;
    /** true if this place is in history */
    private final boolean fromHistory;
    private final static transient Set<String> EMPTYSET = new HashSet<String>();
    
    /**
     * Build a new instance of <code>PlaceRequest</code>.
     * 
     * @param place
     */
    public Place(final String placeId) {
        this.placeId = placeId;
        this.params = null;
        fromHistory = false;
    }
    
    /**
     * Build a new instance of <code>PlaceRequest</code>.
     * 
     * @param placeRequest
     * @param fromHistory
     */
    public Place(final Place placeRequest, final boolean fromHistory) {
        this.placeId = placeRequest.placeId;
        this.params = placeRequest.params;
        this.fromHistory = fromHistory;
    }
    
    /**
     * Build a new instance of <code>PlaceRequest</code>.
     * 
     * @param req
     * @param name
     * @param value
     */
    public Place(final Place placeRequest, final String name, final String value) {
        this.placeId = placeRequest.placeId;
        this.fromHistory = placeRequest.fromHistory;
        this.params = new HashMap<String, String>();
        if (placeRequest.params != null)
            this.params.putAll(placeRequest.params);
        this.params.put(name, value);
        
    }
    
    /**
     * Returns the unique name for this place. Uniqueness is not enforced -
     * creators must ensure that the name is unique or there will be potential
     * issues with multiple places responding to the same History token.
     * 
     * @return The place ID.
     */
    public String getPlaceId() {
        return placeId;
    }
    
    /**
     * @return a set of parameter names.
     */
    public Set<String> getParameterNames() {
        if (params != null) {
            return params.keySet();
        } else {
            return EMPTYSET;
        }
    }
    
    /**
     * Return parameter value as String.
     * 
     * @param name the parameter name
     * @param defaultValue default value returned if no params is found or if value is null.
     * @return value or default value.
     */
    public String getParameter(final String name, final String defaultValue) {
        String value = null;
        if (params != null)
            value = params.get(name);
        if (value == null)
            value = defaultValue;
        return value;
    }
    
    /**
     * Return parameter value as Long.
     * 
     * @param name the parameter name
     * @param defaultValue default value returned if no params is found or if value is null.
     * @return value or default value.
     */
    
    public Long getParameterAsLong(final String name, final Long defaultValue) {
        String value = getParameter(name, null);
        return value != null ? Long.parseLong(value) : defaultValue;
    }
    
    /**
     * Return parameter value as Integer.
     * 
     * @param name the parameter name
     * @param defaultValue default value returned if no params is found or if value is null.
     * @return value or default value.
     */
    
    public Integer getParameterAsInteger(final String name, final Integer defaultValue) {
        String value = getParameter(name, null);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }
    
    /**
     * Return parameter value as Boolean.
     * 
     * @param name the parameter name
     * @param defaultValue default value returned if no params is found or if value is null.
     * @return value or default value.
     */
    
    public Boolean getParameterAsBoolean(final String name, final Boolean defaultValue) {
        String value = getParameter(name, null);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
    
    /**
     * Returns a new instance of the request with the specified parameter name
     * and value. If a parameter with the same name was previously specified,
     * the new request contains the new value.
     * 
     * @param name
     *            The new parameter name.
     * @param value
     *            The new String parameter value.
     * @return The new place request instance.
     */
    public Place with(final String name, final String value) {
        return new Place(this, name, value);
    }
    
    /**
     * Returns a new instance of the request with the specified parameter name
     * and value. If a parameter with the same name was previously specified,
     * the new request contains the new value.
     * 
     * @param name
     *            The new parameter name.
     * @param value
     *            The new Long parameter value.
     * @return The new place request instance.
     */
    public Place with(final String name, final Long value) {
        return new Place(this, name, value != null ? value.toString() : null);
    }
    
    /**
     * Returns a new instance of the request with the specified parameter name
     * and value. If a parameter with the same name was previously specified,
     * the new request contains the new value.
     * 
     * @param name
     *            The new parameter name.
     * @param value
     *            The new Integer parameter value.
     * @return The new place request instance.
     */
    public Place with(final String name, final Integer value) {
        return new Place(this, name, value != null ? value.toString() : null);
    }
    
    /**
     * Returns a new instance of the request with the specified parameter name
     * and value. If a parameter with the same name was previously specified,
     * the new request contains the new value.
     * 
     * @param name
     *            The new parameter name.
     * @param value
     *            The new Boolean parameter value.
     * @return The new place request instance.
     */
    public Place with(final String name, final Boolean value) {
        return new Place(this, name, value != null ? value.toString() : null);
    }
    
    public boolean isFromHistory() {
        return fromHistory;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((params == null) ? 0 : params.hashCode());
        result = prime * result + ((placeId == null) ? 0 : placeId.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Place other = (Place)obj;
        if (params == null) {
            if (other.params != null)
                return false;
        } else if (!params.equals(other.params))
            return false;
        if (placeId == null) {
            if (other.placeId != null)
                return false;
        } else if (!placeId.equals(other.placeId))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "Place [ placeId=" + placeId + ", params=" + params + "]";
    }
    
}
