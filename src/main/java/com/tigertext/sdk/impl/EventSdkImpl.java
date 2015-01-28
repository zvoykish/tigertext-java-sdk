package com.tigertext.sdk.impl;

import com.tigertext.sdk.EventSdk;
import com.tigertext.sdk.TigerTextSdk;
import com.tigertext.sdk.authorization.ApiCredentials;
import com.tigertext.sdk.authorization.Credentials;
import com.tigertext.sdk.events.EventHandler;
import com.tigertext.sdk.events.TigerTextEvent;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Zvika on 1/28/15.
 */
public class EventSdkImpl extends BaseSdk implements EventSdk {
    private static final String EVENTS_API = BASE_URL + "/v2/events/";

    private Client client;
    private EventSource eventSource;
    private TigerTextSdk sdk;

    public EventSdkImpl(Credentials credentials, TigerTextSdk sdk) {
        super(credentials);
        this.sdk = sdk;
    }

    @Override
    public void connect(final EventHandler handler) {
        if (eventSource != null) {
            throw new IllegalStateException("Event handler already registered, unregister first...");
        }

        if (!(credentials instanceof ApiCredentials)) {
            throw new IllegalArgumentException("Please use ApiCredentials for consuming events from API");
        }

        ApiCredentials c = (ApiCredentials) credentials;
        HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.basic(c.getKey(), c.getSecret());
        client = ClientBuilder.newBuilder().register(SseFeature.class).register(authenticationFeature).build();
        WebTarget target = client.target(EVENTS_API);
        eventSource = EventSource.target(target).build();
        EventListener listener = new EventListener() {
            @Override
            public void onEvent(InboundEvent inboundEvent) {
                String eventData = inboundEvent.readData();
                try {
                    JsonNode eventJson = new ObjectMapper().readTree(eventData);
                    String eventId = eventJson.get("event_id").asText();
                    JsonNode events = eventJson.get("event");
                    Iterator<Map.Entry<String, JsonNode>> it = events.getFields();
                    while (it.hasNext()) {
                        Map.Entry<String, JsonNode> currEvent = it.next();
                        TigerTextEvent event = new TigerTextEventImpl(eventId, currEvent.getKey(), currEvent.getValue());
                        handler.onEvent(event, sdk);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        eventSource.register(listener);
        eventSource.open();
    }

    @Override
    public void disconnect() {
        if (eventSource == null) {
            throw new IllegalStateException("Event handler not registered");
        }

        eventSource.close();
        eventSource = null;
        client.close();
        client = null;
        disconnectEventStream();
    }

    private boolean disconnectEventStream() {
        HttpDelete request = createDeleteRequest(EVENTS_API);
        try {
            CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case HttpStatus.SC_OK:
                    HttpEntity entity = response.getEntity();
                    String body = EntityUtils.toString(entity);
                    JsonNode node = new ObjectMapper().readTree(body);
                    int dropCount = node.get("reply").get("count").asInt();
                    log.info("Disconnected " + dropCount + " connection(s)");
                    return dropCount > 0;
                default:
                    log.error("Unexpected status code: " + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
