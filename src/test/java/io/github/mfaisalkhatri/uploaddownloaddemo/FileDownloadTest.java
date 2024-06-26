package io.github.mfaisalkhatri.uploaddownloaddemo;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FileDownloadTest {

    private WebDriver driver;

    @Parameters("browser")
    @BeforeTest
    public void setup(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            var chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("download.prompt_for_download", "false");
            chromePrefs.put("download.default_directory", String.valueOf(Paths.get(System.getProperty("user.home"), "Downloads")));
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("prefs", chromePrefs);

            driver = new ChromeDriver(chromeOptions);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile", "application/octet-stream");
            firefoxProfile.setPreference("browser.download.useDownloadDir", true);
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setProfile(firefoxProfile);
            driver = new FirefoxDriver(firefoxOptions);
        } else {
            throw new Error("Browser configuration is not set!");
        }

        driver.manage()
            .timeouts()
            .implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    public void testFileDownload() throws InterruptedException {
        driver.get("https://www.lambdatest.com/selenium-playground/download-file-demo");
        WebElement downloadBtn = driver.findElement(By.cssSelector(".btn-primary"));
        downloadBtn.click();
        Thread.sleep(5000);
        assertTrue(checkFileDownload("LambdaTest.pdf"));
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