package com.fc.task;

import com.fc.*;
import com.fc.event.CommentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentRemoveTask {

    private final PostClient postClient;
    private final CommentClient commentClient;
    private final NotificationGetService notificationGetService;
    private final NotificationRemoveService notificationRemoveService;

    public void processEvent(CommentEvent event) {
        // 내가 작성한 댓글인 경우 무시
        Post post = postClient.getPost(event.postId());
        if (Objects.equals(post.userId(), event.userId())) {
            return;
        }

        notificationGetService.getNotification(NotificationType.COMMENT, event.commentId())
                .ifPresentOrElse(
                        notification -> notificationRemoveService.deleteById(notification.getId()),
                        () -> log.error("Notification not found")
                );
    }
}
