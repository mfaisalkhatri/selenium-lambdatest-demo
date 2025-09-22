package io.github.mfaisalkhatri.seleniumgriddemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

    private static final ThreadLocal<RemoteWebDriver> DRIVER = new ThreadLocal<> ();

    public RemoteWebDriver getDriver () {
        return DRIVER.get ();
    }

    @Parameters ("browser")
    @BeforeClass (alwaysRun = true)
    public void setup (final String browser) {
        try {
            if (browser.equalsIgnoreCase ("chrome")) {
                final ChromeOptions chromeOptions = new ChromeOptions ();
                chromeOptions.setCapability ("se:name", "Test on Grid - Chrome");
                setDriver (new RemoteWebDriver (new URL ("http://localhost:4444"), chromeOptions));

            } else if (browser.equalsIgnoreCase ("firefox")) {
                final FirefoxOptions firefoxOptions = new FirefoxOptions ();
                firefoxOptions.setCapability ("se:name", "Test on Grid - Firefox");
                setDriver (new RemoteWebDriver (new URL ("http://localhost:4444"), firefoxOptions));

            } else if (browser.equalsIgnoreCase ("edge")) {
                final EdgeOptions edgeOptions = new EdgeOptions ();
                edgeOptions.setCapability ("se:name", "Test on Grid - Edge");
                setDriver (new RemoteWebDriver (new URL ("http://localhost:4444"), edgeOptions));
            } else {
                throw new Error ("Browser configuration is not defined!!");
            }

        } catch (final MalformedURLException e) {
            throw new Error ("Error setting up browsers in Grid");
        }
        getDriver ().manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (20));
    }

    @AfterTest (alwaysRun = true)
    public void tearDown () {
        getDriver ().quit ();
    }

    private void setDriver (final RemoteWebDriver remoteWebDriver) {
        DRIVER.set (remoteWebDriver);
    }
}
