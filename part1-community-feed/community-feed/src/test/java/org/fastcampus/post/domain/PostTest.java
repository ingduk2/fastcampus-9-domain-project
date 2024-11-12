package org.fastcampus.post.domain;

import org.assertj.core.api.Assertions;
import org.fastcampus.post.domain.content.PostContent;
import org.fastcampus.post.domain.content.PostPublicationState;
import org.fastcampus.user.domain.User;
import org.fastcampus.user.domain.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostTest {

    @Nested
    class Constructor {
        @DisplayName("검증 실패")
        @Test
        void test1() {
            PostContent postContent = new PostContent("content");

            Assertions.assertThatThrownBy(() -> new Post(1L, null, postContent))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("검증 성공")
        @Test
        void test1000() {
            // given
            PostContent postContent = new PostContent("content");
            User author = new User(1L, new UserInfo("name", "url"));

            // when
            Post result = new Post(1L, author, postContent);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getState()).isEqualTo(PostPublicationState.PUBLIC);
        }
    }

    @Nested
    class Like {
        @DisplayName("같은 user 가 like 시 실패")
        @Test
        void test1() {
            // given
            User author = new User(1L, new UserInfo("name", "url"));
            Post post = new Post(1L, author, new PostContent("content"));

            // when & Then
            assertThatThrownBy(() -> post.like(author))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("다른 user 가 like 시 성공")
        @Test
        void test1000() {
            // given
            User author = new User(1L, new UserInfo("name", "url"));
            Post post = new Post(1L, author, new PostContent("content"));
            User anotherUser = new User(2L, new UserInfo("name", "url"));

            // when
            post.like(anotherUser);

            // then
            assertThat(post.getLikeCount()).isEqualTo(1);
        }
    }

    @Nested
    class Unlike {
        @DisplayName("likeCount 가 0 인 경우에는 계속 0")
        @Test
        void test1() {
            // given
            User author = new User(1L, new UserInfo("name", "url"));
            Post post = new Post(1L, author, new PostContent("content"));

            // when
            post.unlike();

            // then
            assertThat(post.getLikeCount()).isEqualTo(0);
        }

        @DisplayName("likeCount 가 1 이상인 경우는 -1")
        @Test
        void test1000() {
            // given
            User author = new User(1L, new UserInfo("name", "url"));
            Post post = new Post(1L, author, new PostContent("content"));
            User anotherUser = new User(2L, new UserInfo("name", "url"));

            post.like(anotherUser);
            post.like(anotherUser);

            // when
            post.unlike();

            // then
            assertThat(post.getLikeCount()).isEqualTo(1);
        }
    }

    @Nested
    class UpdatePost {
        @DisplayName("다른 유저가 수정 시 실패")
        @Test
        void test1() {
            // given
            User author = new User(1L, new UserInfo("name", "url"));
            Post post = new Post(1L, author, new PostContent("content"));
            User anotherUser = new User(2L, new UserInfo("name", "url"));

            // when & Then
            assertThatThrownBy(() -> post.updatePost(anotherUser, "updateContent", PostPublicationState.ONLY_FOLLOWER))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("같은 유저가 수정 시 성공")
        @Test
        void test1000() {
            // given
            User author = new User(1L, new UserInfo("name", "url"));
            Post post = new Post(1L, author, new PostContent("content"));

            // when
            post.updatePost(author, "updateContent", PostPublicationState.ONLY_FOLLOWER);

            // then
            assertThat(post.getContent()).isEqualTo("updateContent");
            assertThat(post.getState()).isEqualTo(PostPublicationState.ONLY_FOLLOWER);
        }
    }
}