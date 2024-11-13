package org.fastcampus.user.application;

import org.fastcampus.fake.FakeObjectFactory;
import org.fastcampus.user.application.dto.CreateUserRequestDto;
import org.fastcampus.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = FakeObjectFactory.getUserService();
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
            assertThat(result.getUserInfo().getName()).isEqualTo("name1");
        }
    }
}