package io.github.mfaisalkhatri.exceptionsdemo;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ExceptionsDemoTest {

    private static final String GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String LT_USERNAME   = System.getenv ("LT_USERNAME");

    private WebDriver driver;

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("122.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Selenium LambdaTest Demo");
        ltOptions.put ("build", "[Java] How to Deal with Element is not clickable at point Exception");
        ltOptions.put ("name", "[Java] How to Deal with Element is not clickable at point Exception");
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");

        browserOptions.setCapability ("LT:Options", ltOptions);

        return browserOptions;

    }

    @BeforeTest
    public void setup () {
        try {
            this.driver = new RemoteWebDriver (new URL ("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + GRID_URL),
                getChromeOptions ());
        } catch (final MalformedURLException e) {
            System.out.println ("Could not start the remote session on LambdaTest cloud grid");
        }

        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (20));
    }

    @AfterTest
    public void tearDown () {
        this.driver.quit ();
    }

    @Test
    public void testElementNotClickableException () {

        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final WebElement shopByCategory = this.driver.findElement (By.linkText ("Shop by Category"));
        shopByCategory.click ();

        final WebElement blogLink = this.driver.findElement (By.linkText ("Blog"));
        blogLink.click ();

    }

    @Test
    public void testElementNotClickableExceptionResolved () {

        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final WebElement shopByCategory = this.driver.findElement (By.linkText ("Shop by Category"));
        shopByCategory.click ();

        final WebElement menuBar = this.driver.findElement (By.cssSelector (".entry-section div.navbar-collapse"));

        final Actions actions = new Actions (this.driver);
        actions.moveToElement (menuBar)
            .click ()
            .build ()
            .perform ();

        final WebElement blogLink = this.driver.findElement (By.linkText ("Blog"));
        blogLink.click ();

        final WebElement pageTitle = this.driver.findElement (By.cssSelector (".entry-section .entry-module h3"));
        assertEquals (pageTitle.getText (), "LATEST ARTICLES");
    }
}