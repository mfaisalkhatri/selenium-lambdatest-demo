package io.github.mfaisalkhatri.webdrivermanagertests;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ChromeRemoteWithWebDriverManagerTest {

    private static final String GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String LT_USERNAME   = System.getenv ("LT_USERNAME");

    private WebDriver driver;

    @Test
    public void checkTheUrl () {
        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final String url = this.driver.getCurrentUrl ();
        assertTrue (url.contains ("lambdatest"));
    }

    @BeforeTest
    public void setup () {
        final ChromeOptions browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("122.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "WebDriverManager Demo");
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");
        ltOptions.put ("build", "Demonstration: WebDriverManager on LambdaTest");
        browserOptions.setCapability ("LT:Options", ltOptions);

        final WebDriverManager webDriverManager = WebDriverManager.chromedriver ()
            .capabilities (browserOptions);
        webDriverManager.setup ();
        this.driver = webDriverManager.remoteAddress ("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + GRID_URL)
            .create ();
    }

    @AfterTest
    public void teardown () {
        this.driver.quit ();
    }
}