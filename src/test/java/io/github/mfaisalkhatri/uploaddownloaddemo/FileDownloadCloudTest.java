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

    private static final String GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String LT_USERNAME   = System.getenv ("LT_USERNAME");

    private RemoteWebDriver driver;

    public boolean checkFileDownload (final String downloadedFileName) {
        final File directory = new File (String.valueOf (Paths.get (System.getProperty ("user.home"), "Downloads")));
        final String[] fileList = directory.list ();

        int flag = 0;
        if (fileList != null) {
            for (final String fileName : fileList) {
                if (fileName.equalsIgnoreCase (downloadedFileName)) {
                    System.out.println ("Downloaded file Found: " + directory + " " + fileName);
                    flag = 1;
                }
            }
        } else {
            System.out.println ("Downloads directory is Empty!" + directory);
            return false;
        }
        if (flag == 0) {
            System.out.println ("Error: Downloaded File not found in the path!!" + directory);
            return false;
        }
        return true;
    }

    @BeforeTest
    @Parameters ({ "browser", "browserVersion", "platform" })
    public void setup (final String browser, final String browserVersion, final String platform) {
        try {
            this.driver = new RemoteWebDriver (new URL ("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + GRID_URL),
                getChromeOptions (browserVersion, platform));

        } catch (final MalformedURLException e) {
            throw new Error ("Could not start the chrome browser on LambdaTest cloud grid");
        }
        this.driver.setFileDetector (new LocalFileDetector ());
        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (20));
    }

    @AfterTest
    public void tearDown () {
        this.driver.quit ();
    }

    @Test
    public void testFileDownload () throws InterruptedException {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/download-file-demo");
        final WebElement downloadBtn = this.driver.findElement (By.cssSelector (".btn-primary"));
        downloadBtn.click ();
        Thread.sleep (5000);
        assertTrue (checkFileDownload ("LambdaTest.pdf"));
    }

    private ChromeOptions getChromeOptions (final String browserVersion, final String platform) {
        final var chromePrefs = new HashMap<String, Object> ();
        chromePrefs.put ("download.prompt_for_download", "false");
        chromePrefs.put ("download.default_directory",
            String.valueOf (Paths.get (System.getProperty ("user.home"), "Downloads")));

        final ChromeOptions chromeOptions = new ChromeOptions ();
        chromeOptions.setExperimentalOption ("prefs", chromePrefs);
        chromeOptions.setPlatformName (platform);
        chromeOptions.setBrowserVersion (browserVersion);
        chromeOptions.setCapability ("LT:Options", getLtOptions ());

        return chromeOptions;
    }

    private HashMap<String, Object> getLtOptions () {
        final var ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "LambdaTest Selenium Playground");
        ltOptions.put ("build", "File Download Web Page");
        ltOptions.put ("name", "File download using Chrome");
        ltOptions.put ("w3c", true);
        ltOptions.put ("visual", true);
        ltOptions.put ("plugin", "java-testNG");
        return ltOptions;
    }
}