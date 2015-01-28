package com.tigertext.sdk.events;

import org.codehaus.jackson.JsonNode;

/**
 * Created by Zvika on 1/28/15.
 */
public interface TigerTextEvent {
    /**
     * Returns the event ID
     *
     * @return The event ID
     */
    String getId();

    /**
     * Returns the event type
     *
     * @return The event type
     */
    String getType();

    /**
     * Returns the full event payload
     *
     * @return The event payload
     */
    JsonNode getPayload();
}
