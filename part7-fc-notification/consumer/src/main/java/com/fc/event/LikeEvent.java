package com.fc.event;

import java.time.Instant;

public record LikeEvent(
        LikeEventType type,
        Long postId,
        Long userId,
        Instant createdAt
) {
}
