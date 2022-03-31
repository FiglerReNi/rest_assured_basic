package hu.tmx.rest_assured_basic;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import hu.tmx.config.Endpoints;
import hu.tmx.config.FootballDataConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Test
    public void getSingleTeamAllDataInGivenCompetition(){
        Response response = given().pathParam("id", 2021).
                            when().get(Endpoints.SINGLE_COMPETITION);
        Map<String, ?> allDataOfSingleTeam = response.path("teams.find {it.name == 'Liverpool FC'}");
        System.out.println(allDataOfSingleTeam);
    }

    @Test
    public void getSinglePlayerNameByShirtNumber(){
        Response response = given().pathParam("id", 57).
                            when().get(Endpoints.SINGLE_TEAM);
        String certainPlayer = response.path("squad.find {it.shirtNumber == 48}.name");
        System.out.println(certainPlayer);
    }

    @Test
    public void getListOfPlayersNameByNationality(){
        Response response = given().pathParam("id", 57).
                            when().get(Endpoints.SINGLE_TEAM);
        List<String> playersName = response.path("squad.findAll {it.nationality == 'Brazil'}.name");
        System.out.println(playersName);
    }

    @Test
    public void getSinglePlayerNameWithMaxShirtNumber(){
        Response response = given().pathParam("id", 57).
                            when().get(Endpoints.SINGLE_TEAM);
        String playerName = response.path("squad.max {it.shirtNumber}.name");
        System.out.println(playerName);
    }

    @Test
    public void getSumAllPlayersId(){
        Response response = given().pathParam("id", 57).
                            when().get(Endpoints.SINGLE_TEAM);
        int sumOfIds = response.path("squad.collect {it.id}.sum()");
        System.out.println(sumOfIds);
    }

    @Test
    public void getSinglePlayerByPositionAndNation(){
        Response response = given().pathParam("id", 57).
                            when().get(Endpoints.SINGLE_TEAM);
        Map<String, ?> playerAllData = response.path("squad.findAll {it.position == 'Defender'}.find {it.nationality == 'England'}");
        System.out.println(playerAllData);
    }

    @Test
    public void getSinglePlayerByPositionAndNationWithParam(){
        String position = "Defender";
        String nationality = "England";
        Response response = given().pathParam("id", 57).
                            when().get(Endpoints.SINGLE_TEAM);
        Map<String, ?> playerAllData = response.path("squad.findAll {it.position == '%s'}.find {it.nationality == '%s'}", position,
                nationality);
        System.out.println(playerAllData);
    }

    @Test
    public void getPlayersByPositionAndNationWithParam(){
        String position = "Defender";
        String nationality = "England";
        Response response = given().pathParam("id", 57).
                when().get(Endpoints.SINGLE_TEAM);
        ArrayList<Map<String, ?>> playerAllData = response.path("squad.findAll {it.position == '%s'}.findAll {it.nationality == '%s'}",
                position, nationality);
        System.out.println(playerAllData);
    }



}
