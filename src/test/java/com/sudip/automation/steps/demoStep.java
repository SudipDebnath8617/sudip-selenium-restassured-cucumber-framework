package com.sudip.automation.steps;

import com.sudip.automation.hooks.Hook;
import com.sudip.automation.pages.LoginPage;
import com.sudip.framework.core.DriverFactory;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;
public class demoStep  {

    WebDriver driver = DriverFactory.getDriver();
    LoginPage login=new LoginPage(driver);
    @Given("I open the browser")
    public void i_open_the_browser() {
        // Browser opens in Hooks @Before

    }

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        login.launchTheAppUrl(url);
    }
    @Given("I login with username {string} and password {string}")
    public void login(String username,String password) {
        login.loginToTheApplication(username,password);
    }
    @Given("I should be logged in successfully")
    public void verifyLoggedInSuccessFully() {

    }




}
