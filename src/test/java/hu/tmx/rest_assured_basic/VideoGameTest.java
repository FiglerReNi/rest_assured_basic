package hu.tmx.rest_assured_basic;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

import hu.tmx.config.Endpoints;
import hu.tmx.config.VideoGameConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;
import java.util.List;
import model.VideoGame;
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
        String json = "{\"id\": 65,"
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
                + "<id>66</id>"
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
        String json = "{\"id\": 3,\n"
                + "  \"name\": \"MyNewGame\","
                + "  \"releaseDate\": \"2022-03-17T08:40:04.069Z\","
                + "  \"reviewScore\": 77,"
                + "  \"category\": \"Driving\","
                + "  \"rating\": \"Universal\"}";

        given().body(json).pathParam("id", 3).
        when().put(Endpoints.SINGLE_VIDEO_GAME).
        then();
    }

    @Test
    public void deleteGame(){
        given().pathParam("id", 10).
        when().delete(Endpoints.SINGLE_VIDEO_GAME).
        then();
    }

    @Test
    public void getSingleGame(){
        given().pathParam("id", 5).
        when().get(Endpoints.SINGLE_VIDEO_GAME).
        then();
    }

    @Test
    public void createNewGameWitPojo(){
        VideoGame videoGame = VideoGame.builder()
                .id("68").reviewScore("99")
                .releaseDate("2022-02-02")
                .name("My new game")
                .category("Shooter")
                .rating("Universal").build();
        given().body(videoGame).
        when().post(Endpoints.ALL_VIDEO_GAMES).
        then();
    }

    @Test
    public void getSingleVideoGameXmlResponse(){
        given().pathParam("id", 5).header("Content-Type", "application/xml").header("Accept", "application/xml").
        when().get(Endpoints.SINGLE_VIDEO_GAME).
        then().body(matchesXsdInClasspath("VideoGameXsd.xsd"));
    }

    @Test
    public void getSingleVideoGameJsonResponse(){
        given().pathParam("id", 5).
        when().get(Endpoints.SINGLE_VIDEO_GAME).
        then().body(matchesJsonSchemaInClasspath("VideoGameJson.json"));
    }

    @Test
    public void getSingleVideoGamePojo(){
        Response response = given().pathParam("id", 5).
                            when().get(Endpoints.SINGLE_VIDEO_GAME);
        VideoGame videoGame = response.getBody().as(VideoGame.class);
        System.out.println(videoGame.toString());
    }

    @Test
    public void getAllGameWithResponseTime(){
        long responseTime = get(Endpoints.ALL_VIDEO_GAMES).time();
        System.out.println(responseTime);
    }

    @Test
    public void getAllGameInLessTimeThanSpecified(){
        when().get(Endpoints.ALL_VIDEO_GAMES).
        then().time(lessThan(2000L));
    }

    @Test
    public void getFirstGameName(){
        Response response = given().header("Content-Type", "application/xml").header("Accept", "application/xml").
                            when().get(Endpoints.ALL_VIDEO_GAMES);
        String gameName = response.path("videoGames.videoGame.name[0]");
        System.out.println(gameName);
    }

    @Test
    public void getFirstGameCategory(){
        Response response = given().header("Content-Type", "application/xml").header("Accept", "application/xml").
                            when().get(Endpoints.ALL_VIDEO_GAMES);
        String category = response.path("videoGames.videoGame[0].@category");
        System.out.println(category);
    }

    @Test
    public void getAllGamesInList(){
        String responseAsString = given().header("Content-Type", "application/xml").header("Accept", "application/xml").
                                when().get(Endpoints.ALL_VIDEO_GAMES).asString();
        List<Node> allResults = XmlPath.from(responseAsString).get("videoGames.videoGame.findAll {element -> return element}");
        System.out.println(allResults.get(2).get("name").toString());
    }

    @Test
    public void getAllGamesWithDrivingCategory(){
        String responseAsString = given().header("Content-Type", "application/xml").header("Accept", "application/xml").
                                when().get(Endpoints.ALL_VIDEO_GAMES).asString();
        List<Node> allDrivingGames = XmlPath.from(responseAsString).get("videoGames.videoGame.findAll {videoGame -> def category = "
                + "videoGame.@category; category == 'Driving'}");
        System.out.println(allDrivingGames.get(0).get("name").toString());
    }

    @Test
    public void getSingleGameByName(){
        String responseAsString = given().header("Content-Type", "application/xml").header("Accept", "application/xml").
                                when().get(Endpoints.ALL_VIDEO_GAMES).asString();
        Node videoGame = XmlPath.from(responseAsString).get("videoGames.videoGame.find {videoGame -> def name = videoGame.name; name == "
                + "'Resident Evil 4'}");
        System.out.println(videoGame.get("name").toString());
    }

    @Test
    public void getReviewScoreByName(){
        String responseAsString = given().header("Content-Type", "application/xml").header("Accept", "application/xml").
                                when().get(Endpoints.ALL_VIDEO_GAMES).asString();
        int reviewScore = XmlPath.from(responseAsString).getInt("**.find {it.name == 'Gran Turismo 3'}.reviewScore");
        System.out.println(reviewScore);
    }

    @Test
    public void getAllGamesByReviewScore(){
        String responseAsString = given().header("Content-Type", "application/xml").header("Accept", "application/xml").
                                when().get(Endpoints.ALL_VIDEO_GAMES).asString();
        int reviewScore = 90;
        List<Node> allVideoGamesOverCertainScore = XmlPath.from(responseAsString).get("VideoGames.videoGame.findAll {it.reviewScore"
                + ".toFloat() >= "+ reviewScore +"}");
        System.out.println(allVideoGamesOverCertainScore);
    }
}
