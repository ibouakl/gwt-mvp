package com.gwt.mvp.client;

import java.util.Set;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.gwt.mvp.client.event.PlaceChangedEvent;
import com.gwt.mvp.client.event.PlaceChangedHandler;
import com.gwt.mvp.client.event.PlaceRequestEvent;
import com.gwt.mvp.client.event.PlaceRequestHandler;
import com.gwt.mvp.client.event.SessionAttributeChangedEvent;

import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>EventBusManager</code> class provide default implementation of <code>EventBus</code>.
 * 
 * @author jguibert
 * @author ibouakl
 */
public class EventBusManager extends HandlerManager implements EventBus {

    private LocalSession localSession;

    /**
     * Build a new instance of <code>EventBusManager</code>.
     * Place Handler will be registered.
     */
    public EventBusManager() {
        this(true);
    }

    /**
     * Build a new instance of <code>EventBusManager</code>.
     * 
     * @param addPlaceHandler if true, register place handler
     */
    public EventBusManager(final boolean addPlaceHandler) {
        this(null, addPlaceHandler);
    }

    /**
     * Build a new instance of <code>EventBusManager</code>.
     * 
     * @param source the event source
     * @param addPlaceHandler if true, register place handler
     */
    public EventBusManager(final Object source, final boolean addPlaceHandler) {
        super(source);
        if (addPlaceHandler) {
            registerPlaceHandler();
        }
        localSession = null;
    }

    @Override
    public Session getSession() {
        return getSession(false);
    }

    @Override
    public Session getSession(boolean create) {
        if (create && (localSession == null)) {
            localSession = new LocalSession();
        }
        return localSession;
    }

    /**
     * Resister Place Handler.
     */
    private void registerPlaceHandler() {
        PlaceManagerHandler placeManagerHandler = new PlaceManagerHandler(this);
        // Register placeManagerHandler with the History API.
        History.addValueChangeHandler(placeManagerHandler);
        // Listen for manual place change events.
        addHandler(PlaceChangedEvent.TYPE, placeManagerHandler);
        addHandler(PlaceRequestEvent.TYPE, placeManagerHandler);
    }

    @Override
    public boolean fireCurrentPlace() {
        boolean result = false;
        if (History.getToken() != null && History.getToken().length() != 0) {
            History.fireCurrentHistoryState();
            result = true;
        }
        return result;
    }

    /**
     * <code>PlaceManagerHandler</code> internal handler.
     * 
     * @author Jerome Guibert
     */
    private static class PlaceManagerHandler implements ValueChangeHandler<String>, PlaceChangedHandler, PlaceRequestHandler {

        private final EventBus eventBus;
        private final TokenFormatter tokenFormatter;

        /**
         * Build a new instance of <code>PlaceManagerHandler</code>.
         * 
         * @param eventBus event bus instance.
         */
        public PlaceManagerHandler(final EventBus eventBus) {
            super();
            this.eventBus = eventBus;
            this.tokenFormatter = new TokenFormatter();
        }

        @Override
        public void onValueChange(final ValueChangeEvent<String> event) {
            try {
                eventBus.fireEvent(new PlaceRequestEvent(new Place(tokenFormatter.toPlace(event.getValue()), true)));
            } catch (IllegalArgumentException e) {
                // log something
            }
        }

        @Override
        public void onPlaceChange(final Place place) {
            if (!place.isFromHistory()) {
                History.newItem(tokenFormatter.toHistoryToken(place), false);
            }
        }

        @Override
        public void onPlaceRequest(final Place place) {
            if (!place.isFromHistory()) {
                History.newItem(tokenFormatter.toHistoryToken(place), false);
            }
        }
    }

    /**
     * Formats tokens from String values into <code>Place</code> values and back again. This implementation
     * parses the token format like so:
     * 
     * <pre>
     * [name](;param=value)*
     * </pre>
     */
    private static class TokenFormatter {

        private static final String PARAM_SEPARATOR = ";";
        private static final String PARAM_PATTERN = PARAM_SEPARATOR + "(?!" + PARAM_SEPARATOR + ")";
        private static final String PARAM_ESCAPE = PARAM_SEPARATOR + PARAM_SEPARATOR;
        private static final String VALUE_SEPARATOR = "=";
        private static final String VALUE_PATTERN = VALUE_SEPARATOR + "(?!" + VALUE_SEPARATOR + ")";
        private static final String VALUE_ESCAPE = VALUE_SEPARATOR + VALUE_SEPARATOR;

        /**
         * Build a new instance of <code>TokenFormatter</code>.
         */
        public TokenFormatter() {
            super();
        }

        /**
         * Converts a {@link Place} into a {@link com.google.gwt.user.client.History} token.
         * 
         * @param place The place request
         * @return The history token
         */
        public String toHistoryToken(final Place place) {
            StringBuilder out = new StringBuilder();
            out.append(place.getPlaceId());

            Set<String> params = place.getParameterNames();
            if (params != null && params.size() > 0) {
                for (String name : params) {
                    out.append(PARAM_SEPARATOR);
                    out.append(escape(name)).append(VALUE_SEPARATOR).append(escape(place.getParameter(name, null)));
                }
            }
            return out.toString();
        }

        /**
         * Converts a {@link com.google.gwt.user.client.History} token into a {@link Place}.
         * 
         * @param token The token.
         * @return The place request
         * @throws IllegalArgumentException if there is an error converting.
         */
        public Place toPlace(final String token) throws IllegalArgumentException {
            Place req = null;

            int split = token.indexOf(PARAM_SEPARATOR);
            if (split == 0) {
                throw new IllegalArgumentException("Place name is missing.");
            } else if (split == -1) {
                req = new Place(token);
            } else if (split >= 0) {
                req = new Place(token.substring(0, split));
                String paramsChunk = token.substring(split + 1);
                String[] paramTokens = paramsChunk.split(PARAM_PATTERN);
                for (String paramToken : paramTokens) {
                    String[] param = paramToken.split(VALUE_PATTERN);
                    if (param.length != 2) {
                        throw new IllegalArgumentException("Bad parameter: Parameters require a single '" + VALUE_SEPARATOR + "' between the key and value.");
                    }
                    req = req.with(unescape(param[0]), unescape(param[1]));
                }
            }

            return req;

        }

        private String escape(final String value) {
            return value.replaceAll(PARAM_SEPARATOR, PARAM_ESCAPE).replaceAll(VALUE_SEPARATOR, VALUE_ESCAPE);
        }

        private String unescape(final String value) {
            return value.replaceAll(PARAM_ESCAPE, PARAM_SEPARATOR).replaceAll(VALUE_ESCAPE, VALUE_SEPARATOR);
        }
    }

    /**
     * Local Session is a container of String key/value. This object by pass loozly coupled mechanism.
     *
     * @author Jerome Guibert
     */
    private class LocalSession implements Session {

        private final Map<String, Object> attributes;
        private final long created;

        public LocalSession() {
            super();
            attributes = new HashMap<String, Object>();
            created = new Date().getTime();
        }

        /**
         * Returns the time when this session was created, measured in milliseconds since midnight January 1, 1970 GMT.
         * @return
         */
        @Override
        public long getCreationTime() {
            return created;
        }

        /**
         * Returns the object bound with the specified name in this session, or null if no object is bound under the name.
         * @param name
         * @return
         */
        @Override
        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        /**
         * Binds an object to this session, using the name specified.
         * @param name
         * @param value
         */
        @Override
        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
            fireEvent(new SessionAttributeChangedEvent(name, value));
        }

        /**
         * Returns an Enumeration of String objects containing the names of all the objects bou
         * @return
         */
        @Override
        public Enumeration<String> getAttributeNames() {
            return Collections.enumeration(attributes.keySet());
        }

        /**
         *   Invalidates this session and unbinds any objects bound to it.
         */
        @Override
        public void invalidate() {
            attributes.clear();
        }
    }
}
