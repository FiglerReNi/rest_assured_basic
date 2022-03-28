package hu.tmx.rest_assured_basic;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import hu.tmx.config.Endpoints;
import hu.tmx.config.FootballDataConfig;
import org.junit.jupiter.api.Test;

public class FootballDataTest extends FootballDataConfig {

    @Test
    public void getDetailsOfOneArea(){

        given().queryParam("areas", 2072).
        when().get(Endpoints.SINGLE_AREA);
    }

    @Test
    public void getDateTeamFounded(){

        given().pathParam("id", 57).
        when().get(Endpoints.SINGLE_TEAM).
        then().body("founded", equalTo(1886));
    }

    @Test
    public void getFirstTeamNameInGivenCompetition(){
        given().pathParam("id", 2021).
        when().get(Endpoints.SINGLE_COMPETITION).
        then().body("teams.name[0]", equalTo("Arsenal FC"));
    }
}
