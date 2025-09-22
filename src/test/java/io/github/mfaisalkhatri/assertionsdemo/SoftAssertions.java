package io.github.mfaisalkhatri.assertionsdemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SoftAssertions {

    private static final String          GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String          LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String          LT_USERNAME   = System.getenv ("LT_USERNAME");
    private              RemoteWebDriver driver;

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("126.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Selenium Soft Assertions demo");
        ltOptions.put ("build", "LambdaTest Selenium Playground");
        ltOptions.put ("name", "Soft Assertion tests");
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
    public void testRadioBtnPageWithSoftAssert () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/radiobutton-demo");
        final String pageHeader = this.driver.findElement (By.tagName ("h1"))
            .getText ();
        final SoftAssert softAssert = new SoftAssert ();
        softAssert.assertEquals (pageHeader, "Radio button demo page");

        final WebElement maleRadioBtn = this.driver.findElement (
            By.cssSelector ("input[name='optradio'][value='Male']"));
        final WebElement femaleRadioBtn = this.driver.findElement (
            By.cssSelector ("input[name='optradio'][value='Female']"));
        maleRadioBtn.click ();
        softAssert.assertFalse (maleRadioBtn.isSelected ());
        softAssert.assertTrue (femaleRadioBtn.isSelected ());

        final String getValueButtonLabel = this.driver.findElement (By.id ("buttoncheck"))
            .getText ();
        softAssert.assertNotEquals (getValueButtonLabel, "Get value");
        softAssert.assertAll ();
    }

}
