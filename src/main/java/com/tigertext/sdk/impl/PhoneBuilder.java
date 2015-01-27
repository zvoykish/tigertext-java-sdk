package com.tigertext.sdk.impl;

import com.tigertext.sdk.entities.Phone;
import com.tigertext.sdk.entities.impl.PhoneImpl;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Created by Zvika on 1/27/15.
 */
public class PhoneBuilder {
    public static Phone fromJson(ObjectNode node) {
        String number = node.path("number").asText();
        boolean verified = node.path("verified").asBoolean();
        return new PhoneImpl(number, verified);
    }
}
