package com.gorest.testsuite;

import com.gorest.steps.GoRestSteps;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CRUDTest extends TestBase {
    static int userId;
    static String name = "PrimeUser"+ TestUtils.getRandomValue();
    static String email = "PrimeUser"+ TestUtils.getRandomValue()+"@gmail.com";
    static String gender = "male";
    static String status = "Active";

    @Steps
    GoRestSteps goSteps;
    @Title("This will create a new user")
    @Test
    public void test001(){
        ValidatableResponse response = goSteps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);
    }
    @Title("Verify is the user was added to the database")
    @Test
    public void test002(){
        HashMap<String, Object> userMap = goSteps.getInfoByEmail(email);
        Assert.assertThat(userMap, hasValue(email));
        userId = (int) userMap.get("id");
    }
    @Title("Update the user information and verify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";
        goSteps.updateUser(userId,name, email,gender,status)
                .log().all().statusCode(200);
        HashMap<String, Object> userMap = goSteps.getInfoByEmail(email);
        Assert.assertThat(userMap, hasValue(email));
    }
    @Title("Delete the user and verify if the user is deleted!")
    @Test
    public void test004() {
        goSteps.deleteUser(userId).statusCode(204);
        goSteps.getUserById(userId).statusCode(404);
    }
}
