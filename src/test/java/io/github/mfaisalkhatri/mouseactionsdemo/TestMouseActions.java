package io.github.mfaisalkhatri.mouseactionsdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class TestMouseActions {

    private RemoteWebDriver driver;

    @BeforeTest
    public void setup() {
        final String userName = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
        final String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
        final String gridUrl = "@hub.lambdatest.com/wd/hub";
        try {
            this.driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + gridUrl), getChromeOptions());
        } catch (final MalformedURLException e) {
            System.out.println("Could not start the remote session on LambdaTest cloud grid");
        }
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("127.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Mouse Actions Demo");
        ltOptions.put("build", "LambdaTest Selenium Playground");
        ltOptions.put("name", "Perform Mouse Actions using Selenium WebDriver");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");

        browserOptions.setCapability("LT:Options", ltOptions);

        return browserOptions;
    }

    @Test
    public void testSampleCodeForPerformMethod() {
        WebDriver driver = new ChromeDriver();
        Actions actions = new Actions(driver);
        actions.perform();
    }

    @Test
    public void testMouseClickAction() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("iPhone");

        WebElement searchBtn = driver.findElement(By.cssSelector("button[type=submit]"));
        searchBtn.click();

        String pageHeader = driver.findElement(By.cssSelector("#entry_212456 h1")).getText();
        assertEquals(pageHeader, "Search - iPhone");

    }

    @AfterTest
    public void tearDown() {
        this.driver.quit();
    }
}