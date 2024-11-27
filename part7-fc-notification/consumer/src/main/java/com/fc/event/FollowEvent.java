package com.fc.event;

import java.time.Instant;

public record FollowEvent(
        FollowEventType type,
        Long userId,
        Long targetUserId,
        Instant createdAt
) {
}
