package com.tigertext.sdk;

import com.tigertext.sdk.entities.User;

/**
 * Created by Zvika on 1/27/15.
 */
public interface TigerTextSdk {
    User getUser(String identifier);
}
