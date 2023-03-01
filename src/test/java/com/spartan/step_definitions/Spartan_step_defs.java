package com.spartan.step_definitions;


import com.spartan.utilities.SpartanUtil;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.Is.is;

public class Spartan_step_defs extends SpartanUtil {

    String url = "http://3.85.201.5:8000";
    String spartanURL = "http://3.85.201.5:8000/api/spartans/{id}";
    String payLordPost = "{\n" +
            "  \"gender\": \"Male\",\n" +
            "  \"name\": \"Tom Cruise\",\n" +
            "  \"phone\": 2038238760\n" +
            "}";
    String payLordPatch = "{\n" +
            "  \"gender\": \"Female\",\n" +
            "  \"name\": \"Patched Done\",\n" +
            "  \"phone\": 2039318597\n" +
            "}";



    Response responsePost;
    Response responsePatch;
    Response responsePut;
    SpartanUtil spartanUtil = new SpartanUtil();

    @When("the user create a spartan")
    public void the_user_create_a_spartan() {
       // response = given().accept(ContentType.JSON).and().log().all()
               // .contentType(ContentType.JSON).body(payLordPost)
               // .when().post(url + "/api/spartans")
              //  .then().extract().response();

        spartanUtil.post(payLordPost,url+"/api/spartans");


    }

    @Then("verify that status code {int}")
    public void verify_that_status_code(int code) {
        //Assert.assertEquals(response.statusCode(), 201);
            Assert.assertEquals(code,postStatus(payLordPost,url+"/api/spartans"));


    }

    @Then("verify that the message is {string}")
    public void verify_that_the_message_is(String string) {
       // Assert.assertEquals(response.path("success"), string);
        Assert.assertEquals(string,message(payLordPost,url+"/api/spartans"));

    }

    @When("the user get the created spartan")
    public void the_user_get_the_created_spartan() {
       // given().accept(ContentType.JSON)
               // .when().get(url + "/api/spartans")
              //  .then().extract().jsonPath();
        get(spartanURL,1116);
    }

    @Then("verify that the name is correct")
    public void verify_that_the_name_is_correct() {
        Assert.assertEquals("Tom Cruise", getName(spartanURL,1116));

    }

    @Then("verify that the phone is correct")
    public void verify_that_the_phone_is_correct() {
        Assert.assertEquals(2038238760,getPhone(spartanURL,1116));
    }

    @When("the user partial update the created spartan name")
    public void the_user_partial_update_the_created_spartan_name() {
        System.out.println("the user partial update the created spartan name is here");
        responsePatch = given().body(payLordPatch).accept(ContentType.JSON).contentType(ContentType.JSON)
                .pathParam("id", 1000)
                .when().patch(url + "/api/spartans/{id}").prettyPeek()
                .then().statusCode(204).extract().response();


    }

    @Then("verify that the name is updated")
    public void verify_that_the_name_is_updated() {
        responsePatch = given().body(payLordPatch).accept(ContentType.JSON).contentType(ContentType.JSON)
                .pathParam("id", 1000)
                .when().get(url + "/api/spartans/{id}")
                .prettyPeek().then().extract().response();
        System.out.println(responsePatch.path("name").toString());
        Assert.assertEquals("Patched Done", responsePatch.path("name"));


    }

    @When("the user update the name gender phone")
    public void the_user_update_the_name_gender_phone() {
        String payLordPut = "{\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"name\": \"Ashley\",\n" +
                "  \"phone\": 8604286477\n" +
                "}";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("gender","Female");
        requestBody.put("name","Ashley Chanthinith");
        requestBody.put("phone",2039318597l);

        int id = 995;

        Response response1 = given().body(payLordPut).accept(ContentType.JSON).contentType(ContentType.JSON)
                .pathParam("id", id).when().put(url + "/api/spartans/{id}").then().statusCode(204).extract().response();

        response1.prettyPeek();

    }

    @Then("verify that the user is updated")
    public void verify_that_the_user_is_updated() {
        String  putExpected = "{id=995, name=Ashley, gender=Female, phone=8604286477}";
       Response response1  = given().accept(ContentType.JSON).pathParam("id", 995)
                .when().get(url + "/api/spartans/{id}").prettyPeek().then().extract().response();
        System.out.println(putExpected);
        System.out.println(response1.path("").toString());
        Assert.assertEquals(putExpected,response1.path("").toString());


    }


}
