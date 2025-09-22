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
    public void testFileUpload () throws IOException, InterruptedException {

        this.driver.get ("https://filebin.net/");

        this.driver.findElement (By.id ("#fileField"))
            .click ();
        Thread.sleep (5000);

        Runtime.getRuntime ()
            .exec ("C:\\Users\\Faisal\\AutoIt_script\\uploadfile.exe");
        Thread.sleep (5000);

        final WebElement tableRow = this.driver.findElement (By.cssSelector ("table > tbody > tr"));
        final String fileNameText = tableRow.findElement (By.cssSelector ("td:nth-child(1) > a"))
            .getText ();
        assertEquals (fileNameText, "file_example_JPG_100kB.jpg");
    }
}
