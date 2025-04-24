package io.github.mfaisalkhatri.uploaddownloaddemo;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AutoITFileUploadTest {

    private WebDriver driver;

    @BeforeTest
    public void setup () {
        driver = new ChromeDriver ();
        driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (20));
    }

    @Test
    public void testFileUpload () throws IOException, InterruptedException {

        driver.get ("https://filebin.net/");

        driver.findElement (By.id ("#fileField"))
            .click ();
        Thread.sleep (5000);

        Runtime.getRuntime ()
            .exec ("C:\\Users\\Faisal\\AutoIt_script\\uploadfile.exe");
        Thread.sleep (5000);

        WebElement tableRow = driver.findElement (By.cssSelector ("table > tbody > tr"));
        String fileNameText = tableRow.findElement (By.cssSelector ("td:nth-child(1) > a"))
            .getText ();
        assertEquals (fileNameText, "file_example_JPG_100kB.jpg");
    }

    @AfterTest
    public void tearDown() {
        driver.quit ();
    }
}
