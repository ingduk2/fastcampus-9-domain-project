package com.fc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootApplication
@SpringBootTest
class NotificationRepositoryMemoryImplTest {

    @Autowired
    private NotificationRepository sut;

    private final Instant now = Instant.now();
    private final Instant deletedAt = Instant.now().plus(90, ChronoUnit.DAYS);

    @Nested
    class Save {
        @DisplayName("저장 성공")
        @Test
        void test1000() {
            // given
            Notification notification = new Notification("1", 1L, NotificationType.COMMENT, now, deletedAt);

            // when
            sut.save(notification);

            // then
            Optional<Notification> optionalNotification = sut.findById(notification.getId());
            assertThat(optionalNotification).isPresent();
        }
    }

    @Nested
    class FindById {
        @DisplayName("검색 성공")
        @Test
        void test1000() {
            // given
            Notification notification = new Notification("1", 1L, NotificationType.COMMENT, now, deletedAt);
            Notification savedNotification = sut.save(notification);

            // when
            Optional<Notification> optionalNotification = sut.findById(savedNotification.getId());

            // then
            Notification findedNotification = optionalNotification.get();
            assertThat(findedNotification.getId()).isEqualTo(savedNotification.getId());
            assertThat(findedNotification.getUserId()).isEqualTo(savedNotification.getUserId());
            assertThat(findedNotification.getCreatedAt().getEpochSecond()).isEqualTo(now.getEpochSecond());
            assertThat(findedNotification.getDeletedAt().getEpochSecond()).isEqualTo(deletedAt.getEpochSecond());
        }
    }

    @Nested
    class Delete {
        @DisplayName("삭제 성공")
        @Test
        void test1000() {
            // given
            Notification notification = new Notification("1", 1L, NotificationType.COMMENT, now, deletedAt);
            Notification savedNotification = sut.save(notification);

            // when
            sut.deleteById(savedNotification.getId());

            // then
            Optional<Notification> optionalNotification = sut.findById(savedNotification.getId());
            assertThat(optionalNotification).isNotPresent();
        }
    }
}