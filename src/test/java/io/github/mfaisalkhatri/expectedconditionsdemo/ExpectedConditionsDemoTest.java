package io.github.mfaisalkhatri.expectedconditionsdemo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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


    @Test
    public void testJavaScriptAlert() {
        driver.get("https://www.lambdatest.com/selenium-playground/javascript-alert-box-demo");
        WebElement clickMeBtn = driver.findElement(By.cssSelector("button.btn.my-30"));
        clickMeBtn.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        assertEquals(alertText, "I am an alert box!");
        alert.accept();
    }
}
