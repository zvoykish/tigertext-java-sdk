package com.tigertext.sdk.impl;

import com.tigertext.sdk.entities.Attachment;
import com.tigertext.sdk.entities.impl.AttachmentImpl;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Created by Zvika on 1/28/15.
 */
public class AttachmentBuilder {
    public static Attachment fromJson(ObjectNode node) {
        String contentType = node.path("content-type").asText();
        long size = node.path("size").asLong();
        String attachmentId = node.path("attachment_id").asText();
        return new AttachmentImpl(contentType, size, attachmentId);
    }
}
