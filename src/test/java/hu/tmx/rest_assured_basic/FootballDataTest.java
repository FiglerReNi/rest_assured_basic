package hu.tmx.rest_assured_basic;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import hu.tmx.config.Endpoints;
import hu.tmx.config.FootballDataConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import java.util.List;
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

    @Test
    public void getAllTeamDataInString() {
        String responseBody = given().pathParam("id", 57).
                                when().get(Endpoints.SINGLE_TEAM).asString();
        System.out.println(responseBody);
    }

    @Test
    public void getAllTeamDataWithAssertInResponseObject(){
        Response response = given().pathParam("id", 57).
                                when().get(Endpoints.SINGLE_TEAM).
                                then().contentType(ContentType.JSON).extract().response();
        String responseBody = response.asString();
        System.out.println(responseBody);
    }

    @Test
    public void getAllTeamDataWithAssertInString(){
        String responseBody = given().pathParam("id", 57).
                when().get(Endpoints.SINGLE_TEAM).
                then().contentType(ContentType.JSON).extract().asString();
        System.out.println(responseBody);
    }

    @Test
    public void getHeaders(){
        Response response = given().pathParam("id", 57).
                                when().get(Endpoints.SINGLE_TEAM).
                                then().contentType(ContentType.JSON).extract().response();
        Headers headers = response.getHeaders();
        String specificHeader = response.getHeader("Content-Type");
        System.out.println(specificHeader);
    }

    @Test
    public void getFirstTeamNameInGivenCompetitionInString(){
        String firstTeamName = given().pathParam("id", 2021).
                                when().get(Endpoints.SINGLE_COMPETITION).jsonPath().getString("teams.name[0]");
        System.out.println(firstTeamName);
    }

    @Test
    public void getAllTeamNameInGivenCompetitionWithAssertInString(){
        Response response = given().pathParam("id", 2021).
                                when().get(Endpoints.SINGLE_COMPETITION).
                                then().extract().response();
        List<String> teamNames = response.path("teams.name");
        for (String teamName : teamNames){
            System.out.println(teamName);
        }
    }
}
