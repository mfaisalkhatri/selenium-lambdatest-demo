package io.github.mfaisalkhatri.paginationdemo.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.json.JsonOutput;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductPage {

    private final WebDriver driver;

    public ProductPage(final WebDriver driver) {
        this.driver = driver;
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

    public void printProductDetailsOnPages() {
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

    public void changeFilterRecord(final String recordNumber) {
        final WebElement recordFilter = this.driver.findElement(By.cssSelector(".custom-select"));
        final Select select = new Select(recordFilter);
        select.selectByVisibleText(recordNumber);

    }

    public String paginationDetails() {
        return this.driver.findElement(By.cssSelector(".content-pagination .text-right")).getText();
    }

    public void searchForProduct(final String nameOfProduct) {
        boolean found = false;
        while (!found) {
            final List<WebElement> products = this.driver.findElements(By.cssSelector(".product-layout"));
            for (final WebElement product : products) {
                final String productName = product.findElement(By.cssSelector(".caption h4 a")).getText();
                if (nameOfProduct.equals(productName)) {
                    System.out.println("Product found!");
                    found = true;
                    break;
                }
            }
            if (isNextButtonIsDisplayed()) {
                this.driver.findElement(By.linkText(">")).click();
            } else {
                System.out.println("Product Not Found!");
                break;
            }
        }
    }
}



