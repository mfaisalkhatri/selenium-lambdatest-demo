package io.github.mfaisalkhatri.threadsleepdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class ThreadSleepDemoTests {

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
    }

    @Test
    public void testLogin() throws InterruptedException {
        this.driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");

        final WebElement emailAddress = this.driver.findElement(By.id("input-email"));
        emailAddress.sendKeys("david.thomson@gmail.com");

        final WebElement password = this.driver.findElement(By.id("input-password"));
        password.sendKeys("Secret@123");

        final WebElement loginBtn = this.driver.findElement(By.cssSelector("input.btn-primary"));
        loginBtn.click();

        Thread.sleep(3000);

        final String myAccountHeaderText = this.driver.findElement(By.cssSelector("#content h2")).getText();
        assertEquals(myAccountHeaderText, "My Account");
    }

    @Test
    public void testWebsiteNavigation() throws InterruptedException {
        this.driver.get("https://ecommerce-playground.lambdatest.io/");
        Thread.sleep(1000);

        this.driver.findElement(By.linkText("Shop by Category")).click();

        Thread.sleep(1000);

        this.driver.findElement(By.cssSelector(".entry-component .entry-widget nav.navbar ul li:nth-child(5) a")).click();

        Thread.sleep(1000);
    }

    @Test
    public void testWebsiteNavigationWithExplicitWait() {
        this.driver.get("https://ecommerce-playground.lambdatest.io/");

        final WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Shop by Category"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".entry-component .entry-widget nav.navbar ul li:nth-child(5) a"))).click();
    }

    @Test
    public void testLoginWithSmartWait() {
        this.driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");

        final WebElement emailAddress = this.driver.findElement(By.id("input-email"));
        emailAddress.sendKeys("david.thomson@gmail.com");

        final WebElement password = this.driver.findElement(By.id("input-password"));
        password.sendKeys("Secret@123");

        final WebElement loginBtn = this.driver.findElement(By.cssSelector("input.btn-primary"));
        loginBtn.click();

        final String myAccountHeaderText = this.driver.findElement(By.cssSelector("#content h2")).getText();
        assertEquals(myAccountHeaderText, "My Account");
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("123.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Thread.sleep Demo on Cloud");
        ltOptions.put("build", "LambdaTest e-commerce website test");
        ltOptions.put("name", "Login test with SmartWait");
        ltOptions.put("w3c", true);
        ltOptions.put("smartWait", 20);
        ltOptions.put("plugin", "java-testNG");

        browserOptions.setCapability("LT:Options", ltOptions);

        return browserOptions;
    }

    @AfterTest
    public void tearDown() {
        this.driver.quit();
    }

}