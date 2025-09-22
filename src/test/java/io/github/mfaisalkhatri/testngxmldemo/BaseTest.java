package io.github.mfaisalkhatri.testngxmldemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest {
    private static final String          GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String          LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String          LT_USERNAME   = System.getenv ("LT_USERNAME");
    protected            RemoteWebDriver driver;
    protected            String          status        = "failed";

    @BeforeTest
    @Parameters ({ "browser", "browserVersion", "platform" })
    public void setup (final String browser, final String browserVersion, final String platform) {
        if (browser.equalsIgnoreCase ("chrome")) {
            try {
                this.driver = new RemoteWebDriver (new URL ("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + GRID_URL),
                    getChromeOptions (browser, browserVersion, platform));

            } catch (final MalformedURLException e) {
                System.out.println ("Could not start the chrome browser on LambdaTest cloud grid");
            }
        } else if (browser.equalsIgnoreCase ("firefox")) {
            try {
                this.driver = new RemoteWebDriver (new URL ("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + GRID_URL),
                    getFirefoxOptions (browser, browserVersion, platform));

            } catch (final MalformedURLException e) {
                System.out.println ("Could not start the firefox browser on LambdaTest cloud grid");
            }
        }

        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (20));
    }

    @AfterTest
    public void tearDown () {
        this.driver.executeScript ("lambda-status=" + this.status);
        this.driver.quit ();
    }

    private ChromeOptions getChromeOptions (final String browser, final String browserVersion, final String platform) {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName (platform);
        browserOptions.setBrowserVersion (browserVersion);
        browserOptions.setCapability ("LT:Options", getLtOptions ());

        return browserOptions;
    }

    private FirefoxOptions getFirefoxOptions (final String browser, final String browserVersion,
        final String platform) {
        final var browserOptions = new FirefoxOptions ();
        browserOptions.setPlatformName (platform);
        browserOptions.setBrowserVersion (browserVersion);
        browserOptions.setCapability ("LT:Options", getLtOptions ());

        return browserOptions;
    }

    private HashMap<String, Object> getLtOptions () {
        final var ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Selenium ECommerce playground website");
        ltOptions.put ("build", "LambdaTest Ecommerce Website tests");
        ltOptions.put ("name", "Search for a product test");
        ltOptions.put ("w3c", true);
        ltOptions.put ("visual", true);
        ltOptions.put ("plugin", "java-testNG");
        return ltOptions;
    }
}