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
    private String          status = "failed";

    @BeforeTest
    @Parameters ({ "browser", "browserVersion", "platform" })
    public void setup (final String browser, final String browserVersion, final String platform) {
        final String userName = System.getenv ("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv ("LT_USERNAME");
        final String accessKey = System.getenv ("LT_ACCESS_KEY") == null
                                 ? "LT_ACCESS_KEY"
                                 : System.getenv ("LT_ACCESS_KEY");
        final String gridUrl = "@hub.lambdatest.com/wd/hub";

        try {
            this.driver = new RemoteWebDriver (new URL ("https://" + userName + ":" + accessKey + gridUrl),
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
        this.driver.executeScript ("lambda-status=" + this.status);
        this.driver.quit ();
    }

    @Test ()
    public void testFileUpload () {
        this.driver.get ("https://filebin.net/");
        final WebElement selectFileToUploadButton = this.driver.findElement (By.id ("fileField"));
        final String fileName = "file_example_JPG_100kB.jpg";
        selectFileToUploadButton.sendKeys ("/Users/faisalkhatri/Blogs/file_upload_download/" + fileName);
        final WebElement tableRow = this.driver.findElement (By.cssSelector ("table > tbody > tr"));
        final String fileNameText = tableRow.findElement (By.cssSelector ("td:nth-child(1) > a"))
            .getText ();
        assertEquals (fileNameText, fileName);
        this.status = "passed";
    }

    @Test
    public void testUploadFileForPlagiarismCheck () {
        this.driver.get ("https://smallseotools.com/plagiarism-checker/");
        final WebElement attachFile = this.driver.findElement (By.cssSelector ("div #fileUpload"));
        attachFile.sendKeys ("/Users/faisalkhatri/Blogs/file_upload_download/samplepdf.pdf");

        final WebDriverWait wait = new WebDriverWait (this.driver, Duration.ofSeconds (20));
        wait.until (ExpectedConditions.invisibilityOfElementLocated (By.cssSelector ("#loader_con11 p")));

        final String wordsCount = this.driver.findElement (By.cssSelector ("span#count_"))
            .getText ();
        assertTrue (Integer.parseInt (wordsCount) > 0);
        this.status = "passed";
    }

    private ChromeOptions getChromeOptions (final String browserVersion, final String platform) {
        final var chromePrefs = new HashMap<String, Object> ();
        final ChromeOptions chromeOptions = new ChromeOptions ();
        chromeOptions.setExperimentalOption ("prefs", chromePrefs);
        chromeOptions.setPlatformName (platform);
        chromeOptions.setBrowserVersion (browserVersion);
        chromeOptions.setCapability ("LT:Options", getLtOptions ());

        return chromeOptions;
    }

    private HashMap<String, Object> getLtOptions () {
        final var ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "LambdaTest File upload download demo");
        ltOptions.put ("build", "File Upload Web Page");
        ltOptions.put ("name", "File Upload using Chrome");
        ltOptions.put ("w3c", true);
        ltOptions.put ("visual", true);
        ltOptions.put ("plugin", "java-testNG");
        return ltOptions;
    }
}