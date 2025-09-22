package io.github.mfaisalkhatri;

import static org.testng.Assert.assertEquals;

import io.github.mfaisalkhatri.pages.seleniumplayground.FormDemoPage;
import io.github.mfaisalkhatri.pages.seleniumplayground.MainPage;
import org.testng.annotations.Test;

public class SeleniumPlaygroundDemoTests extends BaseTest {

    @Test
    public void testInputForm () {
        this.driverManager.getDriver ()
            .navigate ()
            .to ("https://www.lambdatest.com/selenium-playground/");

        final var mainPage = new MainPage (this.driverManager.getDriver ());
        mainPage.clickOnLink ("Input Form Submit");

        final var formDemoPage = new FormDemoPage (this.driverManager.getDriver ());
        formDemoPage.fillForm ("Faisal", "faisal@gmail.com", "Pass@111", "LambdaTest", "https://www.lambdatest.com",
            "India", "Mumbai", "Sector 22, Lane 1", "Landmark zone", "Maharashtra", "400001");
        assertEquals (formDemoPage.successMessage (), "Thanks for contacting us, we will get back to you shortly.");
    }
}
