package io.github.mfaisalkhatri.pageobjectmodeldemo.tests;

import static org.testng.Assert.assertEquals;

import io.github.mfaisalkhatri.pageobjectmodeldemo.pages.LoginPage;
import io.github.mfaisalkhatri.pageobjectmodeldemo.pages.MyAccountPage;
import io.github.mfaisalkhatri.pageobjectmodeldemo.pages.RegistrationPage;
import io.github.mfaisalkhatri.pageobjectmodeldemo.pages.RegistrationSuccessPage;
import org.testng.annotations.Test;

public class LambdaTestECommerceTests extends BaseTest {

    private static final String EMAIL    = "johndoe877@email.com";
    private static final String PASSWORD = "Password@321";

    @Test
    public void testRegisterUser () {
        this.driver.navigate ()
            .to ("https://ecommerce-playground.lambdatest.io/index.php?route=account/register");

        final RegistrationPage registrationPage = new RegistrationPage (this.driver);
        final RegistrationSuccessPage registrationSuccessPage = registrationPage.registerUser ("John", "Doe", EMAIL,
            "00974633", PASSWORD);

        assertEquals (registrationSuccessPage.getSuccessMessage (), "Your Account Has Been Created!");
    }

    @Test
    public void testLogin () {
        this.driver.navigate ()
            .to ("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");

        final LoginPage loginPage = new LoginPage (this.driver);
        final MyAccountPage myAccountPage = loginPage.performLogin (EMAIL, PASSWORD);
        assertEquals (myAccountPage.getPageTitle (), "My Account");
    }
}