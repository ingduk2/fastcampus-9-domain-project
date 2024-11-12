package org.fastcampus.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    private final UserInfo userInfo = new UserInfo("test", "");
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, userInfo);
        user2 = new User(2L, userInfo);
    }

    @Nested
    class Equals {
        @Test
        void test1() {
            // When
            boolean result = user1.equals(user2);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        void test1000() {
            // Given
            User sameUser = new User(1L, userInfo);

            // When
            boolean result = user1.equals(sameUser);

            // Then
            assertThat(result).isTrue();
        }
    }

    @Nested
    class HashCode {
        @Test
        void test1() {
            assertThat(user1.hashCode()).isNotEqualTo(user2.hashCode());
        }

        @Test
        void test2() {
            User sameUser = new User(1L, userInfo);
            assertThat(user1.hashCode()).isEqualTo(sameUser.hashCode());
        }
    }

    @Nested
    class follow {

        @Test
        void test1() {
            // Given

            // When & Then
            assertThatThrownBy(() -> user1.follow(user1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void test1000() {
            // Given

            // When
            user1.follow(user2);

            // Then
            assertThat(user1.getFollowingCount()).isEqualTo(1);
            assertThat(user1.getFollowerCount()).isEqualTo(0);
            assertThat(user2.getFollowingCount()).isEqualTo(0);
            assertThat(user2.getFollowerCount()).isEqualTo(1);
        }
    }

    @Nested
    class UnFollow {
        @Test
        void test1() {
            // Given

            // When & Then
            assertThatThrownBy(() -> user1.unfollow(user1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void test1000() {
            // Given
            user1.follow(user2);

            // When
            user1.unfollow(user2);

            // Then
            assertThat(user1.getFollowingCount()).isEqualTo(0);
            assertThat(user1.getFollowerCount()).isEqualTo(0);
            assertThat(user2.getFollowingCount()).isEqualTo(0);
            assertThat(user2.getFollowerCount()).isEqualTo(0);
        }

        @Test
        void test1001() {
            // Given

            // When
            user1.unfollow(user2);

            // Then
            assertThat(user1.getFollowingCount()).isEqualTo(0);
            assertThat(user1.getFollowerCount()).isEqualTo(0);
            assertThat(user2.getFollowingCount()).isEqualTo(0);
            assertThat(user2.getFollowerCount()).isEqualTo(0);
        }
    }
}