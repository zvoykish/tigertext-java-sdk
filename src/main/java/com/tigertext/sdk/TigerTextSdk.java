package com.tigertext.sdk;

import com.tigertext.sdk.entities.User;

/**
 * Created by Zvika on 1/27/15.
 */
public interface TigerTextSdk {
    /**
     * Returns the details of the requested user.
     *
     * @param identifier User can be identified by any of the Addressing Schema types
     * @return The user, if such exists, null otherwise
     */
    User getUser(String identifier);

    /**
     * Sends a message with the given body to the given recipient.
     *
     * @param body      Message body
     * @param recipient Recipient user can be identified by any of the Addressing Schema types
     * @return The new message ID
     */
    String sendMessage(String body, String recipient);
}
