package io.github.mfaisalkhatri.javascriptexecutordemo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class TestJavaScriptExecutor {

    private RemoteWebDriver driver;
    private String status = "failed";

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
        driver = new ChromeDriver();
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    @Test
    public void testJavaScriptExecutorCommand() {
        driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('input-email').value='davidjacob@demo.com'");
        js.executeScript("document.getElementById('input-password').value='Password123'");

        WebElement loginBtn = driver.findElement(By.cssSelector("input.btn"));
        js.executeScript("arguments[0].click();", loginBtn);

        String titleText = js.executeScript("return document.title;").toString();
        System.out.println("Page Title is: " + titleText);

        String domainName = js.executeScript("return document.domain;").toString();
        System.out.println("Domain is: " + domainName);

        String myAccountHeader = driver.findElement(By.cssSelector("#content h2")).getText();

        assertEquals(myAccountHeader, "My Account");
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("127.0");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "LambdaTest E-Commerce Playground");
        ltOptions.put("build", "LambdaTest E-Commerce Login page");
        ltOptions.put("name", "JavaScriptExecutor Selenium Tests");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-testNG");

        browserOptions.setCapability("LT:Options", ltOptions);

        return browserOptions;

    }


    @AfterTest
    public void tearDown() {
        this.driver.executeScript("lambda-status=" + this.status);
        this.driver.quit();
    }


}
