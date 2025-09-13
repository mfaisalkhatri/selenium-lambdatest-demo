package io.github.mfaisalkhatri.jsalertdemo;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AlertTest {
    private WebDriver driver;

    @BeforeClass
    public void setup () {
        this.driver = new ChromeDriver ();
    }

    @Test
    public void testAlert () {
        this.driver = new ChromeDriver ();
        this.driver.navigate ()
            .to ("https://www.lambdatest.com/selenium-playground/javascript-alert-box-demo");
        final WebElement clickMeButton = this.driver.findElement (
            By.cssSelector ("div > div:nth-child(3) > p > button"));
        clickMeButton.click ();

        final Alert alert = this.driver.switchTo ()
            .alert ();
        final String name = "Faisal K";
        alert.sendKeys (name);
        alert.accept ();

        final String promptText = this.driver.findElement (By.id ("prompt-demo"))
            .getText ();
        assertEquals (promptText, name);
        this.driver.quit ();
    }

    @AfterClass
    public void tearDown () {
        this.driver.quit ();
    }
}