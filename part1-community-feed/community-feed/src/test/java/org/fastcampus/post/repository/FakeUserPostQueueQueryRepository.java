package org.fastcampus.post.repository;

import lombok.RequiredArgsConstructor;
import org.fastcampus.post.repository.post_queue.UserPostQueueQueryRepository;
import org.fastcampus.post.ui.dto.GetPostContentResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("test")
@RequiredArgsConstructor
public class FakeUserPostQueueQueryRepository implements UserPostQueueQueryRepository {

    private final FakeUserQueueRedisRepository fakeUserQueueRedisRepository;

    @Override
    public List<GetPostContentResponseDto> getPostList(Long userId, Long lastPostId) {
        return fakeUserQueueRedisRepository.getPostsByUserId(userId).stream()
                .map(it -> GetPostContentResponseDto.builder()
                        .id(it.getId())
                        .build())
                .collect(Collectors.toList());
    }
}
