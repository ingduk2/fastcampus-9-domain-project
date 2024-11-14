package org.fastcampus.post.repository.post_queue;

import org.fastcampus.post.ui.dto.GetPostContentResponseDto;

import java.util.List;

public interface UserPostQueueQueryRepository {
    List<GetPostContentResponseDto> getPostList(Long userId, Long lastPostId);
}
