package com.fc.task;

import com.fc.*;
import com.fc.event.CommentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommentAddTask {

    private final PostClient postClient;
    private final CommentClient commentClient;
    private final NotificationSaveService notificationSaveService;

    public void processEvent(CommentEvent event) {
        // 내가 작성한 댓글인 경우 무시
        Post post = postClient.getPost(event.postId());
        if (Objects.equals(post.userId(), event.userId())) {
            return;
        }

        Comment comment = commentClient.getComment(event.commentId());

        // 알림 생성
        Notification notification = createNotification(post, comment);
        notificationSaveService.insert(notification);
    }

    private Notification createNotification(Post post, Comment comment) {
        Instant now = Instant.now();

        return new CommentNotification(
                NotificationIdGenerator.generate(),
                post.userId(),
                NotificationType.COMMENT,
                comment.createdAt(),
                now,
                now,
                now.plus(90, ChronoUnit.DAYS),
                post.id(),
                comment.userId(),
                comment.content(),
                comment.id()
        );
    }
}
