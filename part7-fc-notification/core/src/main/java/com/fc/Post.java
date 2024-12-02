package com.fc;

public record Post(
        Long id,
        Long userId,
        String imageUrl,
        String content
) {
}
