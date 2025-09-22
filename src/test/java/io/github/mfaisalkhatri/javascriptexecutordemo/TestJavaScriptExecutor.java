package io.github.mfaisalkhatri.javascriptexecutordemo;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestJavaScriptExecutor {

    private RemoteWebDriver driver;
    private String          status = "failed";

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("127.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "LambdaTest E-Commerce Playground");
        ltOptions.put ("build", "LambdaTest E-Commerce Login page");
        ltOptions.put ("name", "JavaScriptExecutor Selenium Tests");
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
            .pageLoadTimeout (Duration.ofSeconds (10));
    }

    @AfterTest
    public void tearDown () {
        this.driver.executeScript ("lambda-status=" + this.status);
        this.driver.quit ();
    }

    @Test
    public void testExecuteAsyncScript () {
        this.driver.get ("https://ecommerce-playground.lambdatest.io");
        final JavascriptExecutor js = this.driver;
        js.executeAsyncScript (
            "var callback = arguments[arguments.length - 1];" + "window.scrollBy(0,document.body.scrollHeight); + callback()");

        final String fromTheBlogText = this.driver.findElement (By.cssSelector ("#entry_217991 > h3"))
            .getText ();
        assertEquals (fromTheBlogText, "FROM THE BLOG");
        this.status = "passed";

        final WebElement element = this.driver.findElement (By.id ("shadowroot"));
        js.executeScript ("return arguments[0].shadowRoot", element);
    }

    @Test
    public void testJavaScriptExecutorCommand () {
        this.driver.get ("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");

        final JavascriptExecutor js = this.driver;
        final WebElement emailAddressField = this.driver.findElement (By.id ("input-email"));
        js.executeScript ("arguments[0].style.border='3px solid red'", emailAddressField);
        emailAddressField.sendKeys ("davidjacob@demo.com");
        js.executeScript ("arguments[0].style.border='2px solid #ced4da'", emailAddressField);

        final WebElement passwordField = this.driver.findElement (By.id ("input-password"));
        js.executeScript ("arguments[0].style.border='3px solid red'", passwordField);
        passwordField.sendKeys ("Password123");
        js.executeScript ("arguments[0].style.border='2px solid #ced4da'", passwordField);

        final WebElement loginBtn = this.driver.findElement (By.cssSelector ("input.btn"));
        js.executeScript ("arguments[0].style.border='3px solid red'", loginBtn);
        js.executeScript ("arguments[0].click();", loginBtn);

        final String titleText = js.executeScript ("return document.title;")
            .toString ();
        System.out.println ("Page Title is: " + titleText);

        final String domainName = js.executeScript ("return document.domain;")
            .toString ();
        System.out.println ("Domain is: " + domainName);

        final String myAccountHeader = this.driver.findElement (By.cssSelector ("#content h2"))
            .getText ();
        assertEquals (myAccountHeader, "My Account");
        this.status = "passed";
    }
}
