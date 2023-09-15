package io.github.mfaisalkhatri;

import io.github.mfaisalkhatri.pages.FormDemoPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SeleniumDemoTests extends BaseTest {

    @Test
    public void testInputForm() {
        this.driverManager.getDriver().navigate().to("https://www.lambdatest.com/selenium-playground/input-form-demo");
        final var formDemoPage = new FormDemoPage(this.driverManager.getDriver());
        formDemoPage.fillForm("Faisal", "faisal@gmail.com", "Pass@111", "LambdaTest",
                "https://www.lambdatest.com", "India", "Mumbai",
                "Sector 22, Lane 1", "Landmark zone",
                "Maharashtra", "400001");
        assertEquals(formDemoPage.successMessage(), "Thanks for contacting us, we will get back to you shortly.");

    }
}
