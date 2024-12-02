package com.fc;

import java.time.Instant;

public record Comment(
        Long id,
        Long userId,
        String content,
        Instant createdAt
) {
}
