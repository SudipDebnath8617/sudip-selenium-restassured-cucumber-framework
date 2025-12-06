package com.sudip.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sudip.framework.config.FrameworkConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Manages a single ExtentReports instance for the whole run.
 */
public class ExtentManager {
    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            try {
                String reportFolder = FrameworkConfig.get("reportFolder", "reports/");
                Files.createDirectories(Paths.get(reportFolder));

                String reportPath = reportFolder + "extent-report.html";
                ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

                extent = new ExtentReports();
                extent.attachReporter(spark);
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize ExtentReports", e);
            }
        }
        return extent;
    }
}
