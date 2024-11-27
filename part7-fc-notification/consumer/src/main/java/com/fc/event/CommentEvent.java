package com.fc.event;

public record CommentEvent(
        CommentEventType type,
        Long postId,
        Long userId,
        Long commentId
) {
}
