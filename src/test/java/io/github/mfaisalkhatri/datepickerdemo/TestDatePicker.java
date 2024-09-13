package io.github.mfaisalkhatri.datepickerdemo;

import io.github.mfaisalkhatri.datepickerdemo.pages.JQueryDatePickerPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class TestDatePicker {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
//        final String userName = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
//        final String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
//        final String gridUrl = "@hub.lambdatest.com/wd/hub";
//        try {
//            this.driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + gridUrl), getChromeOptions());
//        } catch (final MalformedURLException e) {
//            System.out.println("Could not start the remote session on LambdaTest cloud grid");
//        }
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("126.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Selenium Assertions demo");
        ltOptions.put("build", "LambdaTest Selenium Playground");
        ltOptions.put("name", "Assert Not Null Test");
        ltOptions.put("w3c", true);
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
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void testJQueryDatePickerUsingSendKeys() {
        driver.get("https://www.lambdatest.com/selenium-playground/jquery-date-picker-demo");
        WebElement fromDateField = driver.findElement(By.id("from"));
        fromDateField.sendKeys("09/11/2024");
        String fromDateValue = driver.findElement(By.id("from")).getAttribute("value");
        assertEquals(fromDateValue, "09/11/2024");

    }

    @Test
    public void testBootStrapDatePickerSelection() {

    }
}
