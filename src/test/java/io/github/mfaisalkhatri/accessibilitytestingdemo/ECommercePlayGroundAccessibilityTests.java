package io.github.mfaisalkhatri.accessibilitytestingdemo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

public class ECommercePlayGroundAccessibilityTests {

    private RemoteWebDriver driver;

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("127.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Automated Accessibility Testing With Selenium");
        ltOptions.put ("build", "LambdaTest Selenium Playground");
        ltOptions.put ("name", "Accessibility test");
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");
        ltOptions.put ("accessibility", true);
        ltOptions.put ("accessibility.wcagVersion", "wcag21");
        ltOptions.put ("accessibility.bestPractice", false);
        ltOptions.put ("accessibility.needsReview", true);

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

    @Test (priority = 2)
    public void testLoginFunction () {
        final WebElement emailAddressField = this.driver.findElement (By.id ("input-email"));
        emailAddressField.sendKeys ("davidJacob@demo.com");
        final WebElement passwordField = this.driver.findElement (By.id ("input-password"));
        passwordField.sendKeys ("Password123");
        final WebElement loginBtn = this.driver.findElement (By.cssSelector ("input.btn-primary"));
        loginBtn.click ();

        final WebElement myAccountLink = this.driver.findElement (
            By.cssSelector ("#widget-navbar-217834 > ul > li:nth-child(6) > a"));
        final Actions actions = new Actions (this.driver);
        actions.moveToElement (myAccountLink)
            .build ()
            .perform ();
        final WebElement logoutLink = this.driver.findElement (By.linkText ("Logout"));
        assertTrue (logoutLink.isDisplayed ());
    }

    @Test (priority = 1)
    public void testNavigationToLoginPage () {
        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final WebElement myAccountLink = this.driver.findElement (
            By.cssSelector ("#widget-navbar-217834 > ul > li:nth-child(6) > a"));
        final Actions actions = new Actions (this.driver);
        actions.moveToElement (myAccountLink)
            .build ()
            .perform ();
        final WebElement loginLink = this.driver.findElement (By.linkText ("Login"));
        loginLink.click ();

        final String pageHeaderText = this.driver.findElement (
                By.cssSelector ("#content > div > div:nth-child(2) > div h2"))
            .getText ();
        assertEquals (pageHeaderText, "Returning Customer");
    }
}
