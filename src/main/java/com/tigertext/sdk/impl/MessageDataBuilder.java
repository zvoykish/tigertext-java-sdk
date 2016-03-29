package com.tigertext.sdk.impl;

import com.tigertext.sdk.entities.MessageData;
import com.tigertext.sdk.entities.impl.MessageDataImpl;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Created by Zvika on 1/28/15.
 */
class MessageDataBuilder {
    static MessageData fromJson(ObjectNode node) {
        String mimeType = node.path("mimetype").asText();
        String namespace = node.path("namespace").asText();
        String payload = node.path("payload").asText();
        return new MessageDataImpl(mimeType, namespace, payload);
    }
}
