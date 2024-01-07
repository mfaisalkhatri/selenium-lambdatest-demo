package io.github.mfaisalkhatri;

import io.github.mfaisalkhatri.pages.lambdatestecommerce.LoginPage;
import io.github.mfaisalkhatri.pages.lambdatestecommerce.MyAccountPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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


}
