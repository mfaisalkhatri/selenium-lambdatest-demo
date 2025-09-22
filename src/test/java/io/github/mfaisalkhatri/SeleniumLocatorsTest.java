package io.github.mfaisalkhatri;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SeleniumLocatorsTest {

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

        this.driver = new RemoteWebDriver (new URL ("https://" + this.username + ":" + this.accesskey + this.gridURL), browserOptions);

    }

    @AfterTest
    public void teardown () {
        this.driver.quit ();
    }

    @Test
    public void testLinkText () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/");
        this.driver.findElement (By.linkText ("Ajax Form Submit"))
            .click ();
        final String header = this.driver.findElement (By.tagName ("h1"))
            .getText ();
        assertEquals (header, "Form Submit Demo");

    }

    @Test
    public void testPartialLinkText () {

        this.driver.get ("https://www.lambdatest.com/selenium-playground/");
        this.driver.findElement (By.partialLinkText ("Healing"))
            .click ();
        final String header = this.driver.findElement (By.tagName ("h1"))
            .getText ();
        assertEquals (header, "Auto Healing");

    }

    @Test
    public void testTagNames () {

        this.driver.get ("https://www.lambdatest.com/selenium-playground/");
        final List<WebElement> allLinks = this.driver.findElements (By.tagName ("a"));

    }

    @Test
    public void testXpath () throws InterruptedException {
        this.driver.get ("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");
        this.driver.findElement (By.xpath ("//input[@id=\"input-email\" and @name=\"email\"]"))
            .sendKeys ("faisal.k@gmail.com");
        //driver.findElement(By.xpath("//input[@id=\"input-email\" or @name=\"email\"]")).sendKeys("faisal.k@gmail.com");
        this.driver.findElement (By.xpath ("//input[starts-with(@name,'pass')]"))
            .sendKeys ("Password");

        this.driver.get ("https://www.lambdatest.com/selenium-playground/auto-healing");
        this.driver.findElement (By.xpath ("//button[text()=\"Submit\"]"));
    }

}
