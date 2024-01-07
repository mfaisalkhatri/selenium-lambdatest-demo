package io.github.mfaisalkhatri.pages.lambdatestecommerce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyAccountPage {

    private final WebDriver driver;

    public MyAccountPage(final WebDriver driver) {
        this.driver = driver;
    }


    public String getPageTitle() {
        return driver.findElement(By.cssSelector("#content h2")).getText();
    }
}
