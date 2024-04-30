package io.github.mfaisalkhatri.paginationdemo.tests;

import io.github.mfaisalkhatri.paginationdemo.pages.ProductPage;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static org.testng.Assert.assertTrue;

public class SeleniumPaginationTests {

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
//        driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void testProductDetailsOnAllPage() {
        this.driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=25_28");

        final ProductPage productPage = new ProductPage(this.driver);
        assertTrue(productPage.paginationDetails().contains("5 Pages"));
        productPage.printProductDetailsOnPages();
        this.status = "passed";
    }

    @Test
    public void testFilterRecordsOnPage() {
        this.driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=25_28");

        final ProductPage productPage = new ProductPage(this.driver);
        productPage.changeFilterRecord("50");
        assertTrue(productPage.paginationDetails().contains("2 Pages"));
        this.status = "passed";
    }

    @Test
    public void testSearchForProduct() {
        this.driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=25_28");

        final ProductPage productPage = new ProductPage(this.driver);
        productPage.searchForProduct("HP LP3065");
        this.status = "passed";

    }

    @Test
    public void testPageNavigation() {
        this.driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=25_28");

        final ProductPage productPage = new ProductPage(this.driver);
        productPage.navigateToLastPage();
        assertTrue(productPage.paginationDetails().contains("Showing 61 to 75 of 75"));

        productPage.navigateToFirstPage();
        assertTrue(productPage.paginationDetails().contains("Showing 1 to 15 of 75"));
        this.status = "passed";
    }

    public ChromeOptions getChromeOptions() {
        final var browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("latest");
        final HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("project", "Pagination tests - LambdaTest e-commerce website");
        ltOptions.put("build", "Automating pagination of LambdaTest e-commerce website");
        ltOptions.put("name", "Pagination tests - LambdaTest e-commerce website");
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