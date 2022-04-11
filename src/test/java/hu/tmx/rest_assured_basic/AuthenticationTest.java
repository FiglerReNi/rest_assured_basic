package hu.tmx.rest_assured_basic;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthenticationTest {

    @BeforeAll
    public static void setUp(){
     //RestAssured.proxy("localhost", 8888);
    }

    @Test
    public void basicPreemptiveAuthTest(){
        given().auth().preemptive().basic("username", "password").
        when().get("http://localhost:8080/someEndpoint").
        then().statusCode(200);
    }

    @Test
    public void basicChallengeTest(){
        given().auth().basic("username", "password").
        when().get("http://localhost:8080/someEndpoint").
        then().statusCode(200);
    }

    @Test
    public void oauth1Test(){
        given().auth().oauth("consumerKey", "consumerSecret", "accessToken", "secretToken").
        when().get("http://localhost:8080/someEndpoint").
        then().statusCode(200);
    }

    @Test
    public void oauth2Test(){
        given().auth().oauth2("accesssToken").
        when().get("http://localhost:8080/someEndpoint").
        then().statusCode(200);
    }

    @Test
    public void setHttpsValidation(){
        given().relaxedHTTPSValidation().
        when().get("https://localhost:8080/someEndpoint").
        then().statusCode(200);
    }

    @Test
    public void setHttpsValidationWithKeyStore(){
        given().keyStore("/keyStorePath", "password").
        when().get("https://localhost:8080/someEndpoint").
        then().statusCode(200);
    }
}
