package io.github.mfaisalkhatri.uploaddownloaddemo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FileUploadTest {

    private RemoteWebDriver driver;
    private String status = "failed";

    @BeforeTest
    @Parameters ({ "browser", "browserVersion", "platform" })
    public void setup(String browser, String browserVersion, String platform) {
        final String userName = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
        final String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
        final String gridUrl = "@hub.lambdatest.com/wd/hub";

        try {
            driver = new RemoteWebDriver(new URL ("http://" + userName + ":" + accessKey + gridUrl), getChromeOptions(browserVersion, platform));

        } catch (final MalformedURLException e) {
            throw new Error("Could not start the chrome browser on LambdaTest cloud grid");
        }
        driver.setFileDetector(new LocalFileDetector ());
        driver.manage()
            .timeouts()
            .implicitlyWait(Duration.ofSeconds(20));
    }

    @Test()
    public void testFileUpload() {
        driver.get ("https://filebin.net/");
        WebElement selectFileToUploadButton = driver.findElement (By.id ("fileField"));
        String fileName = "file_example_JPG_100kB.jpg";
        selectFileToUploadButton.sendKeys ("/Users/faisalkhatri/Blogs/file_upload_download/" + fileName);
        WebElement tableRow = driver.findElement (By.cssSelector ("table > tbody > tr"));
        String fileNameText = tableRow.findElement (By.cssSelector ("td:nth-child(1) > a")).getText ();
        assertEquals (fileNameText, fileName);
        this.status = "passed";
    }

    @Test
    public void testUploadFileForPlagiarismCheck() {
        driver.get ("https://smallseotools.com/plagiarism-checker/");
        WebElement attachFile = driver.findElement (By.cssSelector ("div #fileUpload"));
        attachFile.sendKeys ("/Users/faisalkhatri/Blogs/file_upload_download/samplepdf.pdf");

        WebDriverWait wait = new WebDriverWait (driver,Duration.ofSeconds (20));
        wait.until (ExpectedConditions.invisibilityOfElementLocated (By.cssSelector ("#loader_con11 p")));

        String wordsCount = driver.findElement (By.cssSelector ("span#count_")).getText ();
        assertTrue (Integer.parseInt (wordsCount) > 0);
        this.status = "passed";
    }

    private ChromeOptions getChromeOptions(String browserVersion, String platform) {
        var chromePrefs = new HashMap<String, Object> ();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.setPlatformName(platform);
        chromeOptions.setBrowserVersion(browserVersion);
        chromeOptions.setCapability("LT:Options", getLtOptions());

        return chromeOptions;
    }

    private HashMap<String, Object> getLtOptions() {
        final var ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "LambdaTest File upload download demo");
        ltOptions.put("build", "File Upload Web Page");
        ltOptions.put("name", "File Upload using Chrome");
        ltOptions.put("w3c", true);
        ltOptions.put("visual", true);
        ltOptions.put("plugin", "java-testNG");
        return ltOptions;
    }

    @AfterTest
    public void tearDown() {
        this.driver.executeScript("lambda-status=" + this.status);
        driver.quit();
    }
}
