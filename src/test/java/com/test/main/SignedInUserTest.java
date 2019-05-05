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

public class SignedInUserTest {
	int user_id = 51;
	String username = "username";
	String password = "password";

	@BeforeClass(alwaysRun=true)
	public void setUp() {
		String requestString = "{\"username\":\"" + username
				+ "\",\"password\":\"" + password + "\"}";
		System.out.println(requestString);
		RequestSpecBuilder request_builder = new RequestSpecBuilder();
		request_builder.setBody(requestString);
		request_builder.setBaseUri("http://test-app:80/api");
		request_builder.setContentType(ContentType.JSON);
		RequestSpecification requestSpec = request_builder.build();
		ResponseSpecBuilder response_builder = new ResponseSpecBuilder();
		response_builder.expectStatusCode(200);
		response_builder.expectHeader("User_id", Integer.toString(user_id));
		ResponseSpecification responseSpec = response_builder.build();
		expect().given().when().spec(requestSpec).post("/users/sign_in").then()
				.spec(responseSpec);
		System.out.println("Login success");
	}

	@AfterClass(alwaysRun=true)
	public void tearDown() {
		RequestSpecBuilder request_builder = new RequestSpecBuilder();
		request_builder.addHeader("user_id", Integer.toString(user_id));
		request_builder.setBaseUri("http://test-app:80/api");
		RequestSpecification requestSpec = request_builder.build();
		ResponseSpecBuilder response_builder = new ResponseSpecBuilder();
		response_builder.expectStatusCode(200);
		ResponseSpecification responseSpec = response_builder.build();
		expect().given().when().spec(requestSpec).get("/users/sign_out").then()
				.spec(responseSpec);
		System.out.println("Logout success");
	}

	@Test
	public void FavoriteProperties() {
		RequestSpecBuilder request_builder = new RequestSpecBuilder();
		request_builder.addHeader("user_id", Integer.toString(user_id));
		request_builder.setBaseUri("http://test-app:80/api");
		RequestSpecification requestSpec = request_builder.build();
		ResponseSpecBuilder response_builder = new ResponseSpecBuilder();
		response_builder.expectStatusCode(200);
		response_builder.expectBody(
				"data.properties.id",
				hasItems(31651, 253211, 253235, 256303, 243609, 247529, 38687,
						38860, 117827, 254671, 253640, 23085));
		ResponseSpecification responseSpec = response_builder.build();
		expect().given().spec(requestSpec).get("/users/favorite_properties")
				.then().spec(responseSpec);
	}
}
