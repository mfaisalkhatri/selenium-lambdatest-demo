package io.github.mfaisalkhatri;

import static org.testng.Assert.assertEquals;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.mfaisalkhatri.pages.lambdatestecommerce.LoginPage;
import io.github.mfaisalkhatri.pages.lambdatestecommerce.MainPage;
import io.github.mfaisalkhatri.pages.lambdatestecommerce.ProductPage;
import io.github.mfaisalkhatri.pages.lambdatestecommerce.RegistrationPage;
import io.github.mfaisalkhatri.pages.lambdatestecommerce.RegistrationSuccessPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LambdaTestECommerceTests extends BaseTest {

    @DataProvider
    public Iterator<Object[]> getLoginData () {
        final List<Object[]> loginData = new ArrayList<> ();
        loginData.add (new Object[] { "david.thomson@gmail.com", "Password", false });
        loginData.add (new Object[] { "david.thomson@gmail.com", "", false });
        loginData.add (new Object[] { "david.thomson@gmail.com", "Secret@123", true });
        return loginData.iterator ();
    }

    @DataProvider
    public Iterator<Object[]> getRegistrationData () {
        final List<Object[]> registerData = new ArrayList<> ();
        registerData.add (new Object[] { "David", "Jacob", "davidjacob@demo.com", "8976783849", "Password123" });
        return registerData.iterator ();
    }

    @Test
    public void testAddProductToCart () {

        final MainPage mainPage = new MainPage (this.driverManager.getDriver ());
        final ProductPage productPage = mainPage.searchProduct ("Macbook");
        productPage.adProductToCart ();
        final String expectedMessage = "{0}\n{1}\n{2}\n{3}\n{4}";
        assertEquals (productPage.successMessageTest (),
            MessageFormat.format (expectedMessage, "Success: You have added", "MacBook", "to your", "shopping cart",
                "!"));
        assertEquals (mainPage.cartItemNumber (), "1");
    }

    @Test (dataProvider = "getLoginData")
    public void testLogin (final String email, final String password, final boolean isValidUser) {
        this.driverManager.getDriver ()
            .navigate ()
            .to ("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");

        final LoginPage loginPage = new LoginPage (this.driverManager.getDriver ());
        loginPage.performLogin (email, password, isValidUser);
    }

    @Test (dataProvider = "getRegistrationData")
    public void testRegisterUser (final String firstName, final String lastName, final String emailId,
        final String telephoneNumber, final String password) {

        this.driverManager.getDriver ()
            .navigate ()
            .to ("https://ecommerce-playground.lambdatest.io/index.php?route=account/register");

        final RegistrationPage registrationPage = new RegistrationPage (this.driverManager.getDriver ());
        final RegistrationSuccessPage registrationSuccessPage = registrationPage.registerUser (firstName, lastName,
            emailId, telephoneNumber, password);

        assertEquals (registrationSuccessPage.getSuccessMessage (), "Your Account Has Been Created!");
    }
}