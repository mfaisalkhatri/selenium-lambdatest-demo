package io.github.mfaisalkhatri.datepickerdemo;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import io.github.mfaisalkhatri.datepickerdemo.pages.JQueryDatePickerPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestDatePicker {

    private static final String GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String LT_USERNAME   = System.getenv ("LT_USERNAME");

    private RemoteWebDriver driver;
    private String          status = "failed";

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("128.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Selenium DatePicker demo");
        ltOptions.put ("build", "LambdaTest Selenium Playground");
        ltOptions.put ("name", "Select Date test with Selenium Java");
        ltOptions.put ("w3c", true);
        ltOptions.put ("visual", true);
        ltOptions.put ("video", true);
        ltOptions.put ("plugin", "java-testNG");

        browserOptions.setCapability ("LT:Options", ltOptions);

        return browserOptions;
    }

    @BeforeTest
    public void setup () {
        try {
            this.driver = new RemoteWebDriver (new URL ("https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + GRID_URL),
                getChromeOptions ());
        } catch (final MalformedURLException e) {
            System.out.println ("Could not start the remote session on LambdaTest cloud grid");
        }
        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (30));
    }

    @AfterTest
    public void tearDown () {
        this.driver.executeScript ("lambda-status=" + this.status);
        this.driver.quit ();
    }

    @Test
    public void testBootStrapDatePickerSelection () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/bootstrap-date-picker-demo");
        final WebElement birthDayField = this.driver.findElement (By.id ("birthday"));
        birthDayField.sendKeys ("10/24/2024");

        final String getDateValue = birthDayField.getAttribute ("value");
        assertEquals (getDateValue, "2024-10-24");
        this.status = "passed";
    }

    @Test
    public void testBootStrapDateStartEndDateSelectionUsingJS () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/bootstrap-date-picker-demo");

        final WebElement startDateField = this.driver.findElement (By.cssSelector ("#datepicker input:first-child"));

        final JavascriptExecutor js = this.driver;
        js.executeScript ("arguments[0].setAttribute('value', '26/09/2024')", startDateField);
        final String fromDateFieldValue = startDateField.getAttribute ("value");
        assertEquals (fromDateFieldValue, "26/09/2024");

        final WebElement endDateField = this.driver.findElement (By.cssSelector ("#datepicker input:last-child"));
        js.executeScript ("arguments[0].setAttribute('value', '04/10/2024')", endDateField);

        final String toDateFieldValue = endDateField.getAttribute ("value");
        assertEquals (toDateFieldValue, "04/10/2024");
        this.status = "passed";
    }

    @Test
    public void testJQueryDatePickerSelection () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/jquery-date-picker-demo");

        final JQueryDatePickerPage jQueryDatePickerPage = new JQueryDatePickerPage (this.driver);
        jQueryDatePickerPage.selectFromDate ("Oct", "19");
        assertEquals (jQueryDatePickerPage.getFromDateValue (), "10/19/2025");

        jQueryDatePickerPage.selectToDate ("Dec", "9");
        assertEquals (jQueryDatePickerPage.getToDateValue (), "12/09/2025");
        this.status = "passed";
    }

    @Test
    public void testJQueryDatePickerUsingSendKeys () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/jquery-date-picker-demo");
        final WebElement fromDateField = this.driver.findElement (By.id ("from"));
        fromDateField.sendKeys ("09/11/2024");
        final String fromDateValue = this.driver.findElement (By.id ("from"))
            .getAttribute ("value");
        assertEquals (fromDateValue, "09/11/2024");
        this.status = "passed";
    }
}
