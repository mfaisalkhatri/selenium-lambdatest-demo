package io.github.mfaisalkhatri.webdrivermanagertests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.Assert.assertTrue;

public class ChromeRemoteWithWebDriverManagerTest {

    WebDriver driver;
    String username = System.getenv("LT_USERNAME") == null ? "LT_USERNAME": System.getenv("LT_USERNAME");
    String accesskey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY": System.getenv("LT_ACCESS_KEY");
    String gridURL = "@hub.lambdatest.com/wd/hub";
    @BeforeTest
    public void setup() {
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("MacOS Catalina");
        browserOptions.setBrowserVersion("latest-1");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "WebDriverManager Demo");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        ltOptions.put("build", "Demonstration: WebDriverManager on LambdaTest");
        browserOptions.setCapability("LT:Options", ltOptions);

        WebDriverManager webDriverManager = WebDriverManager.chromedriver().capabilities(browserOptions);
        webDriverManager.setup();
        driver = webDriverManager.remoteAddress("https://" + username + ":" + accesskey + gridURL).create();
    }
    @Test
    public void checkTheUrl() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("lambdatest"));
    }
    @AfterTest
    public void teardown() {
        driver.quit();
    }
}