package io.github.mfaisalkhatri.threadsleepdemo;

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

import static org.testng.Assert.assertEquals;

public class ThreadSleepDemoTests {

    private WebDriver driver;

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

        final String myAccountHeaderText = driver.findElement(By.cssSelector("#content h2")).getText();
        assertEquals(myAccountHeaderText, "My Account");
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("122.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Thread.sleep Demo on Cloud");
        ltOptions.put("build", "LambdaTest e-commerce website test");
        ltOptions.put("name", "Thread.sleep demo test");
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
