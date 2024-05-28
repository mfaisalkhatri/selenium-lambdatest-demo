package io.github.mfaisalkhatri.screenshotdemo;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class ViewableScreenshotExample {

    WebDriver driver;

    @BeforeTest
    public void setupUrl() {

        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        driver.get("https://ecommerce-playground.lambdatest.io/");
    }

    @Test
    public void testTakeViewableScreenshot() {

        WebElement blogMenu = driver.findElement(By.cssSelector("div.entry-section div.entry-widget ul > li:nth-child(3) > a > div > span"));
        blogMenu.click();

        WebElement firstArticleImage = driver.findElement(By.className("article-thumb"));
        Actions actions = new Actions(driver);
        actions.scrollToElement(firstArticleImage).build().perform();

        WebElement secondArticleImage = driver.findElement(By.cssSelector(".swiper-wrapper div[aria-label='2 / 10']"));
        actions.scrollToElement(secondArticleImage).build().perform();

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File("./screenshots/blogpage.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}