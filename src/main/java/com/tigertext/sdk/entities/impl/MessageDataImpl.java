package com.tigertext.sdk.entities.impl;

import com.tigertext.sdk.entities.MessageData;

/**
 * Created by Zvika on 1/28/15.
 */
public class MessageDataImpl implements MessageData {
    private final String mimeType;
    private final String namespace;
    private final String payload;

    public MessageDataImpl(String mimeType, String namespace, String payload) {
        this.mimeType = mimeType;
        this.namespace = namespace;
        this.payload = payload;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public String getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageDataImpl that = (MessageDataImpl) o;

        if (mimeType != null ? !mimeType.equals(that.mimeType) : that.mimeType != null) return false;
        if (namespace != null ? !namespace.equals(that.namespace) : that.namespace != null) return false;
        if (payload != null ? !payload.equals(that.payload) : that.payload != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mimeType != null ? mimeType.hashCode() : 0;
        result = 31 * result + (namespace != null ? namespace.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageDataImpl{" +
                "mimeType='" + mimeType + '\'' +
                ", namespace='" + namespace + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
