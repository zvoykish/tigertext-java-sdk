package com.tigertext.sdk.impl;

import com.tigertext.sdk.TigerTextConfiguration;
import com.tigertext.sdk.TigerTextSdk;
import com.tigertext.sdk.authorization.Credentials;
import com.tigertext.sdk.entities.User;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zvika on 1/27/15.
 */
public class TigerTextSdkImpl implements TigerTextSdk {
    public static final String BASE_URL = TigerTextConfiguration.getApiServerUrl() + ":" + TigerTextConfiguration.getApiServerPort();
    public static final String USERS_API = BASE_URL + "/v2/user/";
    public static final String MESSAGE_API = BASE_URL + "/v1/message/";

    private Credentials credentials;

    public TigerTextSdkImpl(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public User getUser(String identifier) {
        String url = USERS_API + identifier;
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.addHeader("Authorization", credentials.getAuthorization());
        try {
            CloseableHttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case HttpStatus.SC_OK:
                    String body = EntityUtils.toString(response.getEntity());
                    JsonNode node = new ObjectMapper().readTree(body);
                    ObjectNode replyNode = (ObjectNode) node.get("reply");
                    return UserBuilder.fromJson(replyNode);
                default:
                    System.out.println("Unexpected status code: " + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String sendMessage(String body, String recipient) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(MESSAGE_API);
        request.addHeader("Authorization", credentials.getAuthorization());
        request.addHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
        Map<String, String> params = new HashMap<String, String>();
        params.put("body", body);
        params.put("recipient", recipient);
        try {
            String requestBody = new ObjectMapper().writeValueAsString(params);
            request.setEntity(new StringEntity(requestBody));
            CloseableHttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case HttpStatus.SC_NO_CONTENT:
                    return response.getFirstHeader("TT-X-Message-Id").getValue();
                default:
                    System.out.println("Unexpected status code: " + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
