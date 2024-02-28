package io.github.mfaisalkhatri.clickdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ClickDemoTests {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        String userName = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
        String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
        String gridUrl = "@hub.lambdatest.com/wd/hub";
        try {
            this.driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + gridUrl), getChromeOptions());
        } catch (final MalformedURLException e) {
            System.out.println("Could not start the remote session on LambdaTest cloud grid");
        }

    }

    @Test
    public void testSimpleForm() {
        this.driver.get("https://www.lambdatest.com/selenium-playground/simple-form-demo");
        final String message = "This is a sample message";
        final WebElement enterMessage = this.driver.findElement(By.id("user-message"));
        enterMessage.sendKeys(message);
        final WebElement getCheckedValueBtn = this.driver.findElement(By.id("showInput"));
        getCheckedValueBtn.click();
        String yourMessageText = this.driver.findElement(By.id("message")).getText();
        assertEquals(yourMessageText, message);

    }

    @Test
    public void checkboxDemoTest() {
        this.driver.get("https://www.lambdatest.com/selenium-playground/checkbox-demo");
        final WebElement checkboxOne = this.driver.findElement(By.id("isAgeSelected"));
        checkboxOne.click();
        assertTrue(checkboxOne.isSelected());
        final String selectedResult = this.driver.findElement(By.id("txtAge")).getText();
        assertEquals(selectedResult, "Checked");
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("122.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Selenium Click Demo");
        ltOptions.put("build", "Selenium Tests");
        ltOptions.put("name", "Selenium Click method tests");
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
