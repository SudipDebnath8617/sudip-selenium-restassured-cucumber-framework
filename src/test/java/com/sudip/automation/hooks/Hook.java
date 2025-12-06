package com.sudip.automation.hooks;

import com.sudip.framework.config.ConfigReader;
import com.sudip.framework.config.FrameworkConfig;
import com.sudip.framework.core.DriverFactory;
import com.sudip.framework.logging.LogUtil;
import com.sudip.framework.reporting.EvidenceCollector;
import com.sudip.framework.reporting.ReportUtil;
import com.sudip.framework.reporting.VideoRecordingUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class Hook {

    private WebDriver driver;
    private final DriverFactory driverFactory = new DriverFactory();

    @Before
    public void setUp(Scenario scenario) {
        LogUtil.setClass(Hook.class); // initialize logger for this class
        LogUtil.info("--------------- TEST START ---------------");

        // 1) Load test-side config (src/test/resources/config/*.properties)
        ConfigReader.loadConfig();
        LogUtil.info("Loaded test config");

        // 2) Initialize browser from test config
        String browserName = ConfigReader.get("browser", "chrome");
        LogUtil.info("Launching browser: " + browserName);
        driver = driverFactory.initDriver(browserName);

        // 3) Navigate to base URL from test config
        String baseUrl = ConfigReader.get("baseUrl", "https://www.saucedemo.com/");
        LogUtil.info("Navigating to URL: " + baseUrl);
        //driver.get(baseUrl);

        // 4) Start Extent test
        LogUtil.info("Starting scenario in report: " + scenario.getName());
        ReportUtil.startScenario(scenario.getName());

        // 5) Clear evidence
        EvidenceCollector.clear();

        // 6) Configure + start video recording
        boolean videoEnabled = FrameworkConfig.getBoolean("videoRecording", false);
        LogUtil.info("Video recording enabled? " + videoEnabled);
        VideoRecordingUtil.setRecordingEnabled(videoEnabled);
        VideoRecordingUtil.startRecording(scenario.getName());

        LogUtil.info("------------- SETUP COMPLETE -------------");
    }

    @After
    public void tearDown(Scenario scenario) {
        LogUtil.setClass(Hook.class); // ensure logger prints under correct test during parallel execution
        LogUtil.info("--------------- TEST END ----------------");

        try {
            if (scenario.isFailed()) {
                LogUtil.error("Scenario failed: " + scenario.getName());
                ReportUtil.logFail("Scenario failed: " + scenario.getName());

                // Capture screenshot & attach
                String path = ReportUtil.captureAndAttachScreenshot(
                        scenario, "Failure - " + scenario.getName()
                );
                LogUtil.error("Screenshot captured at: " + path);
                EvidenceCollector.addScreenshotPath(path);

            } else {
                LogUtil.pass("Scenario passed: " + scenario.getName());
                ReportUtil.logPass("Scenario passed: " + scenario.getName());
            }

            // Stop video recording
            LogUtil.info("Stopping video recording...");
            VideoRecordingUtil.stopRecording(scenario.getName());

            // Print evidence list in logs
            EvidenceCollector.getScreenshotPaths()
                    .forEach(p -> LogUtil.info("Evidence screenshot saved: " + p));

        } catch (Exception e) {
            LogUtil.error("Error during teardown", e);

        } finally {
            // Clear evidence after scenario
            EvidenceCollector.clear();

            // Quit driver
            LogUtil.info("Closing driver");
            DriverFactory.quitDriver();

            // Flush Extent report
            LogUtil.info("Flushing Extent report");
            ReportUtil.flushReports();

            LogUtil.info("--------------- FRAMEWORK DONE ---------------");
        }
    }
}
