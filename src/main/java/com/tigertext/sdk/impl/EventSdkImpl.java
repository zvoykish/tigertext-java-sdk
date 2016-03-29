package com.tigertext.sdk.impl;

import com.tigertext.sdk.EventSdk;
import com.tigertext.sdk.TigerTextSdk;
import com.tigertext.sdk.authorization.ApiCredentials;
import com.tigertext.sdk.authorization.Credentials;
import com.tigertext.sdk.events.EventHandler;
import com.tigertext.sdk.events.TigerTextEvent;
import jersey.repackaged.com.google.common.collect.Lists;
import jersey.repackaged.com.google.common.collect.Maps;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Zvika on 1/28/15.
 */
class EventSdkImpl extends BaseSdk implements EventSdk {
    private static final String EVENTS_API = BASE_URL + "/v2/events/";

    private Client client;
    private TigerTextSdk sdk;
    private Set<Feature> features;
    private ExecutorService executor;

    EventSdkImpl(Credentials credentials, TigerTextSdk sdk) {
        super(credentials);
        this.sdk = sdk;
        this.features = new HashSet<>();
    }

    public void connect(final EventHandler handler) {
        executor = Executors.newSingleThreadExecutor();
        if (!(credentials instanceof ApiCredentials)) {
            throw new IllegalArgumentException("Please use ApiCredentials for consuming events from API");
        }

        ApiCredentials c = (ApiCredentials) credentials;
        HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.basic(c.getKey(), c.getSecret());
        client = ClientBuilder.newBuilder().register(SseFeature.class).register(authenticationFeature).build();
        WebTarget target = client.target(EVENTS_API);
        Invocation.Builder invocationBuilder = target.request();

        // Add features header if any feature was enabled
        applyFeatures(invocationBuilder);

        // Send the GET request and start reading async...
        final EventInput eventInput = invocationBuilder.get(EventInput.class);
        executor.execute(() -> {
            while (!eventInput.isClosed()) {
                InboundEvent inboundEvent = null;
                try {
                    inboundEvent = eventInput.read();
                } catch (IllegalStateException e) {
                    log.warn("SDK event read failed (probably due to shutdown): " + e.getMessage());
                } catch (Exception e) {
                    log.error("SDK event read failed: " + e.getMessage());
                }

                if (inboundEvent == null) {
                    // connection has been closed
                    break;
                }

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
                    log.error("Failed handling SSE event: " + eventData, e);
                }
            }
        });
    }

    public void disconnect() {
        client.close();
        client = null;
        disconnectEventStream();
        executor.shutdown();
        executor = null;
    }

    public void ack(String eventId) {
        ack(Lists.newArrayList(eventId));
    }

    public void ack(List<String> eventIds) {
        Map<String, String> body = Maps.newHashMap();
        body.put("events", commaSeperatedList(eventIds));
        try {
            HttpPost request = createPostRequest(EVENTS_API + "ack/", body);

            CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            switch (statusCode) {
                case HttpStatus.SC_NO_CONTENT:
                    // All good...
                    break;
                default:
                    log.error("Unexpected status code: " + statusCode + ". Response: " + response);
            }
        } catch (IOException e) {
            log.error("Failed ack'ing events: " + eventIds, e);
        }
    }

    public EventSdk enable(Feature feature) {
        features.add(feature);
        return this;
    }

    public EventSdk disable(Feature feature) {
        features.remove(feature);
        return this;
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
                    log.error("Unexpected status code: " + statusCode + ". Response: " + response);
            }
        } catch (IOException e) {
            log.error("Failed disconnecting SSE connection", e);
        }

        return false;
    }

    private void applyFeatures(Invocation.Builder invocationBuilder) {
        if (!features.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Feature feature : features) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(feature.getHeaderValue());
            }
            invocationBuilder.header("TT-X-Features", sb.toString());
        }
    }

    private String commaSeperatedList(Collection<String> eventIds) {
        StringBuilder sb = new StringBuilder();
        for (String id : eventIds) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(id);
        }
        return sb.toString();
    }
}
