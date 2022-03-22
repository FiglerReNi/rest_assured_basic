package hu.tmx.rest_assured_basic;

import static io.restassured.RestAssured.*;

import hu.tmx.config.Endpoints;
import hu.tmx.config.VideoGameConfig;
import org.junit.jupiter.api.Test;

public class VideoGameTest extends VideoGameConfig {

    @Test
    public void getAllGames(){
        given().
        when().get(Endpoints.ALL_VIDEO_GAMES).
        then();
    }

    @Test
    public void createNewGameByJson(){
        String json = "{\"id\": 31,"
                + "\"name\": \"MyTeam\","
                + "\"releaseDate\": \"2022-03-11T09:52:07.039Z\","
                + "\"reviewScore\": 88,"
                + "\"category\": \"Shooter\","
                + "\"rating\": \"Universal\"}";

        given().body(json).
        when().post(Endpoints.ALL_VIDEO_GAMES).
        then();
    }

    @Test
    public void createNewGameByXml(){
        String xml = "<videoGame category=\"Shooter\" rating=\"Universal\">"
                + "<id>27</id>"
                + "<name>Resident Evil 8</name>"
                + "<releaseDate>2005-10-01T00:00:00+02:00</releaseDate>"
                + "<reviewScore>88</reviewScore>"
                + "</videoGame>";

        given().body(xml).
                header("Accept", "application/xml").
                header("Content-Type", "application/xml").
        when().post(Endpoints.ALL_VIDEO_GAMES).
        then();
    }

    @Test
    public void updateGame(){
        String json = "{\"id\": 1,\n"
                + "  \"name\": \"MyNewGame\","
                + "  \"releaseDate\": \"2022-03-17T08:40:04.069Z\","
                + "  \"reviewScore\": 77,"
                + "  \"category\": \"Driving\","
                + "  \"rating\": \"Universal\"}";

        given().body(json).pathParam("id", 1).
        when().put(Endpoints.SINGLE_VIDEO_GAME).
        then();
    }

    @Test
    public void deleteGame(){
        given().pathParam("id", 1).
        when().delete(Endpoints.SINGLE_VIDEO_GAME).
        then();
    }

    @Test
    public void getSingleGame(){
        given().pathParam("id", 5).
        when().get(Endpoints.SINGLE_VIDEO_GAME).
        then();
    }
}
