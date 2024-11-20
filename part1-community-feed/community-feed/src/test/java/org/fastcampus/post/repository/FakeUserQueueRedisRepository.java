package org.fastcampus.post.repository;

import org.fastcampus.post.repository.entity.post.PostEntity;
import org.fastcampus.post.repository.post_queue.UserQueueRedisRepository;

import java.util.*;

public class FakeUserQueueRedisRepository implements UserQueueRedisRepository {

    private final Map<Long, Set<PostEntity>> queue = new HashMap<>();

    @Override
    public void publishPostToFollowingUserList(PostEntity postEntity, List<Long> userIdList) {
        for (Long userId: userIdList) {
            if (queue.containsKey(userId)) {
                queue.get(userId).add(postEntity);
            } else {
                queue.put(userId, new HashSet<>(List.of(postEntity)));
            }
        }
    }

    @Override
    public void publishPostListToFollowerUser(List<PostEntity> postEntities, Long userId) {
        if (queue.containsKey(userId)){
            queue.get(userId).addAll(postEntities);
        } else {
            queue.put(userId, new HashSet<>(postEntities));
        }
    }

    @Override
    public void deleteFeed(Long userId, Long targetUserId) {
        if (queue.containsKey(userId)) {
            queue.get(userId).removeIf(post -> post.getAuthor().getId().equals(targetUserId));
        }
    }

    public List<PostEntity> getPostsByUserId(Long userId) {
        return List.copyOf(queue.get(userId));
    }
}
