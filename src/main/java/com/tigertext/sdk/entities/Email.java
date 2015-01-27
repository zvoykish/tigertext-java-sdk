package com.tigertext.sdk.entities;

/**
 * Created by Zvika on 1/27/15.
 */
public interface Email {
    String getAddress();

    boolean isVerified();
}
