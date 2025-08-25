package io.github.mfaisalkhatri.pageobjectmodeldemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class RegistrationPage extends BasePage {
    private final By firstNameField  = By.id ("input-firstname");
    private final By lastNameField   = By.id ("input-lastname");
    private final By emailField      = By.id ("input-email");
    private final By telephoneField  = By.id ("input-telephone");
    private final By passwordField   = By.id ("input-password");
    private final By confirmPassword = By.id ("input-confirm");
    private final By agreePolicy     = By.id ("input-agree");
    private final By continueBtn     = By.cssSelector ("input.btn-primary");

    public RegistrationPage (final WebDriver driver) {
        super (driver);
    }

    public RegistrationSuccessPage registerUser (final String firstName, final String lastName, final String emailId,
        final String telephoneNumber, final String password) {
        this.driver.findElement (this.firstNameField)
            .sendKeys (firstName);
        this.driver.findElement (this.lastNameField)
            .sendKeys (lastName);
        this.driver.findElement (this.emailField)
            .sendKeys (emailId);
        this.driver.findElement (this.telephoneField)
            .sendKeys (telephoneNumber);
        this.driver.findElement (this.passwordField)
            .sendKeys (password);
        this.driver.findElement (this.confirmPassword)
            .sendKeys (password);
        final WebElement agreePolicyCheckBox = this.driver.findElement (this.agreePolicy);
        final Actions actions = new Actions (this.driver);
        actions.moveToElement (agreePolicyCheckBox)
            .click ()
            .build ()
            .perform ();
        this.driver.findElement (this.continueBtn)
            .click ();
        return new RegistrationSuccessPage (this.driver);
    }
}