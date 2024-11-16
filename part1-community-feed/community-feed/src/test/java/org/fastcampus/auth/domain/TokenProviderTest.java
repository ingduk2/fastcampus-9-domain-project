package org.fastcampus.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TokenProviderTest {

    private final String secretKey = "jLjswBUIfZl6VF4pHiXNuYzkP9+sly8IZRheRS24kfd/tg3uZ3IPCGXcYnfmmnITKRtCYafx8f6KdM7FVneoAw==";
    private final TokenProvider tokenProvider = new TokenProvider(secretKey);

    @Nested
    class createToken {
        @DisplayName("실패 - 유효하지 않은 토큰")
        @Test
        void test1() {
            // given
            String invalidToken = "invalidToken";

            // when & Then
            assertThatThrownBy(() -> tokenProvider.getUserId(invalidToken))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("JWT token is malformed");
        }

        @DisplayName("성공")
        @Test
        void test1000() {
            // given
            Long userId = 1L;
            String role = UserRole.USER.name();

            // when
            String token = tokenProvider.createToken(userId, role);

            // then
            assertThat(token).isNotNull();
            assertThat(tokenProvider.getUserId(token)).isEqualTo(userId);
            assertThat(tokenProvider.getUserRole(token)).isEqualTo(role);
        }
    }
}

