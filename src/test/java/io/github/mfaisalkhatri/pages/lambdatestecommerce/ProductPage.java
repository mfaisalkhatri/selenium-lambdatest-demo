package io.github.mfaisalkhatri.pages.lambdatestecommerce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private WebDriver driver;
    WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private WebElement addToCartBtn() {
        return this.driver.findElement(By.cssSelector(".order-3 .btn-secondary"));
    }

    private WebElement notificationPopUp() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-box-top")));
    }

    public void adProductToCart() {
        addToCartBtn().click();
    }

    public String successMessageTest() {
        return notificationPopUp().findElement(By.tagName("p")).getText();
    }
}