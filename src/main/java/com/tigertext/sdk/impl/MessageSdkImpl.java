package com.tigertext.sdk.impl;

import com.tigertext.sdk.MessageSdk;
import com.tigertext.sdk.authorization.Credentials;
import com.tigertext.sdk.entities.Message;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zvika on 1/28/15.
 */
class MessageSdkImpl extends BaseSdk implements MessageSdk {
    private static final String MESSAGE_API = BASE_URL + "/v2/message/";

    MessageSdkImpl(Credentials credentials) {
        super(credentials);
    }

    @Override
    public String send(String body, String recipient) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("body", body);
            params.put("recipient", recipient);
            HttpPost request = createPostRequest(MESSAGE_API, params);
            CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case HttpStatus.SC_NO_CONTENT:
                    return response.getFirstHeader("TT-X-Message-Id").getValue();
                default:
                    log.error("Unexpected status code: " + statusCode + ". Response: " + response);
            }
        } catch (IOException e) {
            log.error("Failed sending message to: " + recipient, e);
        }

        return null;
    }

    @Override
    public Message get(String messageId) {
        HttpGet request = createGetRequest(MESSAGE_API + messageId);
        try {
            // TODO: Make this work without disableContentCompression (throws EOFException )
            CloseableHttpResponse response = HttpClientBuilder.create().disableContentCompression().build().execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case HttpStatus.SC_OK:
                    HttpEntity entity = response.getEntity();
                    String body = EntityUtils.toString(entity);
                    JsonNode node = new ObjectMapper().readTree(body);
                    ObjectNode replyNode = (ObjectNode) node.get("reply");
                    return MessageBuilder.fromJson(replyNode);
                default:
                    log.error("Unexpected status code: " + statusCode + ". Response: " + response);
            }
        } catch (Exception e) {
            log.error("Failed loading message with ID: " + messageId, e);
        }

        return null;
    }
}
