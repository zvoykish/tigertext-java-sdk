package com.tigertext.sdk.impl;

import com.tigertext.sdk.events.TigerTextEvent;
import org.codehaus.jackson.JsonNode;

/**
 * Created by Zvika on 1/28/15.
 */
public class TigerTextEventImpl implements TigerTextEvent {
    private String type;
    private JsonNode content;

    public TigerTextEventImpl(String type, JsonNode content) {
        this.type = type;
        this.content = content;
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
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TigerTextEventImpl{" +
                "type='" + type + '\'' +
                ", content=" + content +
                '}';
    }
}
