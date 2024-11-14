package org.fastcampus.post.repository.post_queue;

import lombok.RequiredArgsConstructor;
import org.fastcampus.post.repository.entity.post.PostEntity;
import org.fastcampus.post.repository.jpa.JpaPostRepository;
import org.fastcampus.user.repository.entity.UserEntity;
import org.fastcampus.user.repository.jpa.JpaUserRelationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserPostQueueCommandRepositoryImpl implements UserPostQueueCommandRepository {

    private final JpaPostRepository jpaPostRepository;
    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final UserQueueRedisRepository userQueueRedisRepository;

    @Transactional
    @Override
    public void publishPost(PostEntity postEntity) {
        UserEntity author = postEntity.getAuthor();
        List<Long> followersIds = jpaUserRelationRepository.findFollowers(author.getId());

        userQueueRedisRepository.publishPostToFollowingUserList(postEntity, followersIds);
    }

    @Transactional
    @Override
    public void saveFollowPost(Long userId, Long targetId) {
        List<PostEntity> postEntities = jpaPostRepository.findAllByAuthorId(targetId);

        userQueueRedisRepository.publishPostListToFollowerUser(postEntities, userId);
    }

    @Transactional
    @Override
    public void deleteUnfollowPost(Long userId, Long targetId) {
        userQueueRedisRepository.deleteFeed(userId, targetId);
    }
}
