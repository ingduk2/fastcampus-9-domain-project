package org.fastcampus.post.domain.comment;

import org.fastcampus.post.domain.Post;
import org.fastcampus.post.domain.content.CommentContent;
import org.fastcampus.post.domain.content.Content;
import org.fastcampus.post.domain.content.PostContent;
import org.fastcampus.user.domain.User;
import org.fastcampus.user.domain.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentTest {

    @Nested
    class Constructor {
        static Stream<Arguments> source() {
            User user = new User(1L, new UserInfo("name", "url"));
            Post post = Post.createDefaultPost(1L, user, "postContent");
            CommentContent commentContent = new CommentContent("commandContent");
            return Stream.of(
                    Arguments.of(1L, null, null, null),
                    Arguments.of(1L, post, null, null),
                    Arguments.of(1L, null, user, null),
                    Arguments.of(1L, null, null, commentContent)
            );
        }

        @DisplayName("생성자")
        @ParameterizedTest
        @MethodSource("source")
        void test1(Long id, Post post, User author, Content content) {
            // given

            // when & Then
            assertThatThrownBy(() -> new Comment(id, post, author, content))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class Like {
        @DisplayName("같은 user 가 like 실패")
        @Test
        void test1() {
            // given
            User sameUser = new User(1L, new UserInfo("name", "url"));
            Comment comment = createComment(sameUser);

            // when & Then
            assertThatThrownBy(() -> comment.like(sameUser))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("다른 user 가 like 성공")
        @Test
        void test100() {
            // given
            Comment comment = createComment(1L, 1L);
            User anotherUser = new User(2L, new UserInfo("name", "url"));

            // when
            comment.like(anotherUser);

            // then
            assertThat(comment.getLikeCount()).isEqualTo(1);
        }
    }

    @Nested
    class UnLike {
        @DisplayName("likeCount 가 0 인 경우에는 계속 0")
        @Test
        void test1() {
            // given
            Comment comment = createComment(1L, 1L);

            // when
            comment.unlike();

            // then
            assertThat(comment.getLikeCount()).isEqualTo(0);
        }

        @DisplayName("likeCount 가 1 이상인 경우는 -1 이 된다")
        @Test
        void test100() {
            // given
            Comment comment = createComment(1L, 1L);
            User anotherUser = new User(2L, new UserInfo("name", "url"));
            comment.like(anotherUser);
            comment.like(anotherUser);

            // when
            comment.unlike();

            // then
            assertThat(comment.getLikeCount()).isEqualTo(1);
        }
    }

    @Nested
    class UpdateComment {
        @DisplayName("다른 사용자가 update 시 실패")
        @Test
        void test1() {
            // given
            Comment comment = createComment(1L, 1L);
            User anotherUser = new User(2L, new UserInfo("name", "url"));

            // when & Then
            assertThatThrownBy(() -> comment.updateComment(anotherUser, "updateContent"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("같은 사용자가 update 시 성공")
        @Test
        void test100() {
            // given
            User sameUser = new User(1L, new UserInfo("name", "url"));
            Comment comment = createComment(sameUser);

            // when
            comment.updateComment(sameUser, "updateContent");

            // then
            assertThat(comment.getContent()).isEqualTo("updateContent");
        }
    }

    private Comment createComment(User user) {
        Post post = Post.createDefaultPost(1L, user, "postContent");
        CommentContent commentContent = new CommentContent("commandContent");
        return new Comment(1L, post, user, commentContent);
    }

    private static Comment createComment(long commentId, long userId) {
        User user = new User(userId, new UserInfo("name", "url"));
        Post post = Post.createDefaultPost(1L, user, "postContent");
        CommentContent commentContent = new CommentContent("commandContent");
        return new Comment(commentId, post, user, commentContent);
    }
}