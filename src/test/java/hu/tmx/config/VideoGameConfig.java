package hu.tmx.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

public class VideoGameConfig {

    public static RequestSpecification requestSpecificationVideoGame;
    public static ResponseSpecification responseSpecificationVideoGame;

    @BeforeAll
    public static void setUp(){
        requestSpecificationVideoGame = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setBasePath("/app/")
                .setPort(8080)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        responseSpecificationVideoGame = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

        RestAssured.requestSpecification = requestSpecificationVideoGame;
        RestAssured.responseSpecification = responseSpecificationVideoGame;
    }
}
