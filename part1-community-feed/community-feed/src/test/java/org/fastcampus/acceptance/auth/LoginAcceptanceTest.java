package org.fastcampus.acceptance.auth;

import org.fastcampus.acceptance.utils.AcceptanceTestTemplate;
import org.fastcampus.auth.application.dto.LoginRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fastcampus.acceptance.steps.LoginAcceptanceSteps.requestLogin;
import static org.fastcampus.acceptance.steps.LoginAcceptanceSteps.requestLoginGetResponseCode;

public class LoginAcceptanceTest extends AcceptanceTestTemplate {

    private final String email = "email@email.com";

    @BeforeEach
    void setUp() {
        this.cleanUp();
        this.createUser(email);
    }

    @Nested
    class Login {
        @DisplayName("성공" +
                "1. login 요청" +
                "2. token 존재")
        @Test
        void test1() {
            LoginRequestDto dto = new LoginRequestDto(email, "password");
            String token = requestLogin(dto);

            assertThat(token).isNotNull();
        }

        @DisplayName("실패" +
                "1. 잘못된 password login 요청" +
                "2. code 400")
        @Test
        void test2() {
            // 1
            LoginRequestDto dto = new LoginRequestDto(email, "invalidPassword");
            Integer code = requestLoginGetResponseCode(dto);

            // 2
            assertThat(code).isEqualTo(500);
        }
    }
}
