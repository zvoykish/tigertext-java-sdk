package com.tigertext.sdk;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Zvika on 1/27/15.
 */
public class TigerTextConfiguration {
    private static final Logger log = Logger.getLogger(TigerTextConfiguration.class);
    private static Properties properties;

    public static String getApiKey() {
        return get("tigertext.api.key");
    }

    public static String getApiSecret() {
        return get("tigertext.api.secret");
    }

    public static String getApiServerUrl() {
        return get("tigertext.api.server.url", "https://api.tigertext.me");
    }

    public static int getApiServerPort() {
        return getInt("tigertext.api.server.port", 443);
    }


    ////////////////////
    /// Internal methods
    ////////////////////

    private static int getInt(String key, int defaultValue) {
        String value = get(key);
        return isEmpty(value) ? defaultValue : Integer.parseInt(value);
    }

    private static String get(String key, String defaultValue) {
        String value = get(key);
        return isEmpty(value) ? defaultValue : value;
    }

    private static String get(String key) {
        if (ensurePropertiesLoaded()) {
            return properties.getProperty(key);
        } else {
            throw new RuntimeException("Failed loading TigerText SDK configuration");
        }
    }

    private static boolean ensurePropertiesLoaded() {
        if (properties == null) {
            synchronized (TigerTextConfiguration.class) {
                if (properties == null) {
                    properties = new Properties();
                    try {
                        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("tigertext.properties"));
                        return true;
                    } catch (IOException e) {
                        properties = null;
                        log.error("Failed reading TigerText configuration", e);
                    }
                }
            }
        }

        return properties.size() > 0;
    }

    private static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
