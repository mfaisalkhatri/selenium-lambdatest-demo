package io.github.mfaisalkhatri.pageobjectmodeldemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationSuccessPage {

    private final WebDriver driver;
    private final By        successMessage = By.cssSelector ("#content h1");

    public RegistrationSuccessPage (final WebDriver driver) {
        this.driver = driver;
    }

    public String getSuccessMessage () {
        return this.driver.findElement (this.successMessage)
            .getText ();
    }
}