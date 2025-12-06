package com.sudip.framework.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads application/test-side configuration from:
 *    src/test/resources/config/config-<env>.properties
 *
 * System properties (-Dkey=value) override file values.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();
    private static final String CONFIG_FOLDER = "src/test/resources/config/";
    private static boolean isLoaded = false;

    /**
     * Load config only once — safe to call multiple times.
     */
    public static void loadConfig() {
        if (isLoaded) return;
        String env = System.getProperty("env", "qa").toLowerCase().trim();
        String filePath = CONFIG_FOLDER + "config-" + env + ".properties";
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            isLoaded = true;
            System.out.println("🔹 Loaded config: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("❌ Unable to load config: " + filePath, e);
        }
    }

    /**
     * Get String property (system property override supported)
     */
    public static String get(String key) {
        String sysVal = System.getProperty(key);
        if (sysVal != null && !sysVal.isEmpty()) {
            return sysVal.trim();
        }

        String val = properties.getProperty(key);
        if (val == null) {
            throw new RuntimeException("❌ Property not found: " + key);
        }
        return val.trim();
    }

    /**
     * Get with default value (never throws error)
     */
    public static String get(String key, String defaultValue) {
        try {
            return get(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Get boolean value
     */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    /**
     * Get boolean with default
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Get integer value
     */
    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    /**
     * Get integer with default
     */
    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Get long with default
     */
    public static long getLong(String key, long defaultValue) {
        try {
            return Long.parseLong(get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
