package com.sudip.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.sudip.framework.config.FrameworkConfig;
import io.cucumber.java.Scenario;

/**
 * Helper around ExtentReports and generic reporting.
 */
public class ReportUtil{

    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void startScenario(String scenarioName) {
        ExtentTest extentTest = extent.createTest(scenarioName);
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void logInfo(String message) {
        System.out.println("[INFO] " + message);
        if (test.get() != null) {
            test.get().info(message);
        }
    }

    public static void logPass(String message) {
        System.out.println("[PASS] " + message);
        if (test.get() != null) {
            test.get().pass(message);
        }
    }

    public static void logFail(String message) {
        System.out.println("[FAIL] " + message);
        if (test.get() != null) {
            test.get().fail(message);
        }
    }

    public static void attachScreenshot(String path, String title) {
        if (test.get() != null && path != null) {
            try {
                test.get().addScreenCaptureFromPath(path, title);
            } catch (Exception e) {
                System.out.println("Failed to attach screenshot to Extent: " + e.getMessage());
            }
        }
    }

    public static String captureAndAttachScreenshot(Scenario scenario, String name) {
        ScreenshotUtil.ScreenshotResult result =
                ScreenshotUtil.captureWithBytes(name);

        boolean attachToScenario =
                FrameworkConfig.getBoolean("attachScreenshotToScenario", true);

        if (attachToScenario && scenario != null) {
            scenario.attach(result.getBytes(), "image/png", name);
        }

        attachScreenshot(result.getPath(), name);
        return result.getPath();
    }

    public static void flushReports() {
        extent.flush();
    }
}
