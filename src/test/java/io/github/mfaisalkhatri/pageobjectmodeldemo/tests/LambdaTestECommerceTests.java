package io.github.mfaisalkhatri.pageobjectmodeldemo.tests;

import static org.testng.Assert.assertEquals;

import io.github.mfaisalkhatri.pages.lambdatestecommerce.RegistrationPage;
import io.github.mfaisalkhatri.pages.lambdatestecommerce.RegistrationSuccessPage;
import org.testng.annotations.Test;

public class LambdaTestECommerceTests extends BaseTest {

    @Test
    public void testRegisterUser (final String firstName, final String lastName, final String emailId,
        final String telephoneNumber, final String password) {

        this.driver.navigate ()
            .to ("https://ecommerce-playground.lambdatest.io/index.php?route=account/register");

        final RegistrationPage registrationPage = new RegistrationPage (this.driver);
        final RegistrationSuccessPage registrationSuccessPage = registrationPage.registerUser ("John", "Doe",
            "johndoe@email.com", "00974633", "Password@321");

        assertEquals (registrationSuccessPage.getSuccessMessage (), "Your Account Has Been Created!");
    }
}
