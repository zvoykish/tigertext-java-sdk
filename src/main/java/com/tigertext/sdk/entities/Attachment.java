package com.tigertext.sdk.entities;

/**
 * Created by Zvika on 1/28/15.
 */
public interface Attachment {
    String getContentType();

    long getSize();

    String getAttachmentId();
}
