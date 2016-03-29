package com.tigertext.sdk.impl;

import com.tigertext.sdk.UserSdk;
import com.tigertext.sdk.authorization.Credentials;
import com.tigertext.sdk.entities.User;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;

/**
 * Created by Zvika on 1/28/15.
 */
class UserSdkImpl extends BaseSdk implements UserSdk {
    private static final String USERS_API = BASE_URL + "/v2/user/";

    UserSdkImpl(Credentials credentials) {
        super(credentials);
    }

    @Override
    public User get(String identifier) {
        HttpGet request = createGetRequest(USERS_API + identifier);
        try {
            CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case HttpStatus.SC_OK:
                    String body = EntityUtils.toString(response.getEntity());
                    JsonNode node = new ObjectMapper().readTree(body);
                    ObjectNode replyNode = (ObjectNode) node.get("reply");
                    return UserBuilder.fromJson(replyNode);
                default:
                    log.error("Unexpected status code: " + statusCode + ". Response: " + response);
            }
        } catch (IOException e) {
            log.error("Failed reading user with identifier: " + identifier);
        }

        return null;
    }
}
