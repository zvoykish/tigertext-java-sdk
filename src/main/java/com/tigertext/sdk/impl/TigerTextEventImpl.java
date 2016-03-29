package com.tigertext.sdk.impl;

import com.tigertext.sdk.events.TigerTextEvent;
import org.codehaus.jackson.JsonNode;

/**
 * Created by Zvika on 1/28/15.
 */
class TigerTextEventImpl implements TigerTextEvent {
    private final String id;
    private final String type;
    private final JsonNode content;

    TigerTextEventImpl(String id, String type, JsonNode content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public JsonNode getPayload() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TigerTextEventImpl that = (TigerTextEventImpl) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TigerTextEventImpl{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", content=" + content +
                '}';
    }
}
