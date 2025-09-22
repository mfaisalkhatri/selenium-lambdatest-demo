package io.github.mfaisalkhatri.testngxmldemo;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class EcommercePlaygroundTests {
    private WebDriver driver;

    @BeforeTest
    public void setup () {
        this.driver = new ChromeDriver ();
        this.driver.manage ()
            .window ()
            .maximize ();
        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (20));
    }

    @AfterTest
    public void tearDown () {
        this.driver.quit ();
    }

    @Test
    public void testCheckTitle () {
        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final String title = this.driver.getTitle ();
        assertEquals (title, "Your Store");
    }

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
    }

}
