package org.fastcampus.acceptance;

import org.fastcampus.acceptance.utils.AcceptanceTestTemplate;
import org.fastcampus.post.application.dto.CreatePostRequestDto;
import org.fastcampus.post.domain.content.PostPublicationState;
import org.fastcampus.post.ui.dto.GetPostContentResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fastcampus.acceptance.steps.FeedAcceptanceSteps.requestFeed;
import static org.fastcampus.acceptance.steps.FeedAcceptanceSteps.requestPost;

class FeedAcceptanceTest extends AcceptanceTestTemplate {
    /**
     * User1 --- follow ---> User2
     * User1 --- follow ---> USer3
     */
    @BeforeEach
    void setUp() {

    }

    /**
     * User 2 create Post 1
     * User 1 Get Post 1 From Feed
     */
    @DisplayName("User2 가 Post 를 작성하면, User1 의 Feed 에 보이게 된다")
    @Test
    void test1() {
        // User2 Post 작성
        CreatePostRequestDto dto = new CreatePostRequestDto(2L, "user 1 can get this post", PostPublicationState.PUBLIC);
        Long createdPostId = requestPost(dto);

        // User1 Feed 조회
        List<GetPostContentResponseDto> result = requestFeed(1L);

        // then
        assertThat(result).hasSize(1);
        assertThat(createdPostId).isEqualTo(result.get(0).getId());
    }
}
