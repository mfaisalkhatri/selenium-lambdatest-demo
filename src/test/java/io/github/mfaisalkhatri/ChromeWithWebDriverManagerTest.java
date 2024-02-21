package io.github.mfaisalkhatri;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ChromeWithWebDriverManagerTest {
    WebDriver driver;
    @BeforeTest
    public void setup() {
        // Set up the wWebDriverManager for chrome driver
        WebDriverManager.chromedriver().setup();
        // Create the driver object
        driver = new ChromeDriver();
    }
    @Test
    void checkTheUrl() {
        // Open the browser and go to lambdatest ecommerce website
        driver.get("https://ecommerce-playground.lambdatest.io/");
        // Set the current url as a string
        String url = driver.getCurrentUrl();
        // Verify that current url contains lambdatest
        assertTrue(url.contains("lambdatest"));
    }
    @AfterTest
    void tearDown() {
        // Close the driver
        driver.quit();
    }
}
