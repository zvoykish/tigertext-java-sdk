package com.tigertext.sdk.authorization;

import sun.misc.BASE64Encoder;

/**
 * Created by Zvika on 1/27/15.
 */
public class ApiCredentials implements Credentials {
    private String key;
    private String secret;

    public ApiCredentials(String key, String secret) {
        this.key = key;
        this.secret = secret;
    }

    @Override
    public String getAuthorization() {
        String authStr = key + ":" + secret;
        return "Basic " + new BASE64Encoder().encode(authStr.getBytes());
    }
}
