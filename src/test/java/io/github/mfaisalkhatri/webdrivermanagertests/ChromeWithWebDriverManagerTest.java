package io.github.mfaisalkhatri.webdrivermanagertests;

import static org.testng.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ChromeWithWebDriverManagerTest {

    private WebDriver driver;

    @Test
    public void checkTheUrl () {

        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final String url = this.driver.getCurrentUrl ();
        assertTrue (url.contains ("lambdatest"));
    }

    @BeforeTest
    public void setup () {
        WebDriverManager.chromedriver ()
            .setup ();
        this.driver = new ChromeDriver ();
    }

    @AfterTest
    public void tearDown () {
        this.driver.quit ();
    }
}
