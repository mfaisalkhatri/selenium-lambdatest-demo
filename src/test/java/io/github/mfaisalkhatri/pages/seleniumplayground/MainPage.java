package io.github.mfaisalkhatri.pages.seleniumplayground;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private final WebDriver driver;

    public MainPage(final WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnLink(final String linkName) {
        this.driver.findElement(By.linkText(linkName)).click();
    }
}
