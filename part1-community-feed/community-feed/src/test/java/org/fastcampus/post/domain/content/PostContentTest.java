package org.fastcampus.post.domain.content;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostContentTest {

    @Nested
    class CheckTest {
        @DisplayName("검증 실패")
        @ParameterizedTest
        @NullAndEmptySource
        void test1(String text) {
            assertThatThrownBy(() -> new PostContent(text))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("검증 실패")
        @ParameterizedTest
        @ValueSource(strings = {"a", "가", "뷁"})
        void test2(String text) {
            String value = text.repeat(501);
            assertThatThrownBy(() -> new PostContent(value))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("생성 성공")
        @Test
        void test1000() {
            // given
            String text = "123452678";

            // when
            PostContent result = new PostContent(text);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getContentText()).isEqualTo("123452678");
        }
    }

    @Nested
    class UpdateContent {
        @DisplayName("검증 실패")
        @ParameterizedTest
        @NullAndEmptySource
        void test1(String updateContent) {
            // given
            PostContent postContent = new PostContent("123452678");

            // when & Then
            assertThatThrownBy(() -> postContent.updateContent(updateContent))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("검증 실패")
        @ParameterizedTest
        @ValueSource(strings = {"a", "가", "뷁"})
        void test2(String updateContent) {
            // given
            PostContent postContent = new PostContent("123452678");
            String value = updateContent.repeat(501);

            // when & Then
            assertThatThrownBy(() -> postContent.updateContent(value))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("성공")
        @Test
        void test1000() {
            // given
            PostContent postContent = new PostContent("123452678");

            // when
            postContent.updateContent("updateContent");

            // then
            assertThat(postContent.getContentText()).isEqualTo("updateContent");
        }
    }
}