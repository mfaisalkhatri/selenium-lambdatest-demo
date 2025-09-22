package io.github.mfaisalkhatri.clickdemo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ClickDemoTests {

    private WebDriver driver;

    @Test
    public void checkboxDemoTest () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/checkbox-demo");
        final WebElement checkboxOne = this.driver.findElement (By.id ("isAgeSelected"));
        checkboxOne.click ();
        assertTrue (checkboxOne.isSelected ());
        final String selectedResult = this.driver.findElement (By.id ("txtAge"))
            .getText ();
        assertEquals (selectedResult, "Checked");
    }

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("122.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Selenium Click Demo");
        ltOptions.put ("build", "Selenium Tests");
        ltOptions.put ("name", "Selenium Click method tests");
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");

        browserOptions.setCapability ("LT:Options", ltOptions);

        return browserOptions;

    }

    @Test
    public void radioButtonDemoTest () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/radiobutton-demo");
        final WebElement maleRadioBtn = this.driver.findElement (
            By.cssSelector ("[name=\"optradio\"][value=\"Male\"]"));
        maleRadioBtn.click ();
        final WebElement getValueBtn = this.driver.findElement (By.id ("buttoncheck"));
        getValueBtn.click ();
        final String resultText = this.driver.findElement (By.cssSelector ("p.text-black.radiobutton"))
            .getText ();
        assertEquals (resultText, "Radio button 'Male' is checked");
    }

    @BeforeTest
    public void setup () {
        final String userName = System.getenv ("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv ("LT_USERNAME");
        final String accessKey = System.getenv ("LT_ACCESS_KEY") == null
                                 ? "LT_ACCESS_KEY"
                                 : System.getenv ("LT_ACCESS_KEY");
        final String gridUrl = "@hub.lambdatest.com/wd/hub";
        try {
            this.driver = new RemoteWebDriver (new URL ("https://" + userName + ":" + accessKey + gridUrl),
                getChromeOptions ());
        } catch (final MalformedURLException e) {
            System.out.println ("Could not start the remote session on LambdaTest cloud grid");
        }
        //        driver = new ChromeDriver();
        this.driver.manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (20));
        this.driver.manage ()
            .timeouts ()
            .pageLoadTimeout (Duration.ofSeconds (20));
    }

    @AfterTest
    public void tearDown () {
        this.driver.quit ();
    }

    @Test
    public void testClickAndHold () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/drag-drop-range-sliders-demo");
        final WebElement sliderOne = this.driver.findElement (By.cssSelector ("#slider1 input.sp__range"));

        final Actions actions = new Actions (this.driver);
        actions.moveToElement (sliderOne)
            .clickAndHold ()
            .moveByOffset (100, 283)
            .build ()
            .perform ();

        final String outputRange = this.driver.findElement (By.id ("range"))
            .getText ();
        assertEquals (outputRange, "71");
    }

    @Test
    public void testClickUsingCoOrdinates () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/simple-form-demo");
        final WebElement firstValueField = this.driver.findElement (By.id ("sum1"));
        firstValueField.sendKeys ("5");

        final WebElement secondValueField = this.driver.findElement (By.id ("sum2"));
        secondValueField.sendKeys ("8");

        final WebElement getSumBtn = this.driver.findElement (By.cssSelector ("#gettotal > button"));

        final Actions actions = new Actions (this.driver);
        actions.moveToElement (getSumBtn, 10, 10)
            .click ()
            .build ()
            .perform ();
        final String resultText = this.driver.findElement (By.id ("addmessage"))
            .getText ();

        assertEquals (resultText, "13");

    }

    @Test
    public void testDoubleClick () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/checkbox-demo");
        final WebElement checkboxOne = this.driver.findElement (By.id ("isAgeSelected"));

        final Actions actions = new Actions (this.driver);
        actions.doubleClick (checkboxOne)
            .build ()
            .perform ();

        assertFalse (checkboxOne.isSelected ());
    }

    @Test
    public void testDragAndDrop () {

        this.driver.get ("https://www.lambdatest.com/selenium-playground/drag-and-drop-demo");
        final WebElement draggableOne = this.driver.findElement (By.cssSelector ("#todrag > span"));
        final WebElement dropHere = this.driver.findElement (By.id ("mydropzone"));

        final Actions actions = new Actions (this.driver);
        actions.dragAndDrop (draggableOne, dropHere)
            .build ()
            .perform ();

        final WebElement droppedList = this.driver.findElement (By.cssSelector ("#droppedlist > span"));
        assertEquals (droppedList.getText (), "Draggable 1");

    }

    @Test
    public void testMouseHover () {

        this.driver.get ("https://www.lambdatest.com/selenium-playground/hover-demo");
        final WebElement hoverMeGreenBtn = this.driver.findElement (By.className ("bg-green-100"));
        final String bgColorBase = hoverMeGreenBtn.getCssValue ("background-color");
        assertEquals (bgColorBase, "rgba(40, 167, 69, 1)");

        final WebElement submitBtn = this.driver.findElement (By.xpath ("//*[@id=\"seleniumform\"]/div[6]/button"));
        final Actions actions = new Actions (this.driver);
        actions.moveToElement (hoverMeGreenBtn)
            .pause (2000)
            .build ()
            .perform ();
        final String bgColorNew = hoverMeGreenBtn.getCssValue ("background-color");
        assertEquals (bgColorNew, "rgba(255, 255, 255, 1)");
    }

    @Test
    public void testPageLinks () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/");

        final WebDriverWait wait = new WebDriverWait (this.driver, Duration.ofSeconds (20));

        List<WebElement> demoPageLinks = this.driver.findElements (By.cssSelector (".container__selenium ul li a"));

        for (int i = 0; i < demoPageLinks.size (); i++) {

            final String pageLinkName = demoPageLinks.get (i)
                .getText ();
            System.out.println (pageLinkName);

            if (!(pageLinkName.equals ("Nested Frames"))) {

                final WebElement link = wait.until (ExpectedConditions.elementToBeClickable (demoPageLinks.get (i)));
                link.click ();

                final WebElement pageHeader = wait.until (
                    ExpectedConditions.presenceOfElementLocated (By.tagName ("h1")));
                final String pageHeaderText = pageHeader.getText ();

                System.out.println ("Visited: " + pageHeaderText);

                this.driver.navigate ()
                    .back ();
                demoPageLinks = this.driver.findElements (By.cssSelector (".container__selenium ul li a"));
            }
        }
    }

    @Test
    public void testScrollUsingJSExecutor () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/hover-demo");
        final WebElement zoomInImage = this.driver.findElement (By.cssSelector (".s__column2 .image-card img"));
        final JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript ("arguments[0].scrollIntoView();", zoomInImage);
        final String imageText = this.driver.findElement (By.cssSelector (".p-15 h2:nth-child(8)"))
            .getText ();
        assertEquals (imageText, "Zoom In");
    }

    @Test
    public void testSimpleForm () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/simple-form-demo");
        final String message = "This is a sample message";
        final WebElement enterMessage = this.driver.findElement (By.id ("user-message"));
        enterMessage.sendKeys (message);
        final WebElement getCheckedValueBtn = this.driver.findElement (By.id ("showInput"));
        getCheckedValueBtn.click ();
        final String yourMessageText = this.driver.findElement (By.id ("message"))
            .getText ();
        assertEquals (yourMessageText, message);

    }

}
