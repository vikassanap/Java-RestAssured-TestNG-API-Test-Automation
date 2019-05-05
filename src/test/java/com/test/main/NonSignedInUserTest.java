package com.test.main;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

public class NonSignedInUserTest {
	int user_id = 51;
	String username = "username";
	String password = "password";

	@BeforeClass(alwaysRun=true)
	public void setUp() {
		System.out.println("User is not logged in");

	}

	@AfterClass(alwaysRun=true)
	public void tearDown() {
		System.out.println("Test Case ended");

	}

	@Test
	public void FavoriteProperties() {
		RequestSpecBuilder request_builder = new RequestSpecBuilder();
		request_builder.setBaseUri("http://test-app:80/api");
		RequestSpecification requestSpec = request_builder.build();
		ResponseSpecBuilder response_builder = new ResponseSpecBuilder();
		response_builder.expectStatusCode(400);
		ResponseSpecification responseSpec = response_builder.build();
		expect().given().spec(requestSpec).get("/users/favorite_properties")
				.then().spec(responseSpec);
	}
}
