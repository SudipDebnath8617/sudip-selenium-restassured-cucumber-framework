package com.sudip.automation.runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",                // Path of feature files
        glue = {"com.sudip.automation.steps", "com.sudip.automation.hooks"},          // Step + Hooks packages
        plugin = {
                "pretty",                                        // Console readable output
                "html:target/cucumber-report.html",              // HTML Report
                "json:target/cucumber.json"                      // JSON Report (optional)
        },
        tags = "@Regression",
        monochrome = true,                                       // Removes unreadable chars from console
        dryRun = false                                           // true = checks mapping only, false = executes tests
)
public class TestRunner {
    // No code required here — Cucumber + JUnit handle execution
}
