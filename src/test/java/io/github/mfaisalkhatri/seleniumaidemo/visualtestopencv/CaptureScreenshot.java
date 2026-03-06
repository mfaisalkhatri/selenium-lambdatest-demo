package io.github.mfaisalkhatri.seleniumaidemo.visualtestopencv;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CaptureScreenshot {

    public static void captureActualImage () throws IOException {
        final WebDriver driver = new ChromeDriver ();

        driver.get ("https://ecommerce-playground.lambdatest.io/");
        driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (3));

        final File screenshot = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);
        FileUtils.copyFile (screenshot, new File ("screenshots/actual.png"));

        System.out.println ("Screenshot captured!");
        driver.quit ();
    }
}