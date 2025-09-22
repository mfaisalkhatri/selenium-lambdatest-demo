package io.github.mfaisalkhatri.screenshotdemo;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LambdaTestScreenshotTest {

    private RemoteWebDriver driver;

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("125");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "LambdaTest Screenshot Testing demo");
        ltOptions.put ("name", "Capture Screenshot test");
        ltOptions.put ("build", "LambdaTest Screenshot Testing demo");
        ltOptions.put ("w3c", true);
        ltOptions.put ("visual", true);
        ltOptions.put ("video", true);
        ltOptions.put ("plugin", "java-testNG");

        browserOptions.setCapability ("LT:Options", ltOptions);

        return browserOptions;
    }

    @BeforeTest
    public void setup () {
        final String userName = System.getenv ("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv ("LT_USERNAME");
        final String accessKey = System.getenv ("LT_ACCESS_KEY") == null
                                 ? "LT_ACCESS_KEY"
                                 : System.getenv ("LT_ACCESS_KEY");
        final String gridUrl = "@hub.lambdatest.com/wd/hub";
        try {
            this.driver = new RemoteWebDriver (new URL ("https://" + userName + ":" + accessKey + gridUrl),
                getChromeOptions ());
        } catch (final MalformedURLException e) {
            System.out.println ("Could not start the remote session on LambdaTest cloud grid");
        }
        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (10));
    }

    @AfterTest
    public void tearDown () {
        this.driver.quit ();
    }

    @Test
    public void testScreenshotCaptureOnLambdaTest () {

        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final WebElement myAccountLink = this.driver.findElement (By.linkText ("My account"));
        final Actions actions = new Actions (this.driver);
        actions.moveToElement (myAccountLink)
            .build ()
            .perform ();

        final WebElement registerLink = this.driver.findElement (By.linkText ("Register"));
        actions.moveToElement (registerLink)
            .click ()
            .build ()
            .perform ();
        final String registerPageHeader = this.driver.findElement (By.tagName ("h1"))
            .getText ();
        assertEquals (registerPageHeader, "Register Account");

        final WebElement loginLink = this.driver.findElement (By.linkText ("Login"));
        loginLink.click ();
        final String loginPageHeader = this.driver.findElement (By.cssSelector (".col-lg-6  h2"))
            .getText ();
        assertEquals (loginPageHeader, "New Customer");
    }
}