package org.fastcampus.user.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserInfoTest {

    @Nested
    class constructor {
        @ParameterizedTest
        @ValueSource(strings = {"", " ", "    "})
        @NullSource
        void test1(String inputName) {
            // Given
            String name = inputName;
            String profileImageUrl = "";

            // When & Then
            assertThatThrownBy(() -> new UserInfo(name, profileImageUrl))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void test1000() {
            // Given
            String name = "name";
            String profileImageUrl = "";

            // When
            UserInfo result = new UserInfo(name, profileImageUrl);

            // Then
            assertThat(result).isNotNull();
        }
    }
}