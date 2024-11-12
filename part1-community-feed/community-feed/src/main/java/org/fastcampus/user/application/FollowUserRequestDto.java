package org.fastcampus.user.application;

public record FollowUserRequestDto(
        Long userId,
        Long targetUserId
) {
}
