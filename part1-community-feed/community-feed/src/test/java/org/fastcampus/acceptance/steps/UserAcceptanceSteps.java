package org.fastcampus.acceptance.steps;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.fastcampus.user.application.dto.CreateUserRequestDto;
import org.fastcampus.user.application.dto.FollowUserRequestDto;
import org.springframework.http.MediaType;

public class UserAcceptanceSteps {

    public static ExtractableResponse<Response> createUser(CreateUserRequestDto dto) {
        return RestAssured
                .given().log().all()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/user")
                .then().log().all()
                .extract();
    }

    public static ValidatableResponse followUser(FollowUserRequestDto dto) {
        return RestAssured
                .given().log().all()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/relation/follow")
                .then().log().all()
                .statusCode(200);
    }
}
