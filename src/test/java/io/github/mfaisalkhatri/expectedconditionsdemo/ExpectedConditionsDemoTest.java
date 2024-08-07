package io.github.mfaisalkhatri.expectedconditionsdemo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

    @Test
    public void testTextToBePresent() {
        driver.get("https://www.lambdatest.com/selenium-playground/dynamic-data-loading-demo");
        WebElement getRandomUserBtn = driver.findElement(By.id("save"));
        getRandomUserBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        assertTrue(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("loading"), "First Name")));

    }

    @Test
    public void testVisibilityOfElementLocated() {
        driver.get("https://www.lambdatest.com/selenium-playground/drag-and-drop-demo");
        WebElement draggableOne = driver.findElement(By.cssSelector("#todrag > span:nth-child(2)"));
        WebElement dropBox = driver.findElement(By.id("mydropzone"));

        Actions actions = new Actions(driver);
        actions.dragAndDrop(draggableOne, dropBox).build().perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement dropListItemOne = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#droppedlist > span")));
        String dropListItemName = dropListItemOne.getText();
        assertEquals(dropListItemName, "Draggable 1");
    }

    @Test
    public void testFrameToBeAvailableAndSwitch() {
        driver.get("https://www.lambdatest.com/selenium-playground/iframe-demo/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement iFrame = driver.findElement(By.id("iFrame1"));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iFrame));

        WebElement iFrameEditor = driver.findElement(By.cssSelector("#__next > div > div.rsw-ce"));
        String text = "switched to iframe";
        iFrameEditor.clear();
        iFrameEditor.sendKeys(text);
        System.out.println("Text entered in iFrame");
    }

    @Test
    public void testElementToBeSelected() {
        driver.get("https://www.lambdatest.com/selenium-playground/checkbox-demo");
        WebElement checkAllBtn = driver.findElement(By.id("box"));
        checkAllBtn.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement checkboxOne = driver.findElement(By.id("ex1-check1"));
        assertTrue(wait.until(ExpectedConditions.elementToBeSelected(checkboxOne)));

        WebElement checkboxTwo = driver.findElement(By.id("ex1-check2"));
        assertTrue(wait.until(ExpectedConditions.elementToBeSelected(checkboxTwo)));
    }

    @Test
    public void testCustomExpectedCondition() {
        driver.get("https://www.lambdatest.com/selenium-playground/table-sort-search-demo");
        WebElement searchBox = driver.findElement(By.cssSelector("#example_filter input"));
        searchBox.sendKeys("Bennet");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                WebElement table = driver.findElement(By.id("example"));
                if (table.isDisplayed()) {
                    WebElement tableRow = driver.findElement(By.cssSelector("#example tbody tr:nth-child(1)"));
                    WebElement nameValue = tableRow.findElement(By.cssSelector("td:nth-child(1)"));
                    if (nameValue.isDisplayed() && nameValue.getText().contains("Bennet")) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });
    }
}