package io.github.mfaisalkhatri.stalereferenceexception;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static RemoteWebDriver driver;

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("126.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Stale Element Reference Exception Blog");
        ltOptions.put ("build", "Selenium Playground - Table Data search");
        ltOptions.put ("name", "Demo Stale Element Reference Exception");
        ltOptions.put ("visual", true);
        ltOptions.put ("network", true);
        ltOptions.put ("console", true);
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");

        browserOptions.setCapability ("LT:Options", ltOptions);

        return browserOptions;

    }

    @BeforeClass
    public void setup () {

        final String USERNAME = System.getenv ("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv ("LT_USERNAME");
        final String ACCESS_KEY = System.getenv ("LT_ACCESS_KEY") == null
                                  ? "LT_ACCESS_KEY"
                                  : System.getenv ("LT_ACCESS_KEY");
        final String GRID_URL = "@hub.lambdatest.com/wd/hub";

        try {
            driver = new RemoteWebDriver (new URL ("https://" + USERNAME + ":" + ACCESS_KEY + GRID_URL),
                getChromeOptions ());
        } catch (final MalformedURLException e) {
            System.out.println ("Could not start the remote session on LambdaTest cloud grid");
        }
        driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (20));
        driver.get ("https://www.lambdatest.com/selenium-playground/");
    }

    @AfterClass
    public void tearDown () {
        driver.quit ();
    }
}