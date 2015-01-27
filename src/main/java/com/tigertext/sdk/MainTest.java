package com.tigertext.sdk;

import com.tigertext.sdk.authorization.ApiCredentials;
import com.tigertext.sdk.entities.User;

import static com.tigertext.sdk.TigerTextConfiguration.*;

/**
 * Created by Zvika on 1/27/15.
 */
public class MainTest {
    public static void main(String[] args) {
        ApiCredentials credentials = new ApiCredentials(getApiKey(), getApiSecret());
        TigerTextSdk tigerTextSdk = TigerTextSdkBuilder.build(credentials);
        User user = tigerTextSdk.getUser("user-identifier");
        System.out.println("Here's the user: " + user);
    }
}
