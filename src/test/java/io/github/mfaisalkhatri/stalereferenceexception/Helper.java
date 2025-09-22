package io.github.mfaisalkhatri.stalereferenceexception;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Helper {

    private static final RemoteWebDriver DRIVER = BaseTest.driver;

    public static void chainMultipleExpectedConditions (final By locator, final String value) {
        final WebDriverWait wait = new WebDriverWait (DRIVER, Duration.ofSeconds (5));
        wait.until (ExpectedConditions.refreshed (ExpectedConditions.presenceOfElementLocated (locator)));
        DRIVER.findElement (locator)
            .sendKeys (value);
    }

    public static boolean retryUsingForLoop_TryCatch (final By locator, final String value) {
        boolean outcome = false;
        for (int repeat = 0; repeat <= 3; repeat++) {
            try {
                DRIVER.findElement (locator)
                    .sendKeys (value);
                outcome = true;
                break;
            } catch (final StaleElementReferenceException exc) {
                exc.printStackTrace ();
            }
        }
        return outcome;
    }

    public static boolean retryUsingWhileLoop_TryCatch (final By locator, final String value) {
        boolean outcome = false;
        int repeat = 0;
        while (repeat <= 3) {
            try {
                DRIVER.findElement (locator)
                    .sendKeys (value);
                outcome = true;
                break;
            } catch (final StaleElementReferenceException exc) {
                exc.printStackTrace ();
            }
            repeat++;
        }
        return outcome;
    }

}
