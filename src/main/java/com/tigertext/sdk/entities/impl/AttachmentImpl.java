package com.tigertext.sdk.entities.impl;

import com.tigertext.sdk.entities.Attachment;

/**
 * Created by Zvika on 1/28/15.
 */
public class AttachmentImpl implements Attachment {
    private String contentType;
    private long size;
    private String attachmentId;

    public AttachmentImpl(String contentType, long size, String attachmentId) {
        this.contentType = contentType;
        this.size = size;
        this.attachmentId = attachmentId;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public String getAttachmentId() {
        return attachmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttachmentImpl that = (AttachmentImpl) o;

        if (size != that.size) return false;
        if (attachmentId != null ? !attachmentId.equals(that.attachmentId) : that.attachmentId != null) return false;
        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contentType != null ? contentType.hashCode() : 0;
        result = 31 * result + (int) (size ^ (size >>> 32));
        result = 31 * result + (attachmentId != null ? attachmentId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AttachmentImpl{" +
                "contentType='" + contentType + '\'' +
                ", size=" + size +
                ", attachmentId='" + attachmentId + '\'' +
                '}';
    }
}
