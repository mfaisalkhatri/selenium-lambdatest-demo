package io.github.mfaisalkhatri.uploaddownloaddemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class UploadDemoTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    public void testFileUpload() {

        driver.get("https://www.lambdatest.com/selenium-playground/upload-file-demo");
        WebElement chooseFile = driver.findElement(By.id("file"));
        chooseFile.sendKeys("/Users/faisalkhatri/Blogs/file_upload_download/file_example_JPG_100kB.jpg");
        String successMessage = driver.findElement(By.id("error")).getText();
        assertEquals(successMessage, "File Successfully Uploaded");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}