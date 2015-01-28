package com.tigertext.sdk.entities;

/**
 * Created by Zvika on 1/28/15.
 */
public interface MessageData {
    String getMimeType();

    String getNamespace();

    String getPayload();
}
