package io.github.mfaisalkhatri.paginationdemo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SeleniumPaginationTests {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        final String userName = System.getenv("LT_USERNAME") == null ? "LT_USERNAME" : System.getenv("LT_USERNAME");
        final String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");
        final String gridUrl = "@hub.lambdatest.com/wd/hub";
//        try {
//            this.driver = new RemoteWebDriver(new URL("http://" + userName + ":" + accessKey + gridUrl), getChromeOptions());
//        } catch (final MalformedURLException e) {
//            System.out.println("Could not start the remote session on LambdaTest cloud grid");
//        }
        driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void testPagination() {
        this.driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=25_28");

        while (isNavigationPanelDisplayed()) {
            final List<WebElement> products = this.driver.findElements(By.cssSelector(".product-layout"));
            for (final WebElement product : products) {
                final WebElement productName = product.findElement(By.cssSelector(".caption h4 a"));
                System.out.println(productName.getText());
            }
            if (isNextButtonIsDisplayed()) {
                this.driver.findElement(By.linkText(">")).click();
            } else {
                break;
            }
        }
    }

    private boolean isNextButtonIsDisplayed() {
        try {
            return this.driver.findElement(By.linkText(">")).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    private boolean isNavigationPanelDisplayed() {
        try {
            return this.driver.findElement(By.cssSelector("ul.pagination")).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    @AfterTest
    public void tearDown() {
        this.driver.quit();
    }

}
