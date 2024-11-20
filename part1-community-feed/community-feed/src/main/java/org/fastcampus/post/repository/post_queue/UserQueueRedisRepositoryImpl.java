package org.fastcampus.post.repository.post_queue;

import org.fastcampus.post.repository.entity.post.PostEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserQueueRedisRepositoryImpl implements UserQueueRedisRepository {
    @Override
    public void publishPostToFollowingUserList(PostEntity postEntity, List<Long> userIdList) {

    }

    @Override
    public void publishPostListToFollowerUser(List<PostEntity> postEntities, Long userId) {

    }

    @Override
    public void deleteFeed(Long userId, Long authorId) {

    }
}
