package hu.tmx.rest_assured_basic;

import static io.restassured.RestAssured.*;

import hu.tmx.config.MyFirstTestConfig;
import hu.tmx.config.Endpoints;
import org.junit.jupiter.api.Test;

public class MyFirstTest extends MyFirstTestConfig {

    @Test
    public void myFirstTest(){
        given().log().all().
        when().get(Endpoints.ALL_VIDEO_GAMES).
        then().log().all();
    }

    @Test
    public void simpleMyFirstTest(){
        get(Endpoints.ALL_VIDEO_GAMES).then().log().all();
    }

}
