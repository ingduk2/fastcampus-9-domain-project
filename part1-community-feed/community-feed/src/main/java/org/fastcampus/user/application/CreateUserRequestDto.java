package org.fastcampus.user.application;

public record CreateUserRequestDto(
        String name,
        String profileImageUrl
) {
}
