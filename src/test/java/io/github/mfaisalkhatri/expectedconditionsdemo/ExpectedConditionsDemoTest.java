package io.github.mfaisalkhatri.expectedconditionsdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class ExpectedConditionsDemoTest extends BaseTest {

    @Test
    public void testElementToBeClickableCondition() {
        driver.get("https://www.lambdatest.com/selenium-playground/simple-form-demo");
        WebElement enterMessageField = driver.findElement(By.id("user-message"));
        String inputMessage = "This is a test";
        enterMessageField.sendKeys(inputMessage);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("showInput"))).click();

        String messageText = driver.findElement(By.id("message")).getText();
        assertEquals(messageText, inputMessage);
    }
}
