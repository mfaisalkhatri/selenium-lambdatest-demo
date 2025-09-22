package io.github.mfaisalkhatri.jsalertdemo;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AlertTest {
    // private WebDriver driver;
    private RemoteWebDriver driver;
    private String          status = "failed";

    @BeforeClass
    public void setup () {
        //this.driver = new ChromeDriver ();
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

    @AfterClass
    public void tearDown () {
        this.driver.executeScript ("lambda-status=" + this.status);
        this.driver.quit ();
    }

    @Test
    public void testAlert () {
        this.driver.navigate ()
            .to ("https://www.lambdatest.com/selenium-playground/javascript-alert-box-demo");
        final WebElement clickMeButton = this.driver.findElement (
            By.cssSelector ("div > div:nth-child(3) > p > button"));
        clickMeButton.click ();

        final Alert alert = this.driver.switchTo ()
            .alert ();
        final String name = "Faisal K";
        alert.sendKeys (name);
        alert.accept ();

        final String promptText = this.driver.findElement (By.id ("prompt-demo"))
            .getText ();
        assertEquals (promptText, "You have entered " + "'" + name + "' !");
        this.status = "passed";
    }

    private ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 11");
        browserOptions.setBrowserVersion ("latest");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "LambdaTest Selenium Playground");
        ltOptions.put ("build", "JavaScript Alert Demo");
        ltOptions.put ("name", "Handling JS Alert in Selenium");
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");

        browserOptions.setCapability ("LT:Options", ltOptions);

        return browserOptions;
    }
}