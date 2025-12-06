package com.sudip.framework.ui;

import com.sudip.framework.wait.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ElementUtils {

    private WebDriver driver;
    private WaitUtils waitUtils;
    private Actions actions;

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        this.actions = new Actions(driver);
    }

    // ------------------ SAFE CLICK (with retry) ------------------
    public void safeClick(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitUtils.elementToBeClickable(locator).click();
                return;
            } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
                attempts++;
            }
        }
        throw new RuntimeException("Failed to click element after 3 attempts: " + locator);
    }

    public void safeClick(WebElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitUtils.elementToBeClickable(element).click();
                return;
            } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
                attempts++;
            }
        }
        throw new RuntimeException("Failed to click WebElement after 3 attempts.");
    }

    // ------------------ JavaScript CLICK ------------------
    public void jsClick(By locator) {
        WebElement element = waitUtils.elementToBeClickable(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void jsClick(WebElement element) {
        waitUtils.elementToBeClickable(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // ------------------ SCROLLING ------------------
    public void scrollIntoView(By locator) {
        WebElement element = waitUtils.visibilityOf(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollIntoView(WebElement element) {
        waitUtils.visibilityOf(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // ------------------ HOVER / ACTIONS ------------------
    public void hover(By locator) {
        WebElement element = waitUtils.visibilityOf(locator);
        actions.moveToElement(element).perform();
    }

    public void hover(WebElement element) {
        waitUtils.visibilityOf(element);
        actions.moveToElement(element).perform();
    }

    public void doubleClick(By locator) {
        WebElement element = waitUtils.elementToBeClickable(locator);
        actions.doubleClick(element).perform();
    }

    public void doubleClick(WebElement element) {
        waitUtils.elementToBeClickable(element);
        actions.doubleClick(element).perform();
    }

    public void rightClick(By locator) {
        WebElement element = waitUtils.elementToBeClickable(locator);
        actions.contextClick(element).perform();
    }

    public void rightClick(WebElement element) {
        waitUtils.elementToBeClickable(element);
        actions.contextClick(element).perform();
    }

    // ------------------ DROPDOWNS ------------------
    public void selectByVisibleText(By locator, String text) {
        Select select = new Select(waitUtils.visibilityOf(locator));
        select.selectByVisibleText(text);
    }

    public void selectByVisibleText(WebElement element, String text) {
        Select select = new Select(waitUtils.visibilityOf(element));
        select.selectByVisibleText(text);
    }

    public void selectByValue(By locator, String value) {
        Select select = new Select(waitUtils.visibilityOf(locator));
        select.selectByValue(value);
    }

    public void selectByValue(WebElement element, String value) {
        Select select = new Select(waitUtils.visibilityOf(element));
        select.selectByValue(value);
    }

    public void selectByIndex(By locator, int index) {
        Select select = new Select(waitUtils.visibilityOf(locator));
        select.selectByIndex(index);
    }

    public void selectByIndex(WebElement element, int index) {
        Select select = new Select(waitUtils.visibilityOf(element));
        select.selectByIndex(index);
    }

    // ------------------ HIGHLIGHT ELEMENT (useful for debugging) ------------------
    public void highlight(By locator) {
        WebElement element = waitUtils.visibilityOf(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red';", element);
    }

    public void highlight(WebElement element) {
        waitUtils.visibilityOf(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red';", element);
    }
}
