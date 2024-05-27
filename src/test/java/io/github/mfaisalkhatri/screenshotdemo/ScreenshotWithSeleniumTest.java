package io.github.mfaisalkhatri.screenshotdemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ScreenshotWithSeleniumTest {
    private WebDriver driver;

    @BeforeTest
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("webSocketUrl", true);
        driver = new ChromeDriver(options);
    }

    @Test
    public void testTakeScreenshot() throws IOException {
        BrowsingContext browsingContext = new BrowsingContext(driver, driver.getWindowHandle());

        driver.get("https://ecommerce-playground.lambdatest.io/");

        String screenshot = browsingContext.captureScreenshot();
        byte[] imgByteArray = Base64.getDecoder().decode(screenshot);
        FileOutputStream imgOutFile = new FileOutputStream("screenshot_homepage.png");
        imgOutFile.write(imgByteArray);
        imgOutFile.close();

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}