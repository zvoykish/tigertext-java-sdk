package com.tigertext.sdk;

import com.tigertext.sdk.authorization.ApiCredentials;
import com.tigertext.sdk.entities.Message;
import com.tigertext.sdk.entities.User;
import com.tigertext.sdk.events.EventHandler;
import com.tigertext.sdk.events.TigerTextEvent;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import static com.tigertext.sdk.TigerTextConfiguration.getApiKey;
import static com.tigertext.sdk.TigerTextConfiguration.getApiSecret;

/**
 * Created by Zvika on 1/27/15.
 */
public class MainTest {
    private static Logger log = Logger.getLogger(MainTest.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);

        // Build a new SDK using the API key/secret in tigertext.properties
        ApiCredentials credentials = new ApiCredentials(getApiKey(), getApiSecret());
        TigerTextSdk sdk = TigerTextSdkBuilder.build(credentials);
        EventSdk eventSdk = sdk.events();
        eventSdk.connect(new EventHandler() {
            @Override
            public void onEvent(TigerTextEvent event, TigerTextSdk sdk) {
                if ("tigertext:iq:message".equals(event.getType())) {
                    log.info("Received message: " + event.getPayload());
                }
            }
        });

        // Example of fetching a user based on identifier
        UserSdk userSdk = sdk.users();
        User user = userSdk.get("recipient-identifier");
        log.info("Here's the user: " + user);

        // Example of sending out a simple text message to a recipient
        MessageSdk messageSdk = sdk.messages();
        String messageId = messageSdk.send("Message body", "another-user-identifier");
        log.info("New message ID: " + messageId);

        Message message = messageSdk.get(messageId);
        log.info("New message properties: " + message);

        eventSdk.disconnect();
    }
}
