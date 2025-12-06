package com.sudip.framework.wait;
import com.sudip.framework.config.FrameworkConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class WaitUtils {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 10;
    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        int timeout = FrameworkConfig.getInt("timeout", DEFAULT_TIMEOUT);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    // ---- Locator-based waits ----
    public WebElement visibilityOf(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement elementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ---- WebElement-based waits ----
    public WebElement visibilityOf(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement elementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
