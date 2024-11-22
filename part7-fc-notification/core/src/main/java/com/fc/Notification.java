package com.fc;

import java.time.Instant;

enum NotificationType {
    LIKE,
    COMMENT,
    FOLLOW
}

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

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }
}
