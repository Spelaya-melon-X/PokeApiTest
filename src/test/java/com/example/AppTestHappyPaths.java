package com.example;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static java.util.concurrent.CompletableFuture.anyOf;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.CompletableFuture;


public class AppTestHappyPaths {
    private final String baseUri ="https://pokeapi.co";
    @ParameterizedTest
    @Tag("happy-path-barry")
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    void testGetBerries(int berryId) {
        given()
                .baseUri(baseUri)
                .pathParam("berry_id" , berryId)
                .when()
                .get("/api/v2/berry/{berry_id}/")
                .then()
                .statusCode(200)
                .body("id", equalTo(berryId))
                .body("name", notNullValue())
                .body("growth_time" , notNullValue())
                .body("max_harvest" , notNullValue())
                .body("natural_gift_power" , notNullValue())
                .body("size" , notNullValue())
                .body("smoothness" , notNullValue())
                .body("soil_dryness" , notNullValue())
                .body("firmness" , notNullValue())
                .body("flavors" , notNullValue())
                .body("item" , notNullValue())
                .body("natural_gift_type" , notNullValue())
                .log().all();
    }

    @ParameterizedTest
    @Tag("happy-path-barry")
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    void testGetPokemonAbilities(int pokemonId ) {
        given()
                .baseUri(baseUri)
                .pathParam("id" ,pokemonId )
                .when()
                .get("/api/v2/ability/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(pokemonId))
                .body("name", notNullValue())
                .body("is_main_series" , notNullValue())
                .body("generation" , notNullValue())
                .body("names" , notNullValue())
                .body("effect_entries" , notNullValue())
                .body("effect_changes" , notNullValue())
                .log().all();
    }

    @ParameterizedTest
    @Tag("happy-path-barry")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testGetEvolutionChainsLogic(int chainId) {
        given()
                .baseUri(baseUri)
                .pathParam("id", chainId)
                .when()
                .get("/api/v2/evolution-chain/{id}/")
                .then()
                .statusCode(200)
                .body("id", equalTo(chainId))
                .body("chain.species.name", notNullValue())
                .body("chain.species.url", notNullValue())
                .body("chain.evolves_to", hasSize(greaterThan(0)))
                .body("chain.evolves_to[0].species.name", notNullValue())
                .body("chain.evolves_to[0].evolution_details", notNullValue())
                .log().all();
    }


    @ParameterizedTest
    @Tag("happy-path-barry")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void testGetPokemonTypesDamageRelations(int typeId) {
        given()
                .baseUri(baseUri)
                .pathParam("id", typeId)
                .when()
                .get("/api/v2/type/{id}/")
                .then()
                .statusCode(200)
                .body("id", equalTo(typeId))
                .body("name", notNullValue())
                .body("damage_relations.double_damage_from", notNullValue())
                .body("damage_relations.double_damage_to", notNullValue())
                .body("damage_relations.half_damage_from", notNullValue())
                .body("damage_relations.half_damage_to", notNullValue())
                .body("damage_relations.no_damage_from", notNullValue())
                .body("damage_relations.no_damage_to", notNullValue())
                .body("generation.name", notNullValue())
                .body("pokemon", hasSize(greaterThan(0)))
                .log().all();
    }



    @ParameterizedTest
    @Tag("happy-path-barry")
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9})
    void testGetPokemonForms(int pokemonId ) {
        given()
                .baseUri(baseUri)
                .pathParam("id" ,pokemonId )
                .when()
                .get("/api/v2/pokemon-habitat/{id}/")
                .then()
                .statusCode(200)
                .body("id", equalTo(pokemonId))
                .body("name", notNullValue())
                .body("names" , notNullValue())
                .body("pokemon_species" , notNullValue())
                .log().all();
    }




}
