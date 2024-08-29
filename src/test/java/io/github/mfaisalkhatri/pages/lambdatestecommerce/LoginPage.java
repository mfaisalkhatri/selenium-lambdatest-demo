package io.github.mfaisalkhatri.pages.lambdatestecommerce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.testng.Assert.assertEquals;


public class LoginPage {

    private final WebDriver driver;

    public LoginPage(final WebDriver driver) {
        this.driver = driver;
    }


    private WebElement emailField () {
       return this.driver.findElement(By.id("input-email"));
    }

    private WebElement passwordField() {
        return this.driver.findElement(By.id("input-password"));
    }

    private WebElement loginBtn() {
        return this.driver.findElement(By.cssSelector("input[type=\"submit\"]"));
    }


    public void performLogin(String email, String password, boolean isValidUser) {
        emailField().clear();
        emailField().sendKeys(email);
        passwordField().clear();
        passwordField().sendKeys(password);
        loginBtn().click();
        if(!isValidUser) {
            assertEquals(getErrorMessageText(), "Warning: No match for E-Mail Address and/or Password.");
        } else {
            MyAccountPage myAccountPage= new MyAccountPage(driver);
            assertEquals(myAccountPage.getPageTitle(), "My Account");
        }
    }

    public String getErrorMessageText() {
        return driver.findElement(By.cssSelector("#account-login div.alert")).getText();
    }

}
