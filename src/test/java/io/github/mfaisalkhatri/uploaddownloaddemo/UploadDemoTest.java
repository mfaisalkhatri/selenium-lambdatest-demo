package io.github.mfaisalkhatri.uploaddownloaddemo;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UploadDemoTest {

    private WebDriver driver;

    @BeforeTest
    public void setup () {
        this.driver = new ChromeDriver ();
        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (20));
    }

    @AfterTest
    public void tearDown () {
        this.driver.quit ();
    }
    
    @Test
    public void testFileUpload () {

        this.driver.get ("https://www.lambdatest.com/selenium-playground/upload-file-demo");
        final WebElement chooseFile = this.driver.findElement (By.id ("file"));
        chooseFile.sendKeys ("/Users/faisalkhatri/Blogs/file_upload_download/file_example_JPG_100kB.jpg");
        final String successMessage = this.driver.findElement (By.id ("error"))
            .getText ();
        assertEquals (successMessage, "File Successfully Uploaded");
    }
}