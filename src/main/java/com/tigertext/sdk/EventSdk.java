package com.tigertext.sdk;

import com.tigertext.sdk.events.EventHandler;

/**
 * Created by Zvika on 1/28/15.
 */
public interface EventSdk {
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
}
