package com.tigertext.sdk.impl;

import com.tigertext.sdk.TigerTextSdk;
import com.tigertext.sdk.authorization.Credentials;
import com.tigertext.sdk.entities.User;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;

/**
 * Created by Zvika on 1/27/15.
 */
public class TigerTextSdkImpl implements TigerTextSdk {
    public static final int PORT = 8888;
    public static final String TIGERTEXT_API = "http://127.0.0.1:" + String.valueOf(PORT);
    public static final String USERS_API = TIGERTEXT_API + "/v2/user/";

    private Credentials credentials;

    public TigerTextSdkImpl(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public User getUser(String identifier) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(USERS_API + identifier);
        request.addHeader("Authorization", credentials.getAuthorization());
        try {
            CloseableHttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case HttpStatus.SC_OK:
                    String body = EntityUtils.toString(response.getEntity());
                    System.out.println("Body: " + body);
                    JsonNode node = new ObjectMapper().readTree(body);
                    ObjectNode replyNode = (ObjectNode) node.get("reply");
                    return UserBuilder.fromJson(replyNode);
                default:
                    System.out.println("Received status code: " + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
