package io.github.mfaisalkhatri.testngxmldemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class EcommercePlaygroundTests {
    private WebDriver driver;


    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }


    @Test
    public void testCheckTitle() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        final String title = driver.getTitle();
        assertEquals(title, "Your Store");
    }

    @Test
    public void testSearchForProduct() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("iphone");

        WebElement searchBtn = driver.findElement(By.cssSelector("button.type-text"));
        searchBtn.click();

        String pageTitle = driver.findElement(By.cssSelector("#product-search h1")).getText();
        assertEquals(pageTitle, "Search - iphone");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
