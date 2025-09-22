package io.github.mfaisalkhatri.seleniumgriddemo;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class LocalGridDemoTests extends BaseTest {

    @Test
    public void testSearchProduct () {

        getDriver ().get ("https://ecommerce-playground.lambdatest.io/");
        final WebElement searchBox = getDriver ().findElement (By.name ("search"));

        final String searchText = "iPhone";
        searchBox.sendKeys (searchText);
        final WebElement searchBtn = getDriver ().findElement (By.cssSelector ("button.type-text"));
        searchBtn.click ();

        final String pageHeader = getDriver ().findElement (By.tagName ("h1"))
            .getText ();

        assertEquals (pageHeader, "Search - " + searchText);
    }
}