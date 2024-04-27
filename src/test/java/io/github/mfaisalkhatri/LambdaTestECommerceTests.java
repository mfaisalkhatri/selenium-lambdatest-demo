package io.github.mfaisalkhatri;

import io.github.mfaisalkhatri.pages.lambdatestecommerce.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class LambdaTestECommerceTests extends BaseTest{


    @DataProvider
    public Iterator<Object[]> getLoginData() {
        List<Object[]> loginData = new ArrayList<>();
        loginData.add(new Object[] {"david.thomson@gmail.com","Password",false});
        loginData.add(new Object[] {"david.thomson@gmail.com","",false});
        loginData.add(new Object[] {"david.thomson@gmail.com","Secret@123",true});
        return loginData.iterator();
    }

    @Test(dataProvider =  "getLoginData")
    public void testLogin (String email, String password, boolean isValidUser) {
        this.driverManager.getDriver().navigate().to("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");

        LoginPage loginPage = new LoginPage(this.driverManager.getDriver());
        loginPage.performLogin(email, password, isValidUser);
    }

    @DataProvider
    public Iterator<Object[]> getRegistrationData() {
        List<Object[]> registerData = new ArrayList<>();
        registerData.add(new Object[]{"David", "Jones", "davidj@demo.com", "8976783849", "Password123"});
        return registerData.iterator();
    }

    @Test(dataProvider = "getRegistrationData")
    public void testRegisterUser(String firstName, String lastName, String emailId, String telephoneNumber, String password) {

        this.driverManager.getDriver().navigate().to("https://ecommerce-playground.lambdatest.io/index.php?route=account/register");

        RegistrationPage registrationPage = new RegistrationPage(this.driverManager.getDriver());
        RegistrationSuccessPage registrationSuccessPage = registrationPage.registerUser(firstName, lastName, emailId, telephoneNumber, password);

        assertEquals(registrationSuccessPage.getSuccessMessage(), "Your Account Has Been Created!");

    }

    @Test
    public void testAddProductToCart() throws InterruptedException {
        this.driverManager.getDriver().navigate().to("https://ecommerce-playground.lambdatest.io/");
        MainPage mainPage = new MainPage(this.driverManager.getDriver());
        ProductPage productPage = mainPage.searchProduct("Macbook");
        productPage.adProductToCart();
        final String expectedMessage = "{0}\n{1}\n{2}\n{3}\n{4}";
        assertEquals(productPage.successMessageTest(), MessageFormat.format(expectedMessage, "Success: You have added", "MacBook", "to your",
                "shopping cart", "!"));
        assertEquals(mainPage.cartItemNumber(), "1");
    }
}