package io.github.mfaisalkhatri.testngxmldemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


import static org.testng.Assert.assertEquals;

public class ECommercePlaygroundTestsOnCloud extends Base{

    @Test
    public void testSearchForProduct() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("iphone");

        WebElement searchBtn = driver.findElement(By.cssSelector("button.type-text"));
        searchBtn.click();

        String pageTitle = driver.findElement(By.cssSelector("#product-search h1")).getText();
        assertEquals(pageTitle, "Search - iphone");

        status = "passed";
    }
}