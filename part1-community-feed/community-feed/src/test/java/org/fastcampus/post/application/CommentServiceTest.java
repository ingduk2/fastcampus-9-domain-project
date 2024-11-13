package org.fastcampus.post.application;

import org.fastcampus.post.application.dto.CreateCommentRequestDto;
import org.fastcampus.post.application.dto.CreatePostRequestDto;
import org.fastcampus.post.application.dto.LikeRequestDto;
import org.fastcampus.post.application.dto.UpdateCommentRequestDto;
import org.fastcampus.post.domain.Post;
import org.fastcampus.post.domain.comment.Comment;
import org.fastcampus.post.domain.content.PostPublicationState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentServiceTest extends PostApplicationTestTemplate {

    @Nested
    class GetPost {
        @DisplayName("없는 id 실패")
        @Test
        void test1() {
            // given
            Long invalidId = 999L;

            // when & then
            assertThatThrownBy(() -> commentService.getComment(invalidId))
                    .isInstanceOf(IllegalAccessError.class);
        }

        @DisplayName("성공")
        @Test
        void test1000() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            Comment savedComment = commentService.createComment(new CreateCommentRequestDto(savedPost.getId(), user.getId(), "content"));

            // when
            Comment result = commentService.getComment(savedComment.getId());


            // then
            assertThat(result).isEqualTo(savedComment);
        }
    }

    @Nested
    class CreatePost {
        @DisplayName("없는 post 의 경우 실패")
        @Test
        void test1() {
            // given
            CreateCommentRequestDto requestDto = new CreateCommentRequestDto(999L, user.getId(), "content");

            // when & then
            assertThatThrownBy(() -> commentService.createComment(requestDto))
                    .isInstanceOf(IllegalAccessError.class);
        }

        @DisplayName("성공")
        @Test
        void test1000() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            CreateCommentRequestDto requestDto = new CreateCommentRequestDto(savedPost.getId(), user.getId(), "content");

            // when
            Comment result = commentService.createComment(requestDto);

            // then
            assertThat(result.getContent()).isEqualTo(requestDto.content());
        }
    }

    @Nested
    class UpdateComment {
        @DisplayName("없는 comment 의 경우 실패")
        @Test
        void test1() {
            UpdateCommentRequestDto requestDto = new UpdateCommentRequestDto(999L, user.getId(), "updateContent");

            // when
            assertThatThrownBy(() -> commentService.updateComment(requestDto))
                    .isInstanceOf(IllegalAccessError.class);
        }

        @DisplayName("없는 user 의 경우 실패")
        @Test
        void test2() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            Comment savedComment = commentService.createComment(new CreateCommentRequestDto(savedPost.getId(), user.getId(), "content"));
            UpdateCommentRequestDto requestDto = new UpdateCommentRequestDto(savedComment.getId(), 999L, "updateContent");

            // when & then
            assertThatThrownBy(() -> commentService.updateComment(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("성공")
        @Test
        void test1000() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            Comment savedComment = commentService.createComment(new CreateCommentRequestDto(savedPost.getId(), user.getId(), "content"));
            UpdateCommentRequestDto requestDto = new UpdateCommentRequestDto(savedComment.getId(), user.getId(), "updateContent");

            // when
            Comment result = commentService.updateComment(requestDto);

            // then
            assertThat(result.getContent()).isEqualTo("updateContent");
        }
    }

    @Nested
    class Like {
        @DisplayName("없는 comment like 실패")
        @Test
        void test1() {
            // given
            LikeRequestDto requestDto = new LikeRequestDto(999L, user.getId());

            // when & then
            assertThatThrownBy(() -> commentService.likeComment(requestDto))
                    .isInstanceOf(IllegalAccessError.class);
        }

        @DisplayName("같은 user 가 like 하면 실패")
        @Test
        void test3() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            Comment savedComment = commentService.createComment(new CreateCommentRequestDto(savedPost.getId(), user.getId(), "content"));

            LikeRequestDto requestDto = new LikeRequestDto(savedComment.getId(), user.getId());

            // when & then
            assertThatThrownBy(() -> commentService.likeComment(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("성공")
        @Test
        void test1000() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            Comment savedComment = commentService.createComment(new CreateCommentRequestDto(savedPost.getId(), user.getId(), "content"));

            LikeRequestDto requestDto = new LikeRequestDto(savedComment.getId(), anotherUser.getId());

            // when
            commentService.likeComment(requestDto);

            // then
            assertThat(savedComment.getLikeCount()).isEqualTo(1);
        }

        @DisplayName("이미 like 한 경우 무시")
        @Test
        void test1001() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            Comment savedComment = commentService.createComment(new CreateCommentRequestDto(savedPost.getId(), user.getId(), "content"));

            LikeRequestDto requestDto = new LikeRequestDto(savedComment.getId(), anotherUser.getId());

            // when
            commentService.likeComment(requestDto);
            commentService.likeComment(requestDto);

            // then
            assertThat(savedComment.getLikeCount()).isEqualTo(1);
        }
    }

    @Nested
    class Unlike {
        @DisplayName("없는 comment 실패")
        @Test
        void test1() {
            // given
            LikeRequestDto requestDto = new LikeRequestDto(999L, user.getId());

            // when & then
            assertThatThrownBy(() -> commentService.unlikeComment(requestDto))
                    .isInstanceOf(IllegalAccessError.class);
        }

        @DisplayName("성공")
        @Test
        void test1000() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            Comment savedComment = commentService.createComment(new CreateCommentRequestDto(savedPost.getId(), user.getId(), "content"));

            LikeRequestDto requestDto = new LikeRequestDto(savedComment.getId(), anotherUser.getId());
            commentService.likeComment(requestDto);

            // when
            commentService.unlikeComment(requestDto);

            // then
            assertThat(savedComment.getLikeCount()).isEqualTo(0);
        }

        @DisplayName("like 가 되어 있지 않으면 무시")
        @Test
        void test1001() {
            // given
            Post savedPost = postService.createPost(new CreatePostRequestDto(user.getId(), "content", PostPublicationState.PUBLIC));
            Comment savedComment = commentService.createComment(new CreateCommentRequestDto(savedPost.getId(), user.getId(), "content"));

            LikeRequestDto requestDto = new LikeRequestDto(savedComment.getId(), anotherUser.getId());

            // when
            commentService.unlikeComment(requestDto);

            // then
            assertThat(savedComment.getLikeCount()).isEqualTo(0);
        }
    }

}