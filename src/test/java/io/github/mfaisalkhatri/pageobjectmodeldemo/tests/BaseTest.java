package io.github.mfaisalkhatri.pageobjectmodeldemo.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    private static final String          GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String          LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String          LT_USERNAME   = System.getenv ("LT_USERNAME");
    //    protected WebDriver driver;
    protected            RemoteWebDriver driver;

    @BeforeClass
    public void setup () {
        //                driver = new ChromeDriver ();
        try {
            this.driver = new RemoteWebDriver (new URL ("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + GRID_URL),
                getChromeOptions ());
        } catch (final MalformedURLException e) {
            System.out.println ("Could not start the remote session on LambdaTest cloud grid");
        }
        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (10));
    }

    @AfterClass
    public void tearDown () {
        this.driver.quit ();
    }

    private ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("latest");
        final HashMap<String, Object> ltOptions = new HashMap<> ();
        ltOptions.put ("project", "POM demo With Selenium");
        ltOptions.put ("build", "LambdaTest ECommerce Playground");
        ltOptions.put ("name", "Registration and Login Tests");
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");
        browserOptions.setCapability ("LT:Options", ltOptions);
        return browserOptions;
    }
}