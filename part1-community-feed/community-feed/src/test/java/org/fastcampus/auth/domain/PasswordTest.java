package org.fastcampus.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordTest {

    @DisplayName("비밀번호가 일치하면 true")
    @Test
    void test1() {
        // given
        Password password = Password.createEncryptPassword("password");

        // when
        boolean result = password.matchPassword("password");

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("비밀번호가 일치하지 않으면 false")
    @Test
    void test2() {
        // given
        Password password = Password.createEncryptPassword("password");

        // when
        boolean result = password.matchPassword("invalid");

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("")
    @ParameterizedTest
    @NullAndEmptySource
    void test3(String inputPassword) {
        assertThatThrownBy(() -> Password.createEncryptPassword(inputPassword))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
