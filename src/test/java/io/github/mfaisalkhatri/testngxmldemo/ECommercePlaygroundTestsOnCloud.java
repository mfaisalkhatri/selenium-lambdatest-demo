package io.github.mfaisalkhatri.testngxmldemo;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class ECommercePlaygroundTestsOnCloud extends Base {

    @Test
    public void testSearchForProduct () {
        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final WebElement searchBox = this.driver.findElement (By.name ("search"));
        searchBox.sendKeys ("iphone");

        final WebElement searchBtn = this.driver.findElement (By.cssSelector ("button.type-text"));
        searchBtn.click ();

        final String pageTitle = this.driver.findElement (By.cssSelector ("#product-search h1"))
            .getText ();
        assertEquals (pageTitle, "Search - iphone");

        this.status = "passed";
    }
}