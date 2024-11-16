package org.fastcampus.acceptance.steps;

import io.restassured.RestAssured;
import org.fastcampus.post.application.dto.CreatePostRequestDto;
import org.fastcampus.post.ui.dto.GetPostContentResponseDto;
import org.springframework.http.MediaType;

import java.util.List;

public class FeedAcceptanceSteps {

    public static Long requestPost(CreatePostRequestDto dto) {
        return RestAssured
                .given().log().all()
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/post")
                .then().log().all()
                .extract()
                .jsonPath()
                .getObject("value", Long.class);
    }

    public static List<GetPostContentResponseDto> requestFeed(String token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/feed")
                .then().log().all()
                .extract()
                .jsonPath()
                .getList("value", GetPostContentResponseDto.class);
    }

    public static Integer requestFeedGetResponseCode(String token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/feed")
                .then().log().all()
                .extract()
                .jsonPath()
                .get("code");
    }
}
