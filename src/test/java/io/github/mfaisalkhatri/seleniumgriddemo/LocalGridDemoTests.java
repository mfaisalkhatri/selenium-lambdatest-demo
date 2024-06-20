package io.github.mfaisalkhatri.seleniumgriddemo;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class LocalGridDemoTests extends BaseTest {

    @Test
    public void testSearchPage() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        WebElement searchBox = driver.findElement(By.name("search"));

        String searchText = "iPhone";
        searchBox.sendKeys(searchText);
        WebElement searchBtn = driver.findElement(By.cssSelector("button.type-text"));
        searchBtn.click();

        String pageHeader = driver.findElement(By.tagName("h1"))
            .getText();

        assertEquals(pageHeader, "Search - " + searchText);
    }
}