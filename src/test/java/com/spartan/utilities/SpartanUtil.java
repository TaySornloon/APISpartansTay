package com.spartan.utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

import static io.restassured.RestAssured.*;

public class SpartanUtil {

    //write your methods in here

    //post
    public int postStatusCode =0;

    public static void post(String body,String url){

         given().body(body).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post(url).prettyPeek().then().statusCode(201).extract().response();

    }
    public static int postStatus(String body,String url ){
        Response response = given().body(body).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post(url).then().statusCode(201).extract().response();
        return response.statusCode();
    }
    public static String message(String body,String url){
        Response response = given().body(body).accept(ContentType.JSON).contentType(ContentType.JSON)
                .when().post(url).then().statusCode(201).extract().response();
        return response.path("success");
    }

    //get*******************************************************************
    public static void get(String url, int id){

        given().accept(ContentType.JSON).pathParam("id",id)
                .when().get(url).prettyPeek().then().statusCode(200).extract().response();

    }
    public static String getName(String url, int id){

        Response name = given().accept(ContentType.JSON).pathParam("id", id)
                .when().get(url).prettyPeek().then().statusCode(200).extract().response();

        return name.path("name").toString();
    }
    public static int getPhone(String url, int id){

        Response name = given().accept(ContentType.JSON).pathParam("id", id)
                .when().get(url).prettyPeek().then().statusCode(200).extract().response();

        return name.path("phone");
    }


    //update

    //partialUpdate

    //delete

}
