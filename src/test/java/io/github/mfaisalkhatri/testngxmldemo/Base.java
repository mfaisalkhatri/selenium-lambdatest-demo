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

public class Base {

    RemoteWebDriver driver;
    String          status = "failed";

    @BeforeTest
    @Parameters ({ "browser", "browserVersion", "platform" })
    public void setup (final String browser, final String browserVersion, final String platform) {
        final String userName = System.getenv ("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv ("LT_USERNAME");
        final String accessKey = System.getenv ("LT_ACCESS_KEY") == null
                                 ? "LT_ACCESS_KEY"
                                 : System.getenv ("LT_ACCESS_KEY");
        final String gridUrl = "@hub.lambdatest.com/wd/hub";

        if (browser.equalsIgnoreCase ("chrome")) {
            try {
                this.driver = new RemoteWebDriver (new URL ("https://" + userName + ":" + accessKey + gridUrl),
                    getChromeOptions (browser, browserVersion, platform));

            } catch (final MalformedURLException e) {
                System.out.println ("Could not start the chrome browser on LambdaTest cloud grid");
            }
        } else if (browser.equalsIgnoreCase ("firefox")) {
            try {
                this.driver = new RemoteWebDriver (new URL ("https://" + userName + ":" + accessKey + gridUrl),
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
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");
        return ltOptions;
    }
}