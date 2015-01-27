package com.tigertext.sdk;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Zvika on 1/27/15.
 */
public class TigerTextConfiguration {
    private static Properties properties;

    public static String getApiKey() {
        return get("tigertext.api.key");
    }

    public static String getApiSecret() {
        return get("tigertext.api.secret");
    }



    ////////////////////
    /// Internal methods
    ////////////////////

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
                        e.printStackTrace();
                    }
                }
            }
        }

        return properties.size() > 0;
    }
}
