package org.fastcampus.post.domain.content;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentContentTest {

    @Nested
    class CheckTest {
        @DisplayName("검증 실패 null, Empty")
        @ParameterizedTest
        @NullAndEmptySource
        void test1(String content) {
            assertThatThrownBy(() -> new CommentContent(content))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("검증 실패")
        @ParameterizedTest
        @ValueSource(strings = {"a", "가", "뷁"})
        void test2(String text) {
            String value = text.repeat(101);
            assertThatThrownBy(() -> new CommentContent(value))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("생성 성공")
        @Test
        void test1000() {
            // given
            String text = "123452678";

            // when
            CommentContent result = new CommentContent(text);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getContentText()).isEqualTo("123452678");
        }
    }

    @Nested
    class UpdateContent {
        @DisplayName("검증 실패")
        @ParameterizedTest
        @ValueSource(strings = {"a", "가", "뷁"})
        void test1(String updateContent) {
            // given
            CommentContent commentContent = new CommentContent("123452678");
            String value = updateContent.repeat(101);

            // when & Then
            assertThatThrownBy(() -> commentContent.updateContent(value))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("성공")
        @Test
        void test1000() {
            // given
            CommentContent commentContent = new CommentContent("123452678");

            // when
            commentContent.updateContent("updateContent");

            // then
            assertThat(commentContent.getContentText()).isEqualTo("updateContent");
        }
    }
}