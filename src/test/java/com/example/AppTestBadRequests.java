package com.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static io.restassured.RestAssured.given;
import static java.lang.Math.log;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;


public class AppTestBadRequests {
    private final String baseUri ="https://pokeapi.co";

    @Test
    @Tag("bad-requests")
    void testBadRequests() {
        given().baseUri(baseUri).when().get("/api/v2/berry/6767767676/").then().statusCode(404);
    }

    @Test
    @Tag("bad-requests")
    void testNegativeParam() {
        given()
                .baseUri(baseUri)
                .when()
                .get("api/v2/berry/-1")
                .then()
                .statusCode(404)
                .log().all();
    }


    @Test
    @Tag("bad-requests")
    void testStringInsteadOfId() {
        given()
                .baseUri(baseUri)
                .when()
                .get("/api/v2/berry/pikachu/")
                .then()
                .statusCode(404)
                .log().all();
    }



    @Test
    @Tag("bad-requests")
    void testIdOverflow() {
        given()
                .baseUri(baseUri)
                .when()
                .get("/api/v2/berry/999999999999999999999999999999/")
                .then()
                .statusCode(404)
                .log().all();
    }

    @Test
    @Tag("bad-requests")
    void testInvalidHttpMethod() {
        given()
                .baseUri(baseUri)
                .contentType("application/json")
                .body("{\"name\": \"super-berry\"}")
                .when()
                .post("/api/v2/berry/")
                .then()
                .statusCode(org.hamcrest.Matchers.anyOf(
                        org.hamcrest.Matchers.equalTo(405),
                        org.hamcrest.Matchers.equalTo(404)
                ))
                .log().all();
    }


    @Test
    @Tag("bad-requests")
    void testInvalidEndpoint() {
        given()
                .baseUri(baseUri)
                .when()
                .get("/api/v2/berries-mutated/")
                .then()
                .statusCode(400)
                .log().all();
    }

    @Test
    @Tag("bad-requests")
    void testInvalidQueryParamType() {
        given()
                .baseUri(baseUri)
                .queryParam("limit", "not-a-number")
                .queryParam("offset", "abc")
                .when()
                .get("/api/v2/berry/")
                .then()
                .statusCode(200)
                .body("results", org.hamcrest.Matchers.notNullValue())
                .log().all();
    }

    @Test
    @Tag("bad-requests")
    void testExtremeLimitQuery() {
        given()
                .baseUri(baseUri)
                .queryParam("limit", 2000000000)
                .when()
                .get("/api/v2/berry/")
                .then()
                .statusCode(200)
                .body("results", org.hamcrest.Matchers.notNullValue())
                .log().all();
    }

}
