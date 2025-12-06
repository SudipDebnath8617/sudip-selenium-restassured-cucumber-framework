package com.sudip.framework.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    // One WebDriver instance per thread (for parallel runs)
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * Initialize WebDriver for the given browser and store it in ThreadLocal.
     *
     * @param browser browser name (chrome / firefox / edge)
     * @return initialized WebDriver
     */
    public WebDriver initDriver(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }

        switch (browser.toLowerCase().trim()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                // Example: read headless from system property if needed
                // if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                //     chromeOptions.addArguments("--headless=new");
                // }
                tlDriver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                tlDriver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                tlDriver.set(new EdgeDriver(edgeOptions));
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        getDriver().manage().window().maximize();
        return getDriver();
    }

    /**
     * Get the WebDriver instance associated with current thread.
     */
    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    /**
     * Quit and clean up WebDriver for current thread.
     */
    public static void quitDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            driver.quit();
            tlDriver.remove();
        }
    }
}
