package org.fastcampus.user.application.dto;

public record CreateUserRequestDto(
        String name,
        String profileImageUrl
) {
}
