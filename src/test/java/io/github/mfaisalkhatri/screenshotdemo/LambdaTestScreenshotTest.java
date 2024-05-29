package io.github.mfaisalkhatri.screenshotdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class LambdaTestScreenshotTest {

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
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testScreenshotCaptureOnLambdaTest() {

        driver.get("https://ecommerce-playground.lambdatest.io/");
        WebElement myAccountLink = driver.findElement(By.linkText("My account"));
        Actions actions = new Actions(driver);
        actions.moveToElement(myAccountLink).build().perform();

        WebElement registerLink = driver.findElement(By.linkText("Register"));
        actions.moveToElement(registerLink).click().build().perform();
        String registerPageHeader = driver.findElement(By.tagName("h1")).getText();
        assertEquals(registerPageHeader, "Register Account");

        WebElement loginLink = driver.findElement(By.linkText("Login"));
        loginLink.click();
        String loginPageHeader = driver.findElement(By.cssSelector(".col-lg-6  h2")).getText();
        assertEquals(loginPageHeader, "New Customer");
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("125");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "LambdaTest Screenshot Testing demo");
        ltOptions.put("name", "Capture Screenshot test");
        ltOptions.put("build", "LambdaTest Screenshot Testing demo");
        ltOptions.put("w3c", true);
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        ltOptions.put("plugin", "java-testNG");

        browserOptions.setCapability("LT:Options", ltOptions);

        return browserOptions;
    }

    @AfterTest
    public void tearDown() {
        this.driver.quit();
    }
}