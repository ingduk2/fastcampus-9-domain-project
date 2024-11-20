package org.fastcampus.acceptance.config;

import org.fastcampus.acceptance.utils.AcceptanceTest;
import org.fastcampus.post.repository.FakeUserPostQueueQueryRepository;
import org.fastcampus.post.repository.FakeUserQueueRedisRepository;
import org.fastcampus.post.repository.post_queue.UserPostQueueQueryRepository;
import org.fastcampus.post.repository.post_queue.UserQueueRedisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@AcceptanceTest
class AcceptanceTestConfigurationTest {

    @Autowired
    private UserPostQueueQueryRepository userPostQueueQueryRepository;
    @Autowired
    private UserQueueRedisRepository userQueueRedisRepository;

    @Test
    void test1() {
        assertThat(userPostQueueQueryRepository instanceof FakeUserPostQueueQueryRepository).isTrue();
        assertThat(userQueueRedisRepository instanceof FakeUserQueueRedisRepository).isTrue();
    }
}