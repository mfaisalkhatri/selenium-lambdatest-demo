package io.github.mfaisalkhatri.pages.lambdatestecommerce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement searchBox() {
        return this.driver.findElement(By.name("search"));
    }

    private WebElement productName() {
        return this.driver.findElement(By.cssSelector(".product-layout h4 a"));
    }

    private WebElement searchBtn() {
        return this.driver.findElement(By.cssSelector("button.type-text"));
    }

    public ProductPage searchProduct(String searchProductName) {
        searchBox().sendKeys(searchProductName);
        searchBtn().click();
        productName().click();
        return new ProductPage(this.driver);
    }

    public String cartItemNumber() {
        return this.driver.findElement(By.cssSelector(".d-none .widget-cart div.cart-icon > span")).getText();
    }
}