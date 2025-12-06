package com.sudip.framework.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Central logging utility for framework + test layers.
 *  - Safe for thread parallel execution
 *  - Wrapper over Log4j2
 *  - Simple static methods used everywhere
 */
public final class LogUtil {

    // ThreadLocal logger for parallel execution
    private static final ThreadLocal<Logger> logger =
            ThreadLocal.withInitial(() -> LogManager.getLogger(LogUtil.class));

    private LogUtil() {}

    public static void setClass(Class<?> clazz) {
        logger.set(LogManager.getLogger(clazz));
    }

    public static void info(String message) {
        logger.get().info(message);
        System.out.println("[INFO] " + message);
    }

    public static void debug(String message) {
        logger.get().debug(message);
        System.out.println("[DEBUG] " + message);
    }

    public static void warn(String message) {
        logger.get().warn(message);
        System.out.println("[WARN] " + message);
    }

    public static void error(String message) {
        logger.get().error(message);
        System.err.println("[ERROR] " + message);
    }

    public static void error(String message, Throwable t) {
        logger.get().error(message, t);
        System.err.println("[ERROR] " + message + " | " + t.getMessage());
    }

    public static void pass(String message) {
        logger.get().info("[PASS] " + message);
        System.out.println("[PASS] " + message);
    }

    public static void fail(String message) {
        logger.get().error("[FAIL] " + message);
        System.err.println("[FAIL] " + message);
    }
}
