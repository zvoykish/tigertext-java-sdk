package com.tigertext.sdk;

import com.tigertext.sdk.events.EventHandler;

import java.util.List;

/**
 * Created by Zvika on 1/28/15.
 */
public interface EventSdk {
    public enum Feature {
        AUTO_ACK("auto-ack"), AUTO_DELIVER("auto-deliver");

        private final String headerValue;

        private Feature(String headerValue) {
            this.headerValue = headerValue;
        }

        public String getHeaderValue() {
            return headerValue;
        }
    }

    /**
     * Connects to the SSE endpoint and registers the event handler for incoming events
     *
     * @param handler The handler, will be invoked per event received
     */
    void connect(EventHandler handler);

    /**
     * Disconnects from the SSE endpoint and drops any open connection
     */
    void disconnect();

    /**
     * Manually ack the given event
     *
     * @param eventId The event ID
     * @see com.tigertext.sdk.EventSdk.Feature#AUTO_ACK
     */
    void ack(String eventId);

    /**
     * Manually ack the given events
     *
     * @param eventIds The event ID
     * @see com.tigertext.sdk.EventSdk.Feature#AUTO_ACK
     */
    void ack(List<String> eventIds);

    /**
     * Enable the given Event SDK feature
     *
     * @param feature The feature to enable
     */
    EventSdk enable(Feature feature);

    /**
     * Disable the given Event SDK feature
     *
     * @param feature The feature to disable
     */
    EventSdk disable(Feature feature);
}
