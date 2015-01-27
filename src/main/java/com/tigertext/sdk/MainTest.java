package com.tigertext.sdk;

import com.tigertext.sdk.authorization.ApiCredentials;
import com.tigertext.sdk.entities.User;

import static com.tigertext.sdk.TigerTextConfiguration.getApiKey;
import static com.tigertext.sdk.TigerTextConfiguration.getApiSecret;

/**
 * Created by Zvika on 1/27/15.
 */
public class MainTest {
    public static void main(String[] args) {
        // Build a new SDK using the API key/secret in tigertext.properties
        ApiCredentials credentials = new ApiCredentials(getApiKey(), getApiSecret());
        TigerTextSdk tigerTextSdk = TigerTextSdkBuilder.build(credentials);

        // Example of fetching a user based on identifier
        User user = tigerTextSdk.getUser("user-identifier");
        System.out.println("Here's the user: " + user);

        // Example of sending out a simple text message to a recipient
        String messageId = tigerTextSdk.sendMessage("Test msg", "recipient-identifier");
        System.out.println("New message ID: " + messageId);
    }
}
