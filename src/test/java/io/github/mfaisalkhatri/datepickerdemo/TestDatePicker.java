package io.github.mfaisalkhatri.datepickerdemo;

import io.github.mfaisalkhatri.datepickerdemo.pages.JQueryDatePickerPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class TestDatePicker {

    private RemoteWebDriver driver;
    private String status = "failed";

    @BeforeTest
    public void setup() {
        final String userName = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
        final String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
        final String gridUrl = "@hub.lambdatest.com/wd/hub";
        try {
            this.driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + gridUrl), getChromeOptions());
        } catch (final MalformedURLException e) {
            System.out.println("Could not start the remote session on LambdaTest cloud grid");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("128.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Selenium DatePicker demo");
        ltOptions.put("build", "LambdaTest Selenium Playground");
        ltOptions.put("name", "Select Date test with Selenium Java");
        ltOptions.put("w3c", true);
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        ltOptions.put("plugin", "java-testNG");

        browserOptions.setCapability("LT:Options", ltOptions);

        return browserOptions;
    }

    @Test
    public void testJQueryDatePickerSelection() {
        driver.get("https://www.lambdatest.com/selenium-playground/jquery-date-picker-demo");

        JQueryDatePickerPage jQueryDatePickerPage = new JQueryDatePickerPage(driver);
        jQueryDatePickerPage.selectFromDate("Oct", "19");
        assertEquals(jQueryDatePickerPage.getFromDateValue(), "10/19/2024");

        jQueryDatePickerPage.selectToDate("Dec", "9");
        assertEquals(jQueryDatePickerPage.getToDateValue(), "12/09/2024");
        this.status="passed";
    }

    @Test
    public void testJQueryDatePickerUsingSendKeys() {
        driver.get("https://www.lambdatest.com/selenium-playground/jquery-date-picker-demo");
        WebElement fromDateField = driver.findElement(By.id("from"));
        fromDateField.sendKeys("09/11/2024");
        String fromDateValue = driver.findElement(By.id("from")).getAttribute("value");
        assertEquals(fromDateValue, "09/11/2024");
        this.status="passed";

    }

    @Test
    public void testBootStrapDatePickerSelection() {
        driver.get("https://www.lambdatest.com/selenium-playground/bootstrap-date-picker-demo");
        WebElement birthDayField = driver.findElement(By.id("birthday"));
        birthDayField.sendKeys("10/24/2024");

        String getDateValue = birthDayField.getAttribute("value");
        assertEquals(getDateValue, "2024-10-24");
        this.status="passed";
    }

    @Test
    public void testBootStrapDateStartEndDateSelectionUsingJS() {
        driver.get("https://www.lambdatest.com/selenium-playground/bootstrap-date-picker-demo");

        WebElement startDateField = driver.findElement(By.cssSelector("#datepicker input:first-child"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', '26/09/2024')", startDateField);
        String fromDateFieldValue = startDateField.getAttribute("value");
        assertEquals(fromDateFieldValue, "26/09/2024");

        WebElement endDateField = driver.findElement(By.cssSelector("#datepicker input:last-child"));
        js.executeScript("arguments[0].setAttribute('value', '04/10/2024')", endDateField);

        String toDateFieldValue = endDateField.getAttribute("value");
        assertEquals(toDateFieldValue, "04/10/2024");
        this.status="passed";
    }

    @AfterTest
    public void tearDown() {
        this.driver.executeScript("lambda-status=" + this.status);
        driver.quit();
    }
}
