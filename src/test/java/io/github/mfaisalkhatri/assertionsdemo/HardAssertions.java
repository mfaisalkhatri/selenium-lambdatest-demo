package io.github.mfaisalkhatri.assertionsdemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HardAssertions {

    private static final String GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String LT_USERNAME   = System.getenv ("LT_USERNAME");

    private RemoteWebDriver driver;

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("126.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Selenium Assertions demo");
        ltOptions.put ("build", "LambdaTest Selenium Playground");
        ltOptions.put ("name", "Assert Not Null Test");
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
    public void testAssertEquals () {

        this.driver.get ("https://www.lambdatest.com/selenium-playground/");
        final String pageHeader = this.driver.findElement (By.tagName ("h1"))
            .getText ();
        Assert.assertEquals (pageHeader, "Selenium Playground", "Page header mismatch");
    }

    @Test
    public void testAssertFalse () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/radiobutton-demo");
        final WebElement maleRadioBtn = this.driver.findElement (
            By.cssSelector ("input[value='Male'][name='optradio']"));
        maleRadioBtn.click ();
        Assert.assertTrue (maleRadioBtn.isSelected ());

        final WebElement femaleRadioBtn = this.driver.findElement (
            By.cssSelector ("input[value='Female'][name='optradio']"));
        femaleRadioBtn.click ();
        Assert.assertFalse (maleRadioBtn.isSelected ());
    }

    @Test
    public void testAssertNotEquals () {

        this.driver.get ("https://www.lambdatest.com/selenium-playground/");
        final String homePageHeader = this.driver.findElement (By.tagName ("h1"))
            .getText ();
        final WebElement ajaxFormSubmitLink = this.driver.findElement (By.linkText ("Ajax Form Submit"));
        ajaxFormSubmitLink.click ();
        final String ajaxFormHeader = this.driver.findElement (By.cssSelector ("div > h1"))
            .getText ();
        Assert.assertNotEquals (homePageHeader, ajaxFormHeader);
    }

    @Test
    public void testAssertNotNull () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/");

        final WebElement simpleFormDemoLink = this.driver.findElement (By.linkText ("Simple Form Demo"));
        simpleFormDemoLink.click ();

        final String currentUrl = this.driver.getCurrentUrl ();
        Assert.assertNotNull (currentUrl);
    }

    @Test
    public void testAssertTrue () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/checkbox-demo");
        final WebElement checkboxOne = this.driver.findElement (
            By.cssSelector ("div:nth-child(1) > label > input[type=checkbox]"));
        checkboxOne.click ();
        Assert.assertTrue (checkboxOne.isSelected ());
    }

}