package com.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

public class AppTestParameters {
    private final String baseUri ="https://pokeapi.co";

    @Test
    @Tag("parameters")
    void testGetAnyParametersLimit() {
        given().baseUri(baseUri).when().get("api/v2/berry/?limit=5").then()
                .statusCode(200)
                .body("count" , equalTo(64))
                .body("results[0].name", equalTo("cheri"))
                .body("results.size()", equalTo(5))
                .body("results[1].url", notNullValue())
                .log().all();
    }

    @Test
    @Tag("parameters")
    void testGetParametersWithOffset() {
        given()
                .baseUri(baseUri)
                .queryParam("offset", 5)
                .when()
                .get("api/v2/berry/")
                .then()
                .statusCode(200)
                .body("count", equalTo(64))
                .body("results.size()", equalTo(20))
                .body("results[0].name", equalTo("leppa"))
                .log().all();
    }

    @Test
    @Tag("parameters")
    void testQueryParametersBuilder() {
        given()
                .baseUri(baseUri)
                .queryParam("limit", 1)
                .queryParam("offset", 10)
                .when()
                .get("api/v2/berry/")
                .then()
                .statusCode(200)
                .body("results.size()", equalTo(1))
                .log().all();
    }
    @Test
    @Tag("parameters")
    void testGetAllBerries() {
        given()
                .baseUri(baseUri)
                .queryParam("limit", 64)
                .when()
                .get("api/v2/berry/")
                .then()
                .statusCode(200)
                .body("results.size()", equalTo(64))
                .log().all();
    }

    @Test
    @Tag("parameters")
    void testExtractOnlyNames() {
        List<String> berryNames = given()
                .baseUri(baseUri)
                .queryParam("limit", 5)
                .when()
                .get("api/v2/berry/")
                .then()
                .statusCode(200)
                .extract()
                .path("results.name");

        assertEquals(berryNames , List.of("cheri", "chesto", "pecha", "rawst", "aspear") );
    }
    @Test
    @Tag("parameters")
    void testExtractAndVerifyUrlsStructure() {
        List<String> berryUrls = given()
                .baseUri(baseUri)
                .queryParam("limit", 3)
                .when()
                .get("api/v2/berry/")
                .then()
                .statusCode(200)
                .extract()
                .path("results.url");


        assertEquals(3, berryUrls.size());


        for (String url : berryUrls) {
            assertNotNull(url);
            assertFalse(url.isEmpty());
        }
    }



    @Test
    @Tag("parameters")
    void testExtractMaxIdFromList() {
        List<String> urls = given()
                .baseUri(baseUri)
                .queryParam("limit", 5)
                .queryParam("offset", 10)
                .when()
                .get("api/v2/ability/")
                .then()
                .statusCode(200)
                .extract()
                .path("results.url");


        List<Integer> ids = urls.stream()
                .map(url -> {

                    String[] tokens = url.split("/");
                    return Integer.parseInt(tokens[tokens.length - 1]);
                })
                .collect(java.util.stream.Collectors.toList());


        Integer maxId = ids.stream().max(Integer::compareTo).orElse(0);



        assertEquals(15, maxId);
    }

    @Test
    @Tag("parameters")
    void testExtractFirstAndLastNames() {

        List<String> berryNames = given()
                .baseUri(baseUri)
                .queryParam("limit", 5)
                .when()
                .get("api/v2/berry/")
                .then()
                .statusCode(200)
                .extract()
                .path("results.name");


        assertEquals(5, berryNames.size());


        String firstName = berryNames.get(0);
        String lastName = berryNames.get(berryNames.size() - 1);


        assertNotNull(firstName);
        assertNotNull(lastName);


        assertNotEquals(firstName, lastName);
    }

}
