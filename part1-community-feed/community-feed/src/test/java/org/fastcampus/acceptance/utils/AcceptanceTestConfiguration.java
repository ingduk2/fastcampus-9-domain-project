package org.fastcampus.acceptance.utils;

import org.fastcampus.post.repository.FakeUserPostQueueQueryRepository;
import org.fastcampus.post.repository.FakeUserQueueRedisRepository;
import org.fastcampus.post.repository.post_queue.UserPostQueueQueryRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class AcceptanceTestConfiguration {

    @Bean
    @Primary
    public UserPostQueueQueryRepository userPostQueueQueryRepository() {
        return new FakeUserPostQueueQueryRepository(userQueueRedisRepository());
    }

    @Bean
    @Primary
    public FakeUserQueueRedisRepository userQueueRedisRepository() {
        return new FakeUserQueueRedisRepository();
    }
}
