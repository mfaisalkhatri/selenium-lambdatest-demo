package io.github.mfaisalkhatri;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected DriverManager driverManager;

    @BeforeClass
    @Parameters("browser")
    public void setup(final String browser) {
        this.driverManager = new DriverManager();
        this.driverManager.createDriver(Browsers.valueOf(browser.toUpperCase()));
    }


    @AfterClass
    public void tearDown() {
        this.driverManager.quitDriver();
    }
}
