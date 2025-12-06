package com.sudip.framework.core;

import com.sudip.framework.wait.WaitUtils;
import org.openqa.selenium.*;

public abstract class BasePage {
    protected WebDriver driver;
    protected WaitUtils waitUtils;
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    // =========================
    //  ACTIONS USING LOCATOR
    // =========================


    protected void get(String url) {
        driver.get(url);
    }

    protected void click(By locator) {
        waitUtils.elementToBeClickable(locator).click();
    }

    protected void sendKeys(By locator, String text) {
        WebElement element = waitUtils.visibilityOf(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitUtils.visibilityOf(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitUtils.visibilityOf(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected String getAttribute(By locator, String attr) {
        return waitUtils.visibilityOf(locator).getAttribute(attr);
    }


    // =========================
    //  ACTIONS USING WEBELEMENT
    // =========================
    protected void click(WebElement element) {
        waitUtils.elementToBeClickable(element).click();
    }

    protected void sendKeys(WebElement element, String text) {
        waitUtils.visibilityOf(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        return waitUtils.visibilityOf(element).getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return waitUtils.visibilityOf(element).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected String getAttribute(WebElement element, String attr) {
        return waitUtils.visibilityOf(element).getAttribute(attr);
    }
}
