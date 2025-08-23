package io.github.mfaisalkhatri.pageobjectmodeldemo;

import io.github.mfaisalkhatri.pages.lambdatestecommerce.RegistrationSuccessPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class RegistrationPage extends BasePage {
    private final By        firstNameField  = By.id ("input-firstname");
    private       By        lastNameField   = By.id ("input-lastname");
    private       By        emailField      = By.id ("input-email");
    private       By        telephoneField  = By.id ("input-telephone");
    private       By        passwordField   = By.id ("input-password");
    private       By        confirmPassword = By.id ("input-confirm");
    private       By        agreePolicy     = By.id ("input-agree");
    private       By        continueBtn     = By.cssSelector ("input.btn-primary");

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
        return new RegistrationSuccessPage (driver);
    }

}
