package com.tigertext.sdk;

/**
 * Created by Zvika on 1/27/15.
 */
public interface TigerTextSdk {
    UserSdk users();

    MessageSdk messages();

    EventSdk events();
}
