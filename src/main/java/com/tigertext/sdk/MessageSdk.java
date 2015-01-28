package com.tigertext.sdk;

import com.tigertext.sdk.entities.Message;

/**
 * Created by Zvika on 1/28/15.
 */
public interface MessageSdk {
    /**
     * Sends a message with the given body to the given recipient.
     *
     * @param body      Message body
     * @param recipient Recipient user can be identified by any of the Addressing Schema types
     * @return The new message ID
     */
    String send(String body, String recipient);

    /**
     * Gets a message details
     *
     * @param messageId The message ID
     * @return A message object with all its properties
     */
    Message get(String messageId);
}
