package com.tigertext.sdk.entities.impl;

import com.tigertext.sdk.entities.Message;
import com.tigertext.sdk.entities.MessageStatus;

import java.util.Date;

/**
 * Created by Zvika on 1/27/15.
 */
public class MessageImpl implements Message {
    private String id;
    private String sender;
    private String senderOrganization;
    private String recipient;
    private String recipientOrganization;
    private Date creationTime;
    private String body;
    private int ttl;
    private boolean dor;
    private MessageStatus status;

    public MessageImpl(String id, String sender, String senderOrganization, String recipient, String recipientOrganization,
                       Date creationTime, String body, int ttl, boolean dor, MessageStatus status) {
        this.id = id;
        this.sender = sender;
        this.senderOrganization = senderOrganization;
        this.recipient = recipient;
        this.recipientOrganization = recipientOrganization;
        this.creationTime = creationTime;
        this.body = body;
        this.ttl = ttl;
        this.dor = dor;
        this.status = status;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public String getSenderOrganization() {
        return senderOrganization;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    @Override
    public String getRecipientOrganization() {
        return recipientOrganization;
    }

    @Override
    public Date getCreationTime() {
        return creationTime;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public int getTimeToLive() {
        return ttl;
    }

    @Override
    public boolean isDeletedOnRead() {
        return dor;
    }

    @Override
    public MessageStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageImpl message = (MessageImpl) o;

        if (dor != message.dor) return false;
        if (ttl != message.ttl) return false;
        if (body != null ? !body.equals(message.body) : message.body != null) return false;
        if (creationTime != null ? !creationTime.equals(message.creationTime) : message.creationTime != null)
            return false;
        if (id != null ? !id.equals(message.id) : message.id != null) return false;
        if (recipient != null ? !recipient.equals(message.recipient) : message.recipient != null) return false;
        if (recipientOrganization != null ? !recipientOrganization.equals(message.recipientOrganization) : message.recipientOrganization != null)
            return false;
        if (sender != null ? !sender.equals(message.sender) : message.sender != null) return false;
        if (senderOrganization != null ? !senderOrganization.equals(message.senderOrganization) : message.senderOrganization != null)
            return false;
        if (status != message.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (senderOrganization != null ? senderOrganization.hashCode() : 0);
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (recipientOrganization != null ? recipientOrganization.hashCode() : 0);
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + ttl;
        result = 31 * result + (dor ? 1 : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageImpl{" +
                "id='" + id + '\'' +
                ", sender='" + sender + '\'' +
                ", senderOrganization='" + senderOrganization + '\'' +
                ", recipient='" + recipient + '\'' +
                ", recipientOrganization='" + recipientOrganization + '\'' +
                ", creationTime=" + creationTime +
                ", body='" + body + '\'' +
                ", ttl=" + ttl +
                ", dor=" + dor +
                ", status=" + status +
                '}';
    }
}
