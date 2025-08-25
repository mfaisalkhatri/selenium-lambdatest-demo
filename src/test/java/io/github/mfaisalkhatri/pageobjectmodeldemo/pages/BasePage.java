package io.github.mfaisalkhatri.pageobjectmodeldemo.pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;
    public BasePage (final WebDriver driver) {
        this.driver = driver;
    }
}