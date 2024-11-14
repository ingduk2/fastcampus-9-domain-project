package org.fastcampus.post.repository.post_queue;

import org.fastcampus.post.repository.entity.post.PostEntity;

public interface UserPostQueueCommandRepository {
    /*
    글을 작성 했을 때, 나를 팔로우 하는 user 들에게 글을 넣어줌
     */
    void publishPost(PostEntity postEntity);

    /*
    팔로우 했을 때, 팔로우 한 사람의 글을 내 피드로 넣어줌
     */
    void saveFollowPost(Long userId, Long targetId);

    /*
    언팔로우 했을 때, 글을 지워줌
     */
    void deleteUnfollowPost(Long userId, Long targetId);
}
