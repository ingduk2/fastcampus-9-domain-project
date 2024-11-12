package org.fastcampus.post.domain.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


class DatetimeInfoTest {

    @Nested
    class Constructor {
        @DisplayName("생성자 테스트")
        @Test
        void test1000() {
            // given

            // when
            LocalDateTime now = LocalDateTime.now();
            DatetimeInfo datetimeInfo = new DatetimeInfo(true, now);

            // then
            assertThat(datetimeInfo.isEdited()).isTrue();
            assertThat(datetimeInfo.getDateTime()).isEqualTo(now);
        }

        @DisplayName("생성자 테스트2")
        @Test
        void test1001() {
            // given

            // when
            DatetimeInfo datetimeInfo = new DatetimeInfo();

            // then
            assertThat(datetimeInfo.isEdited()).isFalse();
            assertThat(datetimeInfo.getDateTime()).isAfter(LocalDateTime.now().minusSeconds(1));
        }
    }

    @Nested
    class UpdateEditDateTime {
        @DisplayName("update")
        @Test
        void test1000() {
            // given
            DatetimeInfo datetimeInfo = new DatetimeInfo();

            // when
            datetimeInfo.updateEditDateTime();

            // then
            assertThat(datetimeInfo.isEdited()).isTrue();
        }
    }
}