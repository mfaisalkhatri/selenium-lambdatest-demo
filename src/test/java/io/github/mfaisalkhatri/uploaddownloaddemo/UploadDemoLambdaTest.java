package io.github.mfaisalkhatri.uploaddownloaddemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class UploadDemoLambdaTest {

    private RemoteWebDriver driver;
    private String status = "failed";

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
        driver.setFileDetector(new LocalFileDetector());
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    public void testFileUpload() {

        driver.get("https://www.lambdatest.com/selenium-playground/upload-file-demo");
        WebElement chooseFile = driver.findElement(By.id("file"));
        chooseFile.sendKeys("/Users/faisalkhatri/Blogs/file_upload_download/file_example_JPG_100kB.jpg");
        String successMessage = driver.findElement(By.id("error")).getText();
        assertEquals(successMessage, "File Successfully Uploaded");
        status = "passed";
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 11");
        browserOptions.setBrowserVersion("latest");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "LambdaTest Grid Demo");
        ltOptions.put("build", "Upload File page demo");
        ltOptions.put("name", "Upload File test on LambdaTest cloud grid");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");

        browserOptions.setCapability("LT:Options", ltOptions);

        return browserOptions;
    }

    @AfterTest
    public void tearDown() {

        this.driver.executeScript("lambda-status=" + this.status);
        this.driver.quit();
    }
}