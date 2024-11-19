package org.fastcampus.acceptance.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.fastcampus.acceptance.steps.SignUpAcceptanceSteps;
import org.fastcampus.acceptance.utils.AcceptanceResponse;
import org.fastcampus.acceptance.utils.AcceptanceTest;
import org.fastcampus.acceptance.utils.AcceptanceDataLoader;
import org.fastcampus.auth.application.dto.CreateUserAuthRequestDto;
import org.fastcampus.auth.application.dto.SendEmailRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fastcampus.acceptance.steps.SignUpAcceptanceSteps.requestSendEmail;
import static org.fastcampus.acceptance.steps.SignUpAcceptanceSteps.requestVerifyEmail;

@AcceptanceTest
public class SignUpAcceptanceTest {

    private final String email = "email@email.com";

    @Autowired
    private AcceptanceDataLoader acceptanceDataLoader;

    @BeforeEach
    void setUp() {

    }

    @Nested
    class RequestSendEmail {
        @DisplayName("유요한 이메일")
        @Test
        void test1() {
            // given
            SendEmailRequestDto dto = new SendEmailRequestDto(email);

            // when
            Integer code = requestSendEmail(dto);

            // then
            String token = acceptanceDataLoader.getEmailToken(email);
            assertThat(token).isNotNull();
            assertThat(code).isEqualTo(0);
        }

        @DisplayName("유효하지 않은 이메일")
        @Test
        void test2() {
            // given
            SendEmailRequestDto dto = new SendEmailRequestDto("invalidEmail");

            // when
            Integer code = requestSendEmail(dto);

            // then
            assertThat(code).isEqualTo(400);
        }
    }

    @Nested
    class RequestVerifyEmail {

        @DisplayName("1. sendEmail 요청" +
                "2. 발급받은 token 으로 verifyEmail 요청" +
                "3. 성공"
        )
        @Test
        void givenSendEmail_whenVerifyEmail_thenEmailVerified() {
            // 1. sendEmail 요청
            requestSendEmail(new SendEmailRequestDto(email));
            String token = acceptanceDataLoader.getEmailToken(email);

            // 2. verifyEmail
            Integer code = requestVerifyEmail(email, token);

            // then
            boolean isEmailVerified = acceptanceDataLoader.isEmailVerified(email);
            assertThat(code).isEqualTo(0);
            assertThat(isEmailVerified).isTrue();
        }

        @DisplayName("1. sendEmail 요청" +
                "2. 잘못된 token 으로 verifyEmail 요청" +
                "3. 실패"
        )
        @Test
        void givenSendEmail_whenVerifyEmailWithWrongToken_thenEmailNotVerified() {
            // given
            requestSendEmail(new SendEmailRequestDto(email));

            // when
            Integer code = requestVerifyEmail(email, "wrong token");

            // then
            boolean isEmailVerified = acceptanceDataLoader.isEmailVerified(email);
            assertThat(code).isEqualTo(500);
            assertThat(isEmailVerified).isFalse();
        }

        @DisplayName("1. sendEmail 요청" +
                "2. 발급받은 token 으로 verifyEmail 요청" +
                "2. 발급받은 token 으로 verifyEmail 재 요청" +
                "3. 실패"
        )
        @Test
        void givenSendEmailVerified_whenVerifyAgain_thenThrowError() {
            // given
            requestSendEmail(new SendEmailRequestDto(email));
            String token = acceptanceDataLoader.getEmailToken(email);
            requestVerifyEmail(email, token);

            // when
            Integer code = requestVerifyEmail(email, token);

            // then
            assertThat(code).isEqualTo(500);
        }

        @DisplayName("1. sendEmail 요청" +
                "2. 잘못 된 email, 잘못 된 token 으로 verifyEmail 요청" +
                "3. 실패"
        )
        @Test
        void givenSendEmail_whenVerifyEmailWithWrongEmail_thenThrowError() {
            // given
            requestSendEmail(new SendEmailRequestDto(email));

            // when
            Integer code = requestVerifyEmail("wrong email", "token");

            // then
            assertThat(code).isEqualTo(400);
        }
    }

    @Nested
    class RequestRegisterUser {
        @DisplayName("1. sendEmail 요청" +
                "2. verifyEmail 요청" +
                "3. registerUser 요청" +
                "4. 성공")
        @Test
        void test1() {
            // 1. sendEmail
            requestSendEmail(new SendEmailRequestDto(email));
            String token = acceptanceDataLoader.getEmailToken(email);

            // 2. verifyEmail
            requestVerifyEmail(email, token);

            // 3. registerUser
            CreateUserAuthRequestDto dto = new CreateUserAuthRequestDto(email, "password", "USER", "name", "profileImageUrl");
            ExtractableResponse<Response> response = SignUpAcceptanceSteps.requestRegisterUser(dto);

            // then
            assertThat(AcceptanceResponse.getCode(response)).isEqualTo(0);
            Long userId = acceptanceDataLoader.getUserId(email);
            assertThat(userId).isEqualTo(AcceptanceResponse.getId(response));
        }

        @DisplayName("1. sendEmail 요청" +
                "2. registerUser 요청" +
                "3. 실패")
        @Test
        void test2() {
            // 1. sendEmail
            requestSendEmail(new SendEmailRequestDto(email));

            // 2. registerUser
            CreateUserAuthRequestDto dto = new CreateUserAuthRequestDto(email, "password", "USER", "name", "profileImageUrl");
            ExtractableResponse<Response> response = SignUpAcceptanceSteps.requestRegisterUser(dto);

            // then
            assertThat(AcceptanceResponse.getCode(response)).isEqualTo(400);
        }
    }
}
