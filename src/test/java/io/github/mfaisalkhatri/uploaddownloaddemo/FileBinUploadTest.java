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

public class FileBinUploadTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver ();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    public void testFileUpload() {

        driver.get ("https://filebin.net/");
        WebElement chooseFile = driver.findElement (By.id ("fileField"));
        String fileName = "file_example_JPG_100kB.jpg";
        chooseFile.sendKeys ("/Users/faisalkhatri/Blogs/file_upload_download/" + fileName);
        WebElement tableRow = driver.findElement (By.cssSelector ("table > tbody > tr"));
        String fileNameText = tableRow.findElement (By.cssSelector ("td:nth-child(1) > a")).getText ();
        ;
        assertEquals (fileNameText, fileName);

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
