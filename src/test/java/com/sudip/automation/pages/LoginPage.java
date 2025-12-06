package com.sudip.automation.pages;

import com.sudip.framework.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    By usernameTextBox=By.xpath("//*[@placeholder='Username']");
    By passwordTextBox=By.xpath("//*[@placeholder='Password']");
    By loginButton=By.xpath("//*[@type='submit']");


    public void launchTheAppUrl(String url)
    {
        get(url);
    }
    public void loginToTheApplication(String username, String password)
    {
          sendKeys(usernameTextBox,username);
          sendKeys(passwordTextBox,password);
          click(loginButton);
    }






}
