package org.fastcampus.acceptance.feed;

import org.fastcampus.acceptance.utils.AcceptanceTest;
import org.fastcampus.acceptance.utils.AcceptanceDataLoader;
import org.fastcampus.auth.application.dto.LoginRequestDto;
import org.fastcampus.post.application.dto.CreatePostRequestDto;
import org.fastcampus.post.domain.content.PostPublicationState;
import org.fastcampus.post.ui.dto.GetPostContentResponseDto;
import org.fastcampus.user.application.dto.FollowUserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fastcampus.acceptance.steps.FeedAcceptanceSteps.*;
import static org.fastcampus.acceptance.steps.LoginAcceptanceSteps.requestLogin;
import static org.fastcampus.acceptance.steps.UserAcceptanceSteps.followUser;

@AcceptanceTest
class FeedAcceptanceTest {

    @Autowired
    private AcceptanceDataLoader acceptanceDataLoader;

    /**
     * User1 --- follow ---> User2
     * User1 --- follow ---> USer3
     */
    @BeforeEach
    void setUp() {
        // user 1, 2, 3 생성
        for (int i = 1; i < 4; i++) {
            acceptanceDataLoader.createUser("user" + i + "@test.com");
        }
        followUser(new FollowUserRequestDto(1L, 2L));
        followUser(new FollowUserRequestDto(1L, 3L));
    }

    /**
     * User 2 create Post 1
     * User 1 Get Post 1 From Feed
     */
    @DisplayName("User2 가 Post 를 작성하면, User1 의 Feed 에 보이게 된다")
    @Test
    void test1() {
        // login
        String token = requestLogin(new LoginRequestDto("user1@test.com", "password"));

        // User2 Post 작성
        CreatePostRequestDto dto = new CreatePostRequestDto(2L, "user 1 can get this post", PostPublicationState.PUBLIC);
        Long createdPostId = requestPost(dto);

        // User1 Feed 조회
        List<GetPostContentResponseDto> result = requestFeed(token);

        // then
        assertThat(result).hasSize(1);
        assertThat(createdPostId).isEqualTo(result.get(0).getId());
    }

    @DisplayName("Invalid 토큰으로 요청")
    @Test
    void test2() {
        // User1 Feed 조회
        Integer code = requestFeedGetResponseCode("invalidToken");

        // then
        assertThat(code).isEqualTo(400);
    }
}
