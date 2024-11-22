package com.fc;

import lombok.Getter;

import java.time.Instant;

enum NotificationType {
    LIKE,
    COMMENT,
    FOLLOW
}

@Getter
public class Notification {
    private String id;
    private Long userId;
    private NotificationType type;
    private Instant createdAt;
    private Instant deletedAt;

    public Notification(String id, Long userId, NotificationType type, Instant createdAt, Instant deletedAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
