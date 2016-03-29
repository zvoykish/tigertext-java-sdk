package com.tigertext.sdk.impl;

import com.tigertext.sdk.TigerTextConfiguration;
import com.tigertext.sdk.authorization.Credentials;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Zvika on 1/28/15.
 */
abstract class BaseSdk {
    static final String BASE_URL = TigerTextConfiguration.getApiServerUrl() + ":" + TigerTextConfiguration.getApiServerPort();

    final Logger log = Logger.getLogger(getClass());
    final Credentials credentials;

    BaseSdk(Credentials credentials) {
        this.credentials = credentials;
    }

    HttpGet createGetRequest(String url) {
        HttpGet request = new HttpGet(url);
        request.addHeader("Authorization", credentials.getAuthorization());
        return request;
    }

    HttpDelete createDeleteRequest(String url) {
        HttpDelete request = new HttpDelete(url);
        request.addHeader("Authorization", credentials.getAuthorization());
        return request;
    }

    HttpPost createPostRequest(String url, Map<String, String> body) throws IOException {
        HttpPost request = new HttpPost(url);
        request.addHeader("Authorization", credentials.getAuthorization());
        request.addHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
        String requestBody = new ObjectMapper().writeValueAsString(body);
        request.setEntity(new StringEntity(requestBody));
        return request;
    }
}
