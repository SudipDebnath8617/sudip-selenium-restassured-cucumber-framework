package com.sudip.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FrameworkConfig {

    private static final Properties props = new Properties();

    static {
        // This will load framework-config.properties from src/main/resources
        try (InputStream is = FrameworkConfig.class
                .getClassLoader()
                .getResourceAsStream("framework-config.properties")) {

            if (is == null) {
                throw new RuntimeException("framework-config.properties not found in classpath");
            }
            props.load(is);
            System.out.println("Loaded framework-config.properties");
        } catch (IOException e) {
            throw new RuntimeException("Unable to load framework-config.properties", e);
        }
    }

    public static String get(String key) {
        String sysVal = System.getProperty(key); // allow override via -Dkey=value
        if (sysVal != null) {
            return sysVal.trim();
        }
        String val = props.getProperty(key);
        if (val == null) {
            throw new RuntimeException("Framework property not found: " + key);
        }
        return val.trim();
    }

    public static String get(String key, String defaultValue) {
        try {
            return get(key);
        } catch (RuntimeException e) {
            return defaultValue;
        }
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
