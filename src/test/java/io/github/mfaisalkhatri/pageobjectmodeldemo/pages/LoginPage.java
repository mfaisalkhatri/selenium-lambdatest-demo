package io.github.mfaisalkhatri.pageobjectmodeldemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By emailField    = By.id ("input-email");
    private final By passwordField = By.id ("input-password");
    private final By loginButton   = By.cssSelector ("input[type=\"submit\"]");

    public LoginPage (final WebDriver driver) {
        super (driver);
    }

    public MyAccountPage performLogin (final String email, final String password) {
        this.driver.findElement (this.emailField)
            .clear ();
        this.driver.findElement (this.emailField)
            .sendKeys (email);
        this.driver.findElement (this.passwordField)
            .clear ();
        this.driver.findElement (this.passwordField)
            .sendKeys (password);
        this.driver.findElement (this.loginButton)
            .click ();
        return new MyAccountPage (this.driver);
    }
}