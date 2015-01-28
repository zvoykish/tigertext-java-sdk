package com.tigertext.sdk.impl;

import com.tigertext.sdk.EventSdk;
import com.tigertext.sdk.MessageSdk;
import com.tigertext.sdk.TigerTextSdk;
import com.tigertext.sdk.UserSdk;
import com.tigertext.sdk.authorization.Credentials;

/**
 * Created by Zvika on 1/27/15.
 */
public class TigerTextSdkImpl extends BaseSdk implements TigerTextSdk {
    private UserSdk userSdk;
    private MessageSdk messageSdk;
    private EventSdk eventSdk;

    public TigerTextSdkImpl(Credentials credentials) {
        super(credentials);
        userSdk = new UserSdkImpl(credentials);
        messageSdk = new MessageSdkImpl(credentials);
        eventSdk = new EventSdkImpl(credentials, this);
    }

    @Override
    public UserSdk users() {
        return userSdk;
    }

    @Override
    public MessageSdk messages() {
        return messageSdk;
    }

    @Override
    public EventSdk events() {
        return eventSdk;
    }
}
