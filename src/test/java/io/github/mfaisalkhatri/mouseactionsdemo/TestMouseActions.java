package io.github.mfaisalkhatri.mouseactionsdemo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestMouseActions {

    private static final String GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String LT_ACCESS_KEY = System.getenv ("LT_ACCESS_KEY");
    private static final String LT_USERNAME   = System.getenv ("LT_USERNAME");

    private RemoteWebDriver driver;
    private String          status = "failed";

    public ChromeOptions getChromeOptions () {
        final var browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("127.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object> ();
        ltOptions.put ("project", "Mouse Actions Demo");
        ltOptions.put ("build", "LambdaTest Selenium Playground");
        ltOptions.put ("name", "Perform Mouse Actions using Selenium WebDriver");
        ltOptions.put ("w3c", true);
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
            .implicitlyWait (Duration.ofSeconds (2));
    }

    @AfterTest
    public void tearDown () {

        this.driver.executeScript ("lambda-status=" + this.status);
        this.driver.quit ();
    }

    @Test
    public void testContextClickAction () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/context-menu");
        final WebElement contextClickBox = this.driver.findElement (By.id ("hot-spot"));
        final Actions actions = new Actions (this.driver);
        actions.contextClick (contextClickBox)
            .build ()
            .perform ();

        final Alert alert = this.driver.switchTo ()
            .alert ();

        final String alertText = alert.getText ();
        assertEquals (alertText, "You selected a context menu");

        alert.accept ();
        this.status = "passed";
    }

    @Test
    public void testDoubleClickAction () {
        this.driver.get ("https://unixpapa.com/js/testmouse.html");
        final WebElement clickHereText = this.driver.findElement (By.linkText ("click here to test"));
        final Actions actions = new Actions (this.driver);
        actions.doubleClick (clickHereText)
            .build ()
            .perform ();

        final WebElement textBox = this.driver.findElement (By.tagName ("textarea"));
        final String textBoxValue = textBox.getAttribute ("value");

        assertTrue (textBoxValue.contains ("dblclick"));
        this.status = "passed";
    }

    @Test
    public void testDragDrop () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/drag-and-drop-demo");

        final WebElement draggable = this.driver.findElement (By.cssSelector ("#todrag > span:nth-child(2)"));
        final WebElement dropZone = this.driver.findElement (By.id ("mydropzone"));

        final Actions actions = new Actions (this.driver);
        actions.dragAndDrop (draggable, dropZone)
            .build ()
            .perform ();

        final String droppedItemList = this.driver.findElement (By.cssSelector ("#droppedlist span"))
            .getText ();
        assertEquals (droppedItemList, "Draggable 1");

        this.status = "passed";
    }

    @Test
    public void testDragDropApproachThree () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/drag-and-drop-demo");

        final WebElement draggable = this.driver.findElement (By.cssSelector ("#todrag > span:nth-child(2)"));

        final Actions actions = new Actions (this.driver);
        actions.dragAndDropBy (draggable, 477, 0)
            .build ()
            .perform ();

        final String droppedItemList = this.driver.findElement (By.cssSelector ("#droppedlist span"))
            .getText ();
        assertEquals (droppedItemList, "Draggable 1");

        this.status = "passed";
    }

    @Test
    public void testDragDropApproachTwo () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/drag-and-drop-demo");

        final WebElement draggable = this.driver.findElement (By.cssSelector ("#todrag > span:nth-child(2)"));
        final WebElement dropZone = this.driver.findElement (By.id ("mydropzone"));

        final Actions actions = new Actions (this.driver);
        actions.clickAndHold (draggable)
            .moveToElement (dropZone)
            .release ()
            .build ()
            .perform ();

        final String droppedItemList = this.driver.findElement (By.cssSelector ("#droppedlist span"))
            .getText ();
        assertEquals (droppedItemList, "Draggable 1");

        this.status = "passed";
    }

    @Test
    public void testMouseClickAction () {
        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final WebElement searchBox = this.driver.findElement (By.name ("search"));
        searchBox.sendKeys ("iPhone");

        final WebElement searchBtn = this.driver.findElement (By.cssSelector ("button[type=submit]"));
        searchBtn.click ();

        final String pageHeader = this.driver.findElement (By.cssSelector ("#entry_212456 h1"))
            .getText ();
        assertEquals (pageHeader, "Search - iPhone");

        this.status = "passed";
    }

    @Test
    public void testMouseHover () {
        this.driver.get ("https://ecommerce-playground.lambdatest.io/");
        final WebElement myAccountLink = this.driver.findElement (
            By.cssSelector ("#widget-navbar-217834 > ul > li:nth-child(6) > a"));
        final Actions actions = new Actions (this.driver);
        actions.moveToElement (myAccountLink)
            .build ()
            .perform ();

        final WebElement loginLink = this.driver.findElement (By.linkText ("Login"));
        loginLink.click ();

        final List<WebElement> pageHeaders = this.driver.findElements (By.tagName ("h2"));
        final String loginHeader = pageHeaders.get (1)
            .getText ();
        assertEquals (loginHeader, "Returning Customer");

        this.status = "passed";

    }

    @Test
    public void testSliderAction () {
        this.driver.get ("https://www.lambdatest.com/selenium-playground/drag-drop-range-sliders-demo");
        final WebElement slider = this.driver.findElement (By.cssSelector ("input[type='range'][value='5']"));
        final Point point = slider.getLocation ();
        final int xcord = point.getX ();
        final int ycord = point.getY ();
        System.out.println ("x: " + xcord);
        System.out.println ("y: " + ycord);

        final Actions actions = new Actions (this.driver);
        actions.clickAndHold (slider)
            .moveByOffset (-145, 0)
            .release ()
            .build ()
            .perform ();
        final String outputResult = this.driver.findElement (By.cssSelector ("output#range"))
            .getText ();

        assertEquals (outputResult, "20");
        this.status = "passed";
    }
}