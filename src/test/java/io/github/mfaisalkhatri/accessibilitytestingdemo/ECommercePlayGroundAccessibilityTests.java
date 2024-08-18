package io.github.mfaisalkhatri.accessibilitytestingdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
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

public class ECommercePlayGroundAccessibilityTests {


    private RemoteWebDriver driver;

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
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("127.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Automated Accessibility Testing With Selenium");
        ltOptions.put("build", "LambdaTest Selenium Playground");
        ltOptions.put("name", "Accessibility test");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");
        ltOptions.put("accessibility", true);
        ltOptions.put("accessibility.wcagVersion", "wcag21");
        ltOptions.put("accessibility.bestPractice", false);
        ltOptions.put("accessibility.needsReview", true);

        browserOptions.setCapability("LT:Options", ltOptions);

        return browserOptions;
    }

    @Test(priority = 1)
    public void testNavigationToLoginPage() {
        driver.get("https://ecommerce-playground.lambdatest.io/");
        WebElement myAccountLink = driver.findElement(By.cssSelector("#widget-navbar-217834 > ul > li:nth-child(6) > a"));
        Actions actions = new Actions(driver);
        actions.moveToElement(myAccountLink).build().perform();
        WebElement loginLink = driver.findElement(By.linkText("Login"));
        loginLink.click();

        String pageHeaderText = driver.findElement(By.cssSelector("#content > div > div:nth-child(2) > div h2")).getText();
        assertEquals(pageHeaderText, "Returning Customer");
    }

    @Test(priority = 2)
    public void testLoginFunction() {
        WebElement emailAddressField = driver.findElement(By.id("input-email"));
        emailAddressField.sendKeys("davidJacob@demo.com");
        WebElement passwordField = driver.findElement(By.id("input-password"));
        passwordField.sendKeys("Password123");
        WebElement loginBtn = driver.findElement(By.cssSelector("input.btn-primary"));
        loginBtn.click();

        WebElement myAccountLink = driver.findElement(By.cssSelector("#widget-navbar-217834 > ul > li:nth-child(6) > a"));
        Actions actions = new Actions(driver);
        actions.moveToElement(myAccountLink).build().perform();
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        assertTrue(logoutLink.isDisplayed());
    }

    @AfterTest
    public void tearDown() {
        this.driver.quit();
    }
}
