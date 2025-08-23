package io.github.mfaisalkhatri.pages.lambdatestecommerce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class RegistrationPage {

    private WebDriver driver;

    public RegistrationPage(final WebDriver driver) {
        this.driver = driver;
    }

    private WebElement firstNameField() {
        return this.driver.findElement(By.id("input-firstname"));
    }

    private WebElement lastNameField() {
        return this.driver.findElement(By.id("input-lastname"));
    }

    private WebElement emailIdField() {
        return this.driver.findElement(By.id("input-email"));
    }

    private WebElement telephoneField() {
        return this.driver.findElement(By.id("input-telephone"));
    }

    private WebElement passwordField() {
        return this.driver.findElement(By.id("input-password"));
    }

    private WebElement confirmPasswordField() {
        return this.driver.findElement(By.id("input-confirm"));
    }

    private WebElement agreePolicy() {
        return this.driver.findElement(By.id("input-agree"));
    }

    private WebElement continueBtn() {
        return this.driver.findElement(By.cssSelector("input.btn-primary"));
    }

    public RegistrationSuccessPage registerUser(String firstName, String lastName, String emailId, String telephoneNumber, String password) {
        firstNameField().sendKeys(firstName);
        lastNameField().sendKeys(lastName);
        emailIdField().sendKeys(emailId);
        telephoneField().sendKeys(telephoneNumber);
        passwordField().sendKeys(password);
        confirmPasswordField().sendKeys(password);

        final Actions actions = new Actions(driver);
        actions.moveToElement(agreePolicy()).click().perform();

        continueBtn().click();
        return new RegistrationSuccessPage(this.driver);
    }
}