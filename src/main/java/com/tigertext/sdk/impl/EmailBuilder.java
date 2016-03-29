package com.tigertext.sdk.impl;

import com.tigertext.sdk.entities.Email;
import com.tigertext.sdk.entities.impl.EmailImpl;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Created by Zvika on 1/27/15.
 */
class EmailBuilder {
    static Email fromJson(ObjectNode node) {
        String address = node.path("address").asText();
        boolean verified = node.path("verified").asBoolean();
        return new EmailImpl(address, verified);
    }
}
