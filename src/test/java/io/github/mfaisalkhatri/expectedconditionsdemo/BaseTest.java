package io.github.mfaisalkhatri.expectedconditionsdemo;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class BaseTest {
    protected RemoteWebDriver driver;

    @BeforeClass
    public void setup() {

        String USERNAME = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
        String ACCESS_KEY = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
        String GRID_URL = "@hub.lambdatest.com/wd/hub";

        try {
            this.driver = new RemoteWebDriver(new URL("http://" + USERNAME + ":" + ACCESS_KEY + GRID_URL), getChromeOptions());
        } catch (final MalformedURLException e) {
            System.out.println("Could not start the remote session on LambdaTest cloud grid");
        }
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("126.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Expected Conditions Blog");
        ltOptions.put("build", "LambdaTest");
        ltOptions.put("name", "Expected Conditions Demo");
        ltOptions.put("visual", true);
        ltOptions.put("network", true);
        ltOptions.put("console", true);
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");

        browserOptions.setCapability("LT:Options", ltOptions);

        return browserOptions;

    }

    @AfterClass
    public void tearDown() {
        this.driver.quit();
    }
}