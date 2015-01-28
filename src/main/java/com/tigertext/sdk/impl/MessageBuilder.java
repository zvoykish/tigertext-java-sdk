package com.tigertext.sdk.impl;

import com.tigertext.sdk.entities.Attachment;
import com.tigertext.sdk.entities.Message;
import com.tigertext.sdk.entities.MessageData;
import com.tigertext.sdk.entities.MessageStatus;
import com.tigertext.sdk.entities.impl.MessageImpl;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zvika on 1/27/15.
 */
public class MessageBuilder {
    public static Message fromJson(ObjectNode node) throws ParseException {
        String messageId = node.path("message_id").asText();
        String senderToken = node.path("sender_token").asText();
        String senderOrganization = node.path("sender_organization").asText();
        String recipientToken = node.path("recipient_token").asText();
        String recipientOrganization = node.path("recipient_organization").asText();
        String createdTimeStr = node.path("created_time").asText();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date createdTime = df.parse(createdTimeStr);
        String body = node.path("body").asText();
        int ttl = node.path("ttl").asInt();
        boolean dor = node.path("dor").asBoolean();
        MessageStatus status = MessageStatus.valueOf(node.path("status").asText());

        List<Attachment> attachments = new ArrayList<Attachment>();
        for (JsonNode attachmentNode : node.path("attachments")) {
            attachments.add(AttachmentBuilder.fromJson((ObjectNode) attachmentNode));
        }

        List<MessageData> messageData = new ArrayList<MessageData>();
        for (JsonNode dataNode : node.path("data")) {
            messageData.add(MessageDataBuilder.fromJson((ObjectNode) dataNode));
        }

        return new MessageImpl(messageId, senderToken, senderOrganization, recipientToken, recipientOrganization,
                createdTime, body, ttl, dor, status, attachments, messageData);
    }
}
