package io.github.mfaisalkhatri.uploaddownloaddemo;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AutoITDemoTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.manage()
            .timeouts()
            .implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    public void testFileUploadDownload() throws IOException, InterruptedException {

        driver.get("https://www.ilovepdf.com/pdf_to_word");

        driver.findElement(By.id("pickfiles"))
            .click();
        Thread.sleep(5000);

        Runtime.getRuntime()
            .exec("C:\\Users\\Faisal\\AutoIt_script\\uploadfile.exe");
        Thread.sleep(5000);

        WebElement convertToWordBtn = driver.findElement(By.id("processTask"));
        assertTrue(convertToWordBtn.isDisplayed());

        convertToWordBtn.click();
        Thread.sleep(5000);

        WebElement downloadWordFile = driver.findElement(By.id("pickfiles"));
        downloadWordFile.click();

        Runtime.getRuntime()
            .exec("C:\\Users\\Faisal\\AutoIt_script\\downloadfile.exe");
        Thread.sleep(5000);

        assertTrue(checkFileDownload("Smallpdf.docx"));
    }

    public boolean checkFileDownload(final String downloadedFileName) {
        File directory = new File(String.valueOf(Paths.get(System.getProperty("user.home"), "Downloads")));
        String[] fileList = directory.list();

        int flag = 0;
        if (fileList != null) {
            for (String fileName : fileList) {
                if (fileName.equalsIgnoreCase(downloadedFileName)) {
                    System.out.println("Downloaded file Found: " + directory + " " + fileName);
                    flag = 1;
                }
            }
        } else {
            System.out.println("Downloads directory is Empty!" + directory);
            return false;
        }
        if (flag == 0) {
            System.out.println("Error: Downloaded File not found in the path!!" + directory);
            return false;
        }
        return true;
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}