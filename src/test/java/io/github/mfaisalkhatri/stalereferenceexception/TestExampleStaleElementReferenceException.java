package io.github.mfaisalkhatri.stalereferenceexception;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static io.github.mfaisalkhatri.stalereferenceexception.Helper.retryUsingWhileLoop_TryCatch;

public class TestExampleStaleElementReferenceException extends BaseTest{

    @Test
    public void createStaleElementReferenceException() {
        WebElement pageLink = driver.findElement(By.linkText("Table Data Search"));
        pageLink.click();
        WebElement filterByField = driver.findElement(By.id("task-table-filter"));

        filterByField.sendKeys("in progress");
        driver.navigate().back();
        pageLink.click();

        filterByField = driver.findElement(By.id("task-table-filter"));
        filterByField.sendKeys("completed");
    }

    @Test
    public void testRetryUsingWhileLoop_TryCatch() {
        WebElement pageLink = driver.findElement(By.linkText("Table Data Search"));
        pageLink.click();
        By filterByField = By.id("task-table-filter");

        retryUsingWhileLoop_TryCatch(filterByField, "in progress");
        driver.navigate().back();
        pageLink = driver.findElement(By.linkText("Table Data Search"));
        pageLink.click();
        retryUsingWhileLoop_TryCatch(filterByField, "completed");

    }
}