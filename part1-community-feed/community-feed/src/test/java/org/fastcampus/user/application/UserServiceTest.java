package org.fastcampus.user.application;

import org.fastcampus.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();
        userService = new UserService(userRepository);
    }

    @Nested
    class CreateUser {
        @Test
        void test1000() {
            // Given
            CreateUserRequestDto dto = new CreateUserRequestDto("name1", "url1");

            // When
            User result = userService.createUser(dto);

            // Then
            User foundUser = userService.getUser(result.getId());
            assertThat(result.getId()).isEqualTo(foundUser.getId());
            assertThat(result.getInfo().getName()).isEqualTo("name1");
        }
    }
}