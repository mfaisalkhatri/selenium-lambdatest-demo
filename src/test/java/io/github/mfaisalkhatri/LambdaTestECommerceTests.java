package io.github.mfaisalkhatri;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LambdaTestECommerceTests extends BaseTest{


    @DataProvider
    public Iterator<Object[]> getLoginData() {
        List<Object[]> loginData = new ArrayList<>();
        loginData.add(new Object[] {"david.thomson@gmail.com", "Password",false});
        loginData.add(new Object[] {"david.thomson@gmail.com", " ",false});
        loginData.add(new Object[] {"david.thomson@gmail.com", "Secret@123",true});
        return loginData.iterator();
    }

    @Test(dataProvider =  "getLoginData")
    public void loginTests () {
        this.driverManager.getDriver().navigate().to("https://ecommerce-playground.lambdatest.io/index.php?route=account/login");
    }


}
