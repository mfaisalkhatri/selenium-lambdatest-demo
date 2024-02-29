package io.github.mfaisalkhatri.clickdemo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ClickDemoTests {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        String userName = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
        String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
        String gridUrl = "@hub.lambdatest.com/wd/hub";
        try {
            this.driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + gridUrl), getChromeOptions());
        } catch (final MalformedURLException e) {
            System.out.println("Could not start the remote session on LambdaTest cloud grid");
        }
        //driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testSimpleForm() {
        this.driver.get("https://www.lambdatest.com/selenium-playground/simple-form-demo");
        final String message = "This is a sample message";
        final WebElement enterMessage = this.driver.findElement(By.id("user-message"));
        enterMessage.sendKeys(message);
        final WebElement getCheckedValueBtn = this.driver.findElement(By.id("showInput"));
        getCheckedValueBtn.click();
        String yourMessageText = this.driver.findElement(By.id("message")).getText();
        assertEquals(yourMessageText, message);

    }
    @Test
    public void checkboxDemoTest() {
        this.driver.get("https://www.lambdatest.com/selenium-playground/checkbox-demo");
        final WebElement checkboxOne = this.driver.findElement(By.id("isAgeSelected"));
        checkboxOne.click();
        assertTrue(checkboxOne.isSelected());
        final String selectedResult = this.driver.findElement(By.id("txtAge")).getText();
        assertEquals(selectedResult, "Checked");
    }

    @Test
    public void radioButtonDemoTest() {
        driver.get("https://www.lambdatest.com/selenium-playground/radiobutton-demo");
        final WebElement maleRadioBtn = driver.findElement(By.cssSelector("[name=\"optradio\"][value=\"Male\"]"));
        maleRadioBtn.click();
        final WebElement getValueBtn = driver.findElement(By.id("buttoncheck"));
        getValueBtn.click();
        String resultText = driver.findElement(By.cssSelector("p.text-black.radiobutton")).getText();
        assertEquals(resultText,"Radio button 'Male' is checked");
    }

    @Test
    public void testMouseHover() {

        driver.get("https://www.lambdatest.com/selenium-playground/hover-demo");
        WebElement hoverMeGreenBtn = driver.findElement(By.className("bg-green-100"));
        String bgColorBase = hoverMeGreenBtn.getCssValue("background-color");
        assertEquals(bgColorBase, "rgba(40, 167, 69, 1)");

        Actions actions = new Actions(driver);
        actions.moveToElement(hoverMeGreenBtn).pause(2000).build().perform();
        String bgColorNew = hoverMeGreenBtn.getCssValue("background-color");
        assertEquals(bgColorNew, "rgba(255, 255, 255, 1)");
    }

    @Test
    public void testScrollUsingJSExecutor ()  {
        driver.get("https://www.lambdatest.com/selenium-playground/hover-demo");
        WebElement zoomInImage = driver.findElement(By.cssSelector(".s__column2 .image-card img"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", zoomInImage);
        String imageText = driver.findElement(By.cssSelector(".p-15 h2:nth-child(8)")).getText();
        assertEquals(imageText, "Zoom In");
    }
    @Test
    public void testClickUsingCoOrdinates () {
        driver.get("https://www.lambdatest.com/selenium-playground/simple-form-demo");
        WebElement firstValueField = driver.findElement(By.id("sum1"));
        firstValueField.sendKeys("5");

        WebElement secondValueField = driver.findElement(By.id("sum2"));
        secondValueField.sendKeys("8");

        WebElement getSumBtn = driver.findElement(By.cssSelector("#gettotal > button"));

        Actions actions = new Actions(driver);
        actions.moveToElement(getSumBtn,10,10).click().build().perform();
        String resultText = driver.findElement(By.id("addmessage")).getText();

        assertEquals(resultText, "13");

    }

    @Test
    public void testDoubleClick()  {
        this.driver.get("https://www.lambdatest.com/selenium-playground/checkbox-demo");
        final WebElement checkboxOne = this.driver.findElement(By.id("isAgeSelected"));

        Actions actions = new Actions(driver);
        actions.doubleClick(checkboxOne).build().perform();

        assertTrue(!checkboxOne.isSelected());
    }

    @Test
    public void testClickAndHold () {
        driver.get("https://www.lambdatest.com/selenium-playground/drag-drop-range-sliders-demo");
        WebElement sliderOne = driver.findElement(By.cssSelector("#slider1 input.sp__range"));

        Actions actions = new Actions(driver);
        actions.moveToElement(sliderOne).clickAndHold().moveByOffset(100,283).build().perform();

        String outputRange = driver.findElement(By.id("range")).getText();
        assertEquals(outputRange,"71");
    }

    

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("122.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Selenium Click Demo");
        ltOptions.put("build", "Selenium Tests");
        ltOptions.put("name", "Selenium Click method tests");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");

        browserOptions.setCapability("LT:Options", ltOptions);

        return browserOptions;

    }

    @AfterTest
    public void tearDown() {
        this.driver.quit();
    }

}
