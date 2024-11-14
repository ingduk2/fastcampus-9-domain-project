package org.fastcampus.post.repository.post_queue;

import org.fastcampus.post.repository.entity.post.PostEntity;

import java.util.List;

public interface UserQueueRedisRepository {
    void publishPostToFollowingUserList(PostEntity postEntity, List<Long> userIdList);

    void publishPostListToFollowerUser(List<PostEntity> postEntities, Long userId);

    void deleteFeed(Long userId, Long authorId);
}
