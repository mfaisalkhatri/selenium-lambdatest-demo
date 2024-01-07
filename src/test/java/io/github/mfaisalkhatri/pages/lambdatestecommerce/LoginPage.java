package io.github.mfaisalkhatri.pages.lambdatestecommerce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LoginPage {

    private final WebDriver driver;

    public LoginPage(final WebDriver driver) {
        this.driver = driver;
    }


    private WebElement emailField () {
       return this.driver.findElement(By.id("input-email"));
    }

    private WebElement passwordField() {
        return this.driver.findElement(By.id("input-password"));
    }

    private WebElement loginBtn() {
        return this.driver.findElement(By.cssSelector("input[type=\"submit\"]"));
    }



    /*
    email - david.thomson@gmail.com
    password - Secret@123

     */
}
