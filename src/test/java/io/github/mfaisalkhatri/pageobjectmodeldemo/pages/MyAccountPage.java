package io.github.mfaisalkhatri.pageobjectmodeldemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyAccountPage extends BasePage {

    private final By pageTitle = By.cssSelector ("#content h2");

    public MyAccountPage (final WebDriver driver) {
        super (driver);
    }

    public String getPageTitle () {
        return this.driver.findElement (this.pageTitle)
            .getText ();
    }
}