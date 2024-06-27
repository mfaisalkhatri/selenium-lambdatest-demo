package io.github.mfaisalkhatri.uploaddownloaddemo;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FileDownloadCloudTest {

    private RemoteWebDriver driver;

    @BeforeTest
    @Parameters({ "browser", "browserVersion", "platform" })
    public void setup(String browser, String browserVersion, String platform) {
        final String userName = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
        final String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
        final String gridUrl = "@hub.lambdatest.com/wd/hub";

        try {
            driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + gridUrl), getChromeOptions(browserVersion, platform));

        } catch (final MalformedURLException e) {
            throw new Error("Could not start the chrome browser on LambdaTest cloud grid");
        }
        driver.setFileDetector(new LocalFileDetector());
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


    private ChromeOptions getChromeOptions(String browserVersion, String platform) {
        var chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.prompt_for_download", "false");
        chromePrefs.put("download.default_directory", String.valueOf(Paths.get(System.getProperty("user.home"), "Downloads")));

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.setPlatformName(platform);
        chromeOptions.setBrowserVersion(browserVersion);
        chromeOptions.setCapability("LT:Options", getLtOptions());

        return chromeOptions;
    }

    private HashMap<String, Object> getLtOptions() {
        final var ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "LambdaTest Selenium Playground");
        ltOptions.put("build", "File Download Web Page");
        ltOptions.put("name", "File download using Chrome");
        ltOptions.put("w3c", true);
        ltOptions.put("visual", true);
        ltOptions.put("plugin", "java-testNG");
        return ltOptions;
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}