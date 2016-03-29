package com.tigertext.sdk.authorization;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by Zvika on 1/27/15.
 */
public class ApiCredentials implements Credentials {
    private final String key;
    private final String secret;

    public ApiCredentials(String key, String secret) {
        this.key = key;
        this.secret = secret;
    }

    @Override
    public String getAuthorization() {
        String authStr = key + ":" + secret;
        return "Basic " + Base64.encodeBase64String(authStr.getBytes());
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }
}
