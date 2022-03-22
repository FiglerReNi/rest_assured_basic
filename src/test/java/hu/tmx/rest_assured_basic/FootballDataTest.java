package hu.tmx.rest_assured_basic;

import static io.restassured.RestAssured.*;

import hu.tmx.config.Endpoints;
import hu.tmx.config.FootballDataConfig;
import org.junit.jupiter.api.Test;

public class FootballDataTest extends FootballDataConfig {

    @Test
    public void getDetailsOfOneArea(){

        given().queryParam("areas", 2072).
        when().get("areas");

    }
}
