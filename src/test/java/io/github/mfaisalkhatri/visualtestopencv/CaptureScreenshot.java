package io.github.mfaisalkhatri.visualtestopencv;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CaptureScreenshot {

    public static void main (final String[] args) throws IOException {
        final WebDriver driver = new ChromeDriver ();

        driver.get ("https://ecommerce-playground.lambdatest.io/");

        final File screenshot = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);
        FileUtils.copyFile (screenshot, new File ("screenshots/actual.png"));

        System.out.println ("Screenshot captured!");
        driver.quit ();
    }
}