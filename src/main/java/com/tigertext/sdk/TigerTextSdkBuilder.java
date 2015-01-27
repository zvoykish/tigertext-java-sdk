package com.tigertext.sdk;

import com.tigertext.sdk.authorization.Credentials;
import com.tigertext.sdk.impl.TigerTextSdkImpl;

/**
 * Created by Zvika on 1/27/15.
 */
public class TigerTextSdkBuilder {
    public static TigerTextSdk build(Credentials credentials) {
        return new TigerTextSdkImpl(credentials);
    }
}
