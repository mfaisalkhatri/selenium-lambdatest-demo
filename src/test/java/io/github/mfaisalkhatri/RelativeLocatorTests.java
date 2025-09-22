package io.github.mfaisalkhatri;

import static org.openqa.selenium.support.locators.RelativeLocator.with;
import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class RelativeLocatorTests {
    String    accesskey = System.getenv ("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv ("LT_ACCESS_KEY");
    WebDriver driver;
    String    gridURL   = "@hub.lambdatest.com/wd/hub";
    String    username  = System.getenv ("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv ("LT_USERNAME");

    @BeforeTest
    public void setup () throws MalformedURLException {
        final ChromeOptions browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("122.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Selenium Locator Demo");
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");
        ltOptions.put ("build", "Demonstration: Selenium Locator Demo on LambdaTest");
        browserOptions.setCapability ("LT:Options", ltOptions);

        this.driver = new RemoteWebDriver (new URL ("https://" + this.username + ":" + this.accesskey + this.gridURL),
            browserOptions);
    }

    @AfterTest
    public void tearDown () {
        this.driver.quit ();
    }

    @Test
    public void testRelativeLocator () {

        this.driver.get (
            "https://ecommerce-playground.lambdatest.io/index.php?route=product/manufacturer/info&manufacturer_id=8");

        final WebElement iMac = this.driver.findElement (By.linkText ("iMac"));
        final WebElement iMacPriceViaBelow = this.driver.findElement (
            with (By.cssSelector ("div.price> span")).below (iMac));
        assertEquals (iMacPriceViaBelow.getText (), "$170.00");

        final WebElement fetchToTheRight = this.driver.findElement (with (By.tagName ("h4")).toRightOf (iMac));
        assertEquals (fetchToTheRight.getText (), "Apple Cinema 30\"");

        final WebElement fetchViaAbove = this.driver.findElement (with (By.tagName ("h4")).above (fetchToTheRight));
        assertEquals (fetchViaAbove.getText (), "iPod Nano");

        final WebElement fetchViaToLeftOf = this.driver.findElement (with (By.tagName ("h4")).toLeftOf (fetchViaAbove));
        assertEquals (fetchViaToLeftOf.getText (), "iPod Shuffle");

    }

}
