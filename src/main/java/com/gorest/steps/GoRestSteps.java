package com.gorest.steps;

import com.gorest.constants.EndPoints;
import com.gorest.model.GoRestPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class GoRestSteps {
    @Step("Creating student with name : {0}, email : {1}, gender : {2}, status : {3}")
    public ValidatableResponse createUser (String name, String email, String gender, String status){
        GoRestPojo goRestPojo = new GoRestPojo();
        goRestPojo.setName(name);
        goRestPojo.setEmail(email);
        goRestPojo.setGender(gender);
        goRestPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer a58066525e0288b799ff232d3b04af9c015d09d7207a3585eb51c1acc8ba118b")
                .body(goRestPojo)
                .when()
                .post()
                .then();
    }

    @Step("Getting user information from the email : {0}")
    public HashMap<String,Object> getInfoByEmail(String email){
        String p1 = "findAll{it.email == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .header("Authorization","Bearer a58066525e0288b799ff232d3b04af9c015d09d7207a3585eb51c1acc8ba118b")
                .get()
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("findAll{it.email == '" + email + "'}.get(0)");
    }
    @Step("Updating user with user_id: {0}, name : {1}, email : {2}, gender : {3}, status : {4}")

    public ValidatableResponse updateUser(int userId, String name, String email, String gender, String status){
        GoRestPojo goRestPojo = new GoRestPojo();
        goRestPojo.setName(name);
        goRestPojo.setEmail(email);
        goRestPojo.setGender(gender);
        goRestPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .header("Authorization","Bearer a58066525e0288b799ff232d3b04af9c015d09d7207a3585eb51c1acc8ba118b")
                .header("Content-Type", "application/json")
                .pathParam("user_id",userId)
                .body(goRestPojo)
                .when()
                .put(EndPoints.UPDATE_A_USER_BY_ID)
                .then();
    }
    @Step("Deleting user information with id: {0}")
    public ValidatableResponse deleteUser(int userId) {
        return SerenityRest.given().log().all()
                .header("Authorization","Bearer a58066525e0288b799ff232d3b04af9c015d09d7207a3585eb51c1acc8ba118b")
                .pathParam("user_id", userId)
                .when()
                .delete(EndPoints.DELETE_A_USER_BY_ID)
                .then();
    }

    @Step("Getting user information with studentId: {0}")
    public ValidatableResponse getUserById(int userId){
        return SerenityRest.given().log().all()
                .header("Authorization","Bearer a58066525e0288b799ff232d3b04af9c015d09d7207a3585eb51c1acc8ba118b")
                .pathParam("user_id", userId)
                .when()
                .get(EndPoints.GET_USER_BY_ID)
                .then();
    }
}
