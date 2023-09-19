package io.github.mfaisalkhatri;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static java.text.MessageFormat.format;

public class DriverManager {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final String GRID_URL = "@hub.lambdatest.com/wd/hub";
    private static final String LT_ACCESS_KEY = System.getProperty("LT_ACCESS_KEY");
    private static final String LT_USERNAME = System.getProperty("LT_USERNAME");

    public void createDriver(final Browsers browser) {
        switch (browser) {
            case FIREFOX -> setupFirefoxInLocal();
            case CHROME_CLOUD -> setupChromeInLambdaTest();
            case FIREFOX_CLOUD -> setupFirefoxInLambdaTest();
            default -> setupChromeInLocal();
        }
        setupBrowserTimeouts();
    }

    public WebDriver getDriver() {
        return DriverManager.DRIVER.get();
    }

    public void quitDriver() {
        if (null != DRIVER.get()) {
            getDriver().quit();
            DRIVER.remove();
        }
    }

    private HashMap<String, Object> ltOptions() {
        final var ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", LT_USERNAME);
        ltOptions.put("accessKey", LT_ACCESS_KEY);
        ltOptions.put("resolution", "2560x1440");
        ltOptions.put("selenium_version", "4.0.0");
        ltOptions.put("build", "LambdaTest Scale Demo");
        ltOptions.put("name", "LambdaTest tests at scale");
        ltOptions.put("acceptInsecureCerts", true);
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        return ltOptions;
    }

    private void setDriver(final WebDriver driver) {
        DriverManager.DRIVER.set(driver);
    }

    private void setupBrowserTimeouts() {
        getDriver().manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(20));
    }

    private void setupChromeInLocal() {
        setDriver(new ChromeDriver());
    }

    private void setupChromeInLambdaTest() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setCapability("LT:Options", ltOptions());
        try {
            setDriver(
                    new RemoteWebDriver(new URL(format("https://{0}:{1}{2}", LT_USERNAME, LT_ACCESS_KEY, GRID_URL)),
                            browserOptions));
        } catch (final MalformedURLException e) {
            throw new Error("Error setting up cloud browser in LambdaTest", e);
        }
    }

    private void setupFirefoxInLambdaTest() {
        final var browserOptions = new FirefoxOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setCapability("LT:Options", ltOptions());
        try {
            setDriver(
                    new RemoteWebDriver(new URL(format("https://{0}:{1}{2}", LT_USERNAME, LT_ACCESS_KEY, GRID_URL)),
                            browserOptions));
        } catch (final MalformedURLException e) {
            throw new Error("Error setting up cloud browser in LambdaTest", e);
        }
    }

    private void setupFirefoxInLocal() {
        setDriver(new FirefoxDriver());
    }
}
