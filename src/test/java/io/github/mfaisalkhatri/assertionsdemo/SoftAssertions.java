package io.github.mfaisalkhatri.assertionsdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class SoftAssertions {

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

    @Test
    public void testRadioBtnPageWithSoftAssert() {
        driver.get("https://www.lambdatest.com/selenium-playground/radiobutton-demo");
        String pageHeader = driver.findElement(By.tagName("h1")).getText();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(pageHeader, "Radio button demo page");

        WebElement maleRadioBtn = driver.findElement(By.cssSelector("input[name='optradio'][value='Male']"));
        WebElement femaleRadioBtn = driver.findElement(By.cssSelector("input[name='optradio'][value='Female']"));
        maleRadioBtn.click();
        softAssert.assertFalse(maleRadioBtn.isSelected());
        softAssert.assertTrue(femaleRadioBtn.isSelected());

        String getValueButtonLabel = driver.findElement(By.id("buttoncheck")).getText();
        softAssert.assertNotEquals(getValueButtonLabel, "Get value");
        softAssert.assertAll();
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("126.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Selenium Soft Assertions demo");
        ltOptions.put("build", "LambdaTest Selenium Playground");
        ltOptions.put("name", "Soft Assertion tests");
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
