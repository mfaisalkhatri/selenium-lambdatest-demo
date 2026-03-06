package io.github.mfaisalkhatri.seleniumaidemo.autohealing;

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

public class AutoHealingTests {

    private static final String          GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String          LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String          LT_USERNAME   = System.getenv ("LT_USERNAME");
    private              RemoteWebDriver driver;

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 11");
        browserOptions.setBrowserVersion ("latest");
        final HashMap<String, Object> ltOptions = new HashMap<> ();
        ltOptions.put ("project", "Selenium Demo");
        ltOptions.put ("build", "Selenium Playground tests");
        ltOptions.put ("name", "Selenium Auto Heal Demo");
        ltOptions.put ("w3c", true);
        ltOptions.put ("autoHeal", true);
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
    public void testAutoHealing () {
        this.driver.get ("https://www.testmuai.com/selenium-playground/auto-healing/");

        this.driver.findElement (By.id ("username"))
            .sendKeys ("faisalk@email.com");
        this.driver.findElement (By.id ("password"))
            .sendKeys ("Password221");
        this.driver.findElement (By.id ("password"))
            .sendKeys ("Password221");
        this.driver.findElement (By.cssSelector ("button.bg-black[type=\"submit\"]"))
            .click ();

        final WebElement changeDOMBtn = this.driver.findElement (By.cssSelector ("p.selenium_btn"));
        final JavascriptExecutor js = this.driver;
        js.executeScript ("arguments[0].click();", changeDOMBtn);
        System.out.println ("clicked on Change DOM ID");

        this.driver.findElement (By.id ("username"))
            .sendKeys ("faisalk@proton.com");
    }
}
