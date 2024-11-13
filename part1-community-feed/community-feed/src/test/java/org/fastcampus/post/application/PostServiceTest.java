package org.fastcampus.post.application;

import org.fastcampus.post.application.dto.CreatePostRequestDto;
import org.fastcampus.post.application.dto.LikeRequestDto;
import org.fastcampus.post.application.dto.UpdatePostRequestDto;
import org.fastcampus.post.domain.Post;
import org.fastcampus.post.domain.content.PostPublicationState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PostServiceTest extends PostApplicationTestTemplate {

    @Nested
    class GetPost {
        @DisplayName("없는 id 실패")
        @Test
        void test1() {
            // given
            Long invalidId = 999L;

            // when & then
            assertThatThrownBy(() -> postService.getPost(invalidId))
                    .isInstanceOf(IllegalAccessError.class);
        }

        @DisplayName("있는 id 성공")
        @Test
        void test1000() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));

            // when
            Post result = postService.getPost(savedPost.getId());

            // then
            assertThat(result).isEqualTo(savedPost);
        }
    }

    @Nested
    class CreatePost {
        @DisplayName("user 가 없는 경우 실패")
        @Test
        void test1() {
            // given
            CreatePostRequestDto dto = new CreatePostRequestDto(999L, "content", PostPublicationState.PUBLIC);

            // when & then
            assertThatThrownBy(() -> postService.createPost(dto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("user 가 있는 성공")
        @Test
        void test1000() {
            // given
            CreatePostRequestDto dto = new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC);

            // when
            Post post = postService.createPost(dto);

            // then
            assertThat(post.getAuthor().getId()).isEqualTo(dto.userId());
            assertThat(post.getContent()).isEqualTo(dto.content());
            assertThat(post.getState()).isEqualTo(dto.state());
        }
    }

    @Nested
    class UpdatePost {
        @DisplayName("post 존재하지 않는 경우 실패")
        @Test
        void test1() {
            // given
            UpdatePostRequestDto requestDto = new UpdatePostRequestDto(999L, user.getId(), "updateContent", PostPublicationState.PRIVATE);

            // when & then
            assertThatThrownBy(() -> postService.updatePost(requestDto))
                    .isInstanceOf(IllegalAccessError.class);
        }

        @DisplayName("user 존재하지 않는 경우 실패")
        @Test
        void test2() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            UpdatePostRequestDto requestDto = new UpdatePostRequestDto(savedPost.getId(), 999L, "updateContent", PostPublicationState.PRIVATE);

            // when & then
            assertThatThrownBy(() -> postService.updatePost(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("post, user 존재하는 경우 성공")
        @Test
        void test1000() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            UpdatePostRequestDto requestDto = new UpdatePostRequestDto(savedPost.getId(), user.getId(), "updateContent", PostPublicationState.PRIVATE);

            // when
            Post result = postService.updatePost(requestDto);

            // then
            assertThat(result.getContent()).isEqualTo(requestDto.content());
            assertThat(result.getState()).isEqualTo(requestDto.state());
        }
    }

    @Nested
    class LikePost {
        @DisplayName("이미 like 를 한 경우에는 count 가 그대로")
        @Test
        void test1000() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            LikeRequestDto requestDto = new LikeRequestDto(savedPost.getId(), anotherUser.getId());
            postService.likePost(requestDto);

            // when
            postService.likePost(requestDto);

            // then
            assertThat(savedPost.getLikeCount()).isEqualTo(1);
        }

        @DisplayName("성공")
        @Test
        void test1001() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            LikeRequestDto requestDto = new LikeRequestDto(savedPost.getId(), anotherUser.getId());

            // when
            postService.likePost(requestDto);

            // then
            assertThat(savedPost.getLikeCount()).isEqualTo(1);
        }
    }

    @Nested
    class UnlikePost {
        @DisplayName("like 가 되어 있지 않으면, 동작하지 않음")
        @Test
        void test1000() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            LikeRequestDto requestDto = new LikeRequestDto(savedPost.getId(), anotherUser.getId());

            // when
            postService.unlikePost(requestDto);

            // then
            assertThat(savedPost.getLikeCount()).isEqualTo(0);
        }

        @DisplayName("성공")
        @Test
        void test1001() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            LikeRequestDto requestDto = new LikeRequestDto(savedPost.getId(), anotherUser.getId());
            postService.likePost(requestDto);

            // when
            postService.unlikePost(requestDto);

            // then
            assertThat(savedPost.getLikeCount()).isEqualTo(0);
        }
    }
}