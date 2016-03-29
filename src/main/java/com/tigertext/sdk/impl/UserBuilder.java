package com.tigertext.sdk.impl;

import com.tigertext.sdk.entities.Email;
import com.tigertext.sdk.entities.Phone;
import com.tigertext.sdk.entities.User;
import com.tigertext.sdk.entities.impl.UserImpl;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zvika on 1/27/15.
 */
class UserBuilder {
    static User fromJson(ObjectNode node) {
        String token = node.path("token").asText();
        String status = node.path("status").asText();
        String firstName = node.path("first_name").asText();
        String lastName = node.path("last_name").asText();
        String displayName = node.path("display_name").asText();
        String username = node.path("username").asText();
        String avatarUrl = node.path("avatar").asText();
        List<Email> emailAddresses = new ArrayList<>();
        for (JsonNode emailNode : node.path("emails")) {
            emailAddresses.add(EmailBuilder.fromJson((ObjectNode) emailNode));
        }
        List<Phone> phones = new ArrayList<>();
        for (JsonNode phoneNode : node.path("phones")) {
            phones.add(PhoneBuilder.fromJson((ObjectNode) phoneNode));
        }
        boolean dnd = node.path("dnd").asBoolean();
        String dndMessage = dnd ? node.path("dnd_text").asText() : "";
        return new UserImpl(token, status, firstName, lastName, displayName, username, avatarUrl, emailAddresses, dnd, dndMessage);
    }
}
