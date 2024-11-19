package org.fastcampus.acceptance.utils;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AcceptanceResponse {

    private AcceptanceResponse() {

    }

    public static Integer getCode(ExtractableResponse<Response> response) {
        return response.jsonPath().get("code");
    }

    public static Long getId(ExtractableResponse<Response> response) {
        return response.jsonPath().getLong("value");
    }
}
