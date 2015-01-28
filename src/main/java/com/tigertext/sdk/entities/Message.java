package com.tigertext.sdk.entities;

import java.util.Date;

/**
 * Created by Zvika on 1/27/15.
 */
public interface Message {
    String getId();

    String getSender();

    String getSenderOrganization();

    String getRecipient();

    String getRecipientOrganization();

    Date getCreationTime();

    String getBody();

    int getTimeToLive();

    boolean isDeletedOnRead();

    MessageStatus getStatus();

    // TODO: attachments

    // TODO: MessageData
}
