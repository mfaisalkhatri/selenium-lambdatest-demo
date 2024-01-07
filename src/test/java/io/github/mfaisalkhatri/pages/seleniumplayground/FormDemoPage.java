package io.github.mfaisalkhatri.pages.seleniumplayground;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FormDemoPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public FormDemoPage(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private WebElement nameField() {
        return this.driver.findElement(By.id("name"));
    }

    private WebElement emailField() {
        return this.driver.findElement(By.id("inputEmail4"));
    }

    private WebElement passwordField() {
        return this.driver.findElement(By.id("inputPassword4"));
    }

    private WebElement companyField() {
        return this.driver.findElement(By.id("company"));
    }

    private WebElement websiteField() {
        return this.driver.findElement(By.id("websitename"));
    }

    private WebElement country() {
        return this.driver.findElement(By.name("country"));
    }

    private void countryName(final String countryName) {
        country().click();
        new Select(country()).selectByVisibleText(countryName);
    }

    private WebElement cityField() {
        return this.driver.findElement(By.id("inputCity"));
    }

    private WebElement addressLineOneField() {
        return this.driver.findElement(By.name("address_line1"));
    }

    private WebElement addressLineTwoField() {
        return this.driver.findElement(By.name("address_line2"));
    }

    private WebElement stateField() {
        return this.driver.findElement(By.id("inputState"));
    }

    private WebElement zipCodeField() {
        return this.driver.findElement(By.id("inputZip"));
    }

    private WebElement submitBtn() {
        return this.driver.findElement(By.cssSelector("#seleniumform button[type=\"submit\"]"));
    }

    public String successMessage() {
        return this.wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.success-msg"))).getText();
    }

    public void fillForm(final String name, final String email, final String password, final String company,
                         final String website, final String country, final String city, final String addressLineOne,
                         final String addressLineTwo, final String state, final String zipCode)  {

        this.nameField().sendKeys(name);
        this.emailField().sendKeys(email);
        this.passwordField().sendKeys(password);
        this.companyField().sendKeys(company);
        this.websiteField().sendKeys(website);
        this.countryName(country);
        this.cityField().sendKeys(city);
        this.addressLineOneField().sendKeys(addressLineOne);
        this.addressLineTwoField().sendKeys(addressLineTwo);
        this.stateField().sendKeys(state);
        this.zipCodeField().sendKeys(zipCode);
        this.submitBtn().click();
    }
}
