package io.github.mfaisalkhatri;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static org.openqa.selenium.support.locators.RelativeLocator.with;
import static org.testng.Assert.assertEquals;

public class RelativeLocatorTests {
    WebDriver driver;
    String username = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
    String accesskey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
    String gridURL = "@hub.lambdatest.com/wd/hub";

    @BeforeTest
    public void setup() throws MalformedURLException {
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("122.0");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Selenium Locator Demo");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        ltOptions.put("build", "Demonstration: Selenium Locator Demo on LambdaTest");
        browserOptions.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), browserOptions);

    }

    @Test
    public void testRelativeLocator() {

        driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=product/manufacturer/info&manufacturer_id=8");


        WebElement iMac = driver.findElement(By.linkText("iMac"));
        WebElement iMacPriceViaBelow = driver.findElement(with(By.cssSelector("div.price> span")).below(iMac));
        assertEquals(iMacPriceViaBelow.getText(), "$170.00");

        WebElement fetchToTheRight = driver.findElement(with(By.tagName("h4")).toRightOf(iMac));
        assertEquals(fetchToTheRight.getText(), "Apple Cinema 30\"");

        WebElement fetchViaAbove = driver.findElement(with(By.tagName("h4")).above(fetchToTheRight));
        assertEquals(fetchViaAbove.getText(), "iPod Nano");

        WebElement fetchViaToLeftOf = driver.findElement(with(By.tagName("h4")).toLeftOf(fetchViaAbove));
        assertEquals(fetchViaToLeftOf.getText(), "iPod Shuffle");

    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
