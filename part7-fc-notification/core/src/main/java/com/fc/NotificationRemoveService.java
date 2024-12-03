package com.fc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationRemoveService {

    private final NotificationRepository notificationRepository;

    public void deleteById(String id) {
        notificationRepository.deleteById(id);
        log.info("deleted: {}", id);
    }

}
