package io.github.mfaisalkhatri.assertionsdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class HardAssertions {

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
        //driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test(enabled = false)
    public void testAssertEquals() {

        driver.get("https://www.lambdatest.com/selenium-playground/");
        String pageHeader = driver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals(pageHeader, "Selenium Playground", "Page header mismatch");

    }

    @Test
    public void testAssertNotEquals() {

        driver.get("https://www.lambdatest.com/selenium-playground/");
        String homePageHeader = driver.findElement(By.tagName("h1")).getText();
        WebElement ajaxFormSubmitLink = driver.findElement(By.linkText("Ajax Form Submit"));
        ajaxFormSubmitLink.click();
        String ajaxFormHeader = driver.findElement(By.cssSelector("div > h1")).getText();
        Assert.assertNotEquals(homePageHeader,ajaxFormHeader);
    }


    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("126.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Selenium Assertions demo");
        ltOptions.put("build", "LambdaTest Selenium Playground");
        ltOptions.put("name", "Hard and Soft assertions Test");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");

        browserOptions.setCapability("LT:Options", ltOptions);

        return browserOptions;

    }

    @AfterTest
    public void tearDown() {
        this.driver.quit();
    }

}