package io.github.mfaisalkhatri.debugtestsdemo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SeleniumECommerceTests {

    RemoteWebDriver driver;
    private String status = "failed";

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("124");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Selenium ECommerce playground website");
        ltOptions.put ("build", "LambdaTest Ecommerce Website tests");
        ltOptions.put ("name", "Test broken links on LambdaTest ECommerce playground website");
        ltOptions.put ("w3c", true);
        ltOptions.put ("visual", true);
        ltOptions.put ("w3c", true);
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
            .implicitlyWait (Duration.ofSeconds (20));
    }

    @AfterTest
    public void tearDown () {
        this.driver.executeScript ("lambda-status=" + this.status);
        this.driver.quit ();
    }

    @Test
    public void testBrokenLinks () {

        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final WebElement shopByCategoryMenu = this.driver.findElement (By.cssSelector ("div.shop-by-category a"));
        shopByCategoryMenu.click ();
        final List<WebElement> shoppingLinks = this.driver.findElements (By.cssSelector ("#widget-navbar-217841 ul li a"));

        for (int i = 0; i <= shoppingLinks.size (); i++) {

            try {
                final HttpURLConnection connection = (HttpURLConnection) new URL (shoppingLinks.get (i)
                    .getAttribute ("href")).openConnection ();
                connection.connect ();

                final URL url = connection.getURL ();
                System.out.println (url.toString ());

                final int responseCode = connection.getResponseCode ();
                System.out.println ("Response Code for :" + shoppingLinks.get (i)
                    .getText () + " is: " + responseCode);
                connection.disconnect ();

            } catch (final IOException e) {
                throw new RuntimeException (e);
            }
        }
        this.status = "passed";
    }
}