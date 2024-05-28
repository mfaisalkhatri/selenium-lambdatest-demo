package io.github.mfaisalkhatri.screenshotdemo;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class FullPageScreenshotTest {

    WebDriver driver;

    @BeforeTest
    public void setup() {

        driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.manage().window().maximize();
    }

    @Test
    public void testTakeFullPageScreenshot()  {

        driver.get("https://ecommerce-playground.lambdatest.io/");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);

        WebElement topTrendingItemList = driver.findElement(By.className("swiper-wrapper"));
        js.executeScript("arguments[0].scrollIntoView(true);", topTrendingItemList);
        actions.pause(2000).build().perform();

        WebElement exchangeOffer = driver.findElement(By.cssSelector("#entry_213263 > div > h4"));
        js.executeScript("arguments[0].scrollIntoView(true);",exchangeOffer);
        actions.pause(2000).build().perform();

        WebElement bottom = driver.findElement(By.className("article-thumb"));
        js.executeScript("arguments[0].scrollIntoView(true);", bottom);
        actions.pause(2000).build().perform();

        File src = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File("./screenshots/fulpagescreenshot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
