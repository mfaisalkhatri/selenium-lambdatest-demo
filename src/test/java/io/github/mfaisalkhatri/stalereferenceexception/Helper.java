package io.github.mfaisalkhatri.stalereferenceexception;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Helper {

    private static RemoteWebDriver driver = BaseTest.driver;

    public static boolean retryUsingWhileLoop_TryCatch(By locator, String value) {
        boolean outcome = false;
        int repeat = 0;
        while (repeat <= 3) {
            try {
                driver.findElement(locator).sendKeys(value);
                outcome = true;
                break;
            } catch (StaleElementReferenceException exc) {
                exc.printStackTrace();
            }
            repeat++;
        }
        return outcome;
    }

    public static boolean retryUsingForLoop_TryCatch(By locator, String value) {
        boolean outcome = false;
        for(int repeat=0; repeat<=3; repeat++) {
            try {
                driver.findElement(locator).sendKeys(value);
                outcome = true;
                break;
            } catch(StaleElementReferenceException exc) {
                exc.printStackTrace();
            }
        }
        return outcome;
    }

    public static void chainMultipleExpectedConditions(By locator, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.presenceOfElementLocated(locator)));
        driver.findElement(locator).sendKeys(value);
    }

}
