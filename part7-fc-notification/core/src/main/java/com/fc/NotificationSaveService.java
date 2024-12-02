package com.fc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSaveService {

    private final NotificationRepository notificationRepository;

    public void insert(Notification notification) {
        Notification insertedNotification = notificationRepository.insert(notification);
        log.info("inserted: {}", insertedNotification);
    }
}
