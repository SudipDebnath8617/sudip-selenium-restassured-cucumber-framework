Feature: SauceDemo Website Automation


  #update
  @Regression
  Scenario: End-to-end shopping flow on SauceDemo website
    Given I navigate to "https://www.saucedemo.com/"
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be logged in successfully
