package io.github.mfaisalkhatri.seleniumgriddemo;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

    RemoteWebDriver driver;


    @Parameters("browser")
    @BeforeClass
    public void setup(String browser) {
        try {
            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("gsg:customcap", true);
                driver = new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions);

            } else if (browser.equalsIgnoreCase("firefox")) {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("gsg:customcap", true);
                driver = new RemoteWebDriver(new URL("http://localhost:4444"), firefoxOptions);

            } else if (browser.equalsIgnoreCase("edge")) {
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new RemoteWebDriver(new URL("http://localhost:4444"), edgeOptions);
            } else {
                throw new Error("Browser configuration is not defined!!");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        driver.manage()
            .timeouts()
            .implicitlyWait(Duration.ofSeconds(20));

    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }
}
