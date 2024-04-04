package io.github.mfaisalkhatri.paginationdemo.tests;

import io.github.mfaisalkhatri.paginationdemo.pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

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
    public void testProductDetailsOnAllPage() {
        this.driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=25_28");

        final ProductPage productPage = new ProductPage(this.driver);
        assertTrue(productPage.paginationDetails().contains("5 Pages"));
        productPage.printProductDetailsOnPages();
    }


    @Test
    public void testByChangingProductNumberOnPage() {
        this.driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=25_28");

        final ProductPage productPage = new ProductPage(this.driver);
        productPage.changeFilterRecord("50");
        assertTrue(productPage.paginationDetails().contains("2 Pages"));
        productPage.printProductDetailsOnPages();
    }

    @Test
    public void testSearchForProduct() {
        this.driver.get("https://ecommerce-playground.lambdatest.io/index.php?route=product/category&path=25_28");

        final ProductPage productPage = new ProductPage(this.driver);
        productPage.searchForProduct("HP LP3065");
    }

    @AfterTest
    public void tearDown() {
        this.driver.quit();
    }

}
