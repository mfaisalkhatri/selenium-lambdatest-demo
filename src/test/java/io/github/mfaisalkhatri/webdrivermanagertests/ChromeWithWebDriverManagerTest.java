package io.github.mfaisalkhatri.webdrivermanagertests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ChromeWithWebDriverManagerTest {
    WebDriver driver;
    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
    @Test
    public void checkTheUrl() {

        driver.get("https://ecommerce-playground.lambdatest.io/");
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("lambdatest"));
    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
