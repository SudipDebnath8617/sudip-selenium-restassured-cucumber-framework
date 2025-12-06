package com.sudip.framework.reporting;

import com.sudip.framework.core.DriverFactory;
import com.sudip.framework.config.FrameworkConfig;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility for capturing screenshots as files and bytes.
 */
public class ScreenshotUtil {
    private static final DateTimeFormatter RUN_FOLDER_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter FILE_NAME_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private static final String RUN_FOLDER;

    static {
        String baseFolder = FrameworkConfig.get("screenshotFolder", "reports/screenshots/");
        String runTimestamp = LocalDateTime.now().format(RUN_FOLDER_FORMAT);
        RUN_FOLDER = baseFolder + "/run_" + runTimestamp;

        try {
            Files.createDirectories(Paths.get(RUN_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create screenshot folder: " + RUN_FOLDER, e);
        }
    }

    public static String captureScreenshot(String name) {
        try {
            byte[] screenshot = takeScreenshotAsBytes();

            String safeName = name.replaceAll("[^a-zA-Z0-9]", "_");
            String timestamp = LocalDateTime.now().format(FILE_NAME_FORMAT);
            String fileName = safeName + "_" + timestamp + ".png";

            Path filePath = Paths.get(RUN_FOLDER, fileName);
            Files.write(filePath, screenshot);

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot for: " + name, e);
        }
    }

    public static byte[] takeScreenshotAsBytes() {
        return ((TakesScreenshot) DriverFactory.getDriver())
                .getScreenshotAs(OutputType.BYTES);
    }

    public static ScreenshotResult captureWithBytes(String name) {
        byte[] bytes = takeScreenshotAsBytes();
        String path = captureScreenshot(name);
        return new ScreenshotResult(path, bytes);
    }

    public static class ScreenshotResult {
        private final String path;
        private final byte[] bytes;

        public ScreenshotResult(String path, byte[] bytes) {
            this.path = path;
            this.bytes = bytes;
        }

        public String getPath() {
            return path;
        }

        public byte[] getBytes() {
            return bytes;
        }
    }
}
