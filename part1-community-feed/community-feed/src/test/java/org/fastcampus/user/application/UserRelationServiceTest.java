package org.fastcampus.user.application;

import org.fastcampus.fake.FakeObjectFactory;
import org.fastcampus.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRelationServiceTest {

    private UserRelationService userRelationService;
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        userService = FakeObjectFactory.getUserService();
        userRelationService = FakeObjectFactory.getUserRelationService();

        initData();
    }

    private void initData() {
        user1 = userService.createUser(new CreateUserRequestDto("user1", "url1"));
        user2 = userService.createUser(new CreateUserRequestDto("user2", "url2"));
    }

    @Nested
    class Follow {
        @Test
        void test1() {
            FollowUserRequestDto requestDto = new FollowUserRequestDto(user1.getId(), user2.getId());

            userRelationService.follow(requestDto);
            assertThatThrownBy(() -> userRelationService.follow(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void test2() {
            FollowUserRequestDto requestDto = new FollowUserRequestDto(user1.getId(), user1.getId());

            assertThatThrownBy(() -> userRelationService.follow(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void test1000() {
            // Given
            FollowUserRequestDto requestDto = new FollowUserRequestDto(user1.getId(), user2.getId());

            // When
            userRelationService.follow(requestDto);

            // Then
            assertThat(user1.getFollowingCount()).isEqualTo(1);
            assertThat(user2.getFollowerCount()).isEqualTo(1);
        }
    }

    @Nested
    class UnFollow {
        @Test
        void test1() {
            FollowUserRequestDto requestDto = new FollowUserRequestDto(user1.getId(), user2.getId());

            assertThatThrownBy(() -> userRelationService.unFollow(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void test2() {
            FollowUserRequestDto requestDto = new FollowUserRequestDto(user1.getId(), user1.getId());

            assertThatThrownBy(() -> userRelationService.unFollow(requestDto))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void test1000() {
            // Given
            FollowUserRequestDto requestDto = new FollowUserRequestDto(user1.getId(), user2.getId());
            userRelationService.follow(requestDto);

            // When
            userRelationService.unFollow(requestDto);

            // Then
            assertThat(user1.getFollowingCount()).isEqualTo(0);
            assertThat(user2.getFollowerCount()).isEqualTo(0);
        }
    }
}