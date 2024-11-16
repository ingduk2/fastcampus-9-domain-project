package org.fastcampus.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이메일 실패")
    void test1(String email) {
        assertThatThrownBy(() -> Email.createEmail(email))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid/gb", "a.com", "asdf", "b#@naver", "안녕@하세요.com"})
    @DisplayName("이메일 실패")
    void test2(String email) {
        assertThatThrownBy(() -> Email.createEmail(email))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"valid@gb", "a@naver.com", "b@naver"})
    @DisplayName("이메일 성공")
    void test1000(String email) {
        Email result = Email.createEmail(email);

        assertThat(result).isNotNull();
        assertThat(result.getEmailText()).isEqualTo(email);
    }
}