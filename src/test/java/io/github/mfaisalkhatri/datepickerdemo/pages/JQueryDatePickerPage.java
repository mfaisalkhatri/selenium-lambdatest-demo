package io.github.mfaisalkhatri.datepickerdemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class JQueryDatePickerPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public JQueryDatePickerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private void openFromDateCalendar() {
        WebElement fromDateField = driver.findElement(By.id("from"));
        fromDateField.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ui-datepicker-div")));
    }

    private WebElement toDateField() {
        return driver.findElement(By.id("to"));
    }

    private void selectMonth(String monthName) {
        WebElement monthField = driver.findElement(By.cssSelector(".ui-datepicker-month"));
        monthField.click();
        Select selectMonth = new Select(monthField);
        selectMonth.selectByVisibleText(monthName);
    }

    private void selectDay(String dayNumber) {
        WebElement dayValue = driver.findElement(By.xpath("//td[@data-handler='selectDay']/a[text()='" + dayNumber + "']"));
        dayValue.click();
    }

    public void selectFromDate(String monthName, String dayNumber) {
        openFromDateCalendar();
        selectMonth(monthName);
        selectDay(dayNumber);
    }

    public String getFromDateValue() {
        return driver.findElement(By.id("from")).getAttribute("value");
    }

    private void openToDateCalendar() {
        WebElement toDateField = driver.findElement(By.id("to"));
        toDateField.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ui-datepicker-div")));
    }

    public void selectToDate(String monthName, String dayNumber){
        openToDateCalendar();
        selectMonth(monthName);
        selectDay(dayNumber);
    }

    public String getToDateValue() {
        return driver.findElement(By.id("to")).getAttribute("value");
    }



}
