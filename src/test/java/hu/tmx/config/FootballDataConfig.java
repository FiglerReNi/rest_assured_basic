package hu.tmx.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

public class FootballDataConfig {

    public static RequestSpecification requestSpecificationFootballData;
    public static ResponseSpecification responseSpecificationFootballData;

    @BeforeAll
    public static void setUp(){

        requestSpecificationFootballData = new RequestSpecBuilder()
                .setBaseUri("http://api.football-data.org")
                .setBasePath("/v2/")
                .addHeader("X-Auth-Token", "b990d140603441ccb899040b700a0048")
                .addHeader("X-Response-Control", "minified")
                .build();

        responseSpecificationFootballData = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

        RestAssured.requestSpecification = requestSpecificationFootballData;
        RestAssured.responseSpecification = responseSpecificationFootballData;

    }
}
