package org.fastcampus.acceptance.steps;

import io.restassured.RestAssured;
import org.fastcampus.auth.application.dto.LoginRequestDto;
import org.fastcampus.auth.application.dto.UserAccessTokenResponseDto;
import org.springframework.http.MediaType;

public class LoginAcceptanceSteps {

    public static String requestLogin(LoginRequestDto dto) {
        UserAccessTokenResponseDto value = RestAssured
                .given().log().all()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract()
                .jsonPath()
                .getObject("value", UserAccessTokenResponseDto.class);
        return value.accessToken();
    }

    public static Integer requestLoginGetResponseCode(LoginRequestDto dto) {
        return RestAssured
                .given().log().all()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract()
                .jsonPath()
                .getObject("code", Integer.class);
    }
}
