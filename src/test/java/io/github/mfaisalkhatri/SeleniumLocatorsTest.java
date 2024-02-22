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
import java.util.List;

import static org.testng.Assert.assertEquals;

public class SeleniumLocatorsTest {

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
    public void testLinkText() {
        driver.get("https://www.lambdatest.com/selenium-playground/");
        driver.findElement(By.linkText("Ajax Form Submit")).click();
        String header = driver.findElement(By.tagName("h1")).getText();
        assertEquals(header, "Form Submit Demo");

    }

    @Test
    public void testPartialLinkText() {

        driver.get("https://www.lambdatest.com/selenium-playground/");
        driver.findElement(By.partialLinkText("Healing")).click();
        String header = driver.findElement(By.tagName("h1")).getText();
        assertEquals(header, "Auto Healing");


    }

    @Test
    public void testTagNames() {

        driver.get("https://www.lambdatest.com/selenium-playground/");
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

    }

    @Test
    public void testXpath() throws InterruptedException {
        driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");
        driver.findElement(By.xpath("//input[@id=\"input-email\" and @name=\"email\"]")).sendKeys("faisal.k@gmail.com");
        //driver.findElement(By.xpath("//input[@id=\"input-email\" or @name=\"email\"]")).sendKeys("faisal.k@gmail.com");
        driver.findElement(By.xpath("//input[starts-with(@name,'pass')]")).sendKeys("Password");

        driver.get("https://www.lambdatest.com/selenium-playground/auto-healing");
        driver.findElement(By.xpath("//button[text()=\"Submit\"]"));
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }


}
