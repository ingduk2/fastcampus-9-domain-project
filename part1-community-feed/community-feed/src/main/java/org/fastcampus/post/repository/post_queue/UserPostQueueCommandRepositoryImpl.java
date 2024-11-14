package org.fastcampus.post.repository.post_queue;

import lombok.RequiredArgsConstructor;
import org.fastcampus.post.repository.entity.post.PostEntity;
import org.fastcampus.post.repository.entity.post.UserPostQueueEntity;
import org.fastcampus.post.repository.jpa.JpaPostRepository;
import org.fastcampus.post.repository.jpa.JpaUserPostQueueRepository;
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
    private final JpaUserPostQueueRepository jpaUserPostQueueRepository;

    @Transactional
    @Override
    public void publishPost(PostEntity postEntity) {
        UserEntity author = postEntity.getAuthor();
        List<Long> followersIds = jpaUserRelationRepository.findFollowers(author.getId());

        List<UserPostQueueEntity> userPostQueueEntities = followersIds.stream()
                .map(userId -> new UserPostQueueEntity(userId, postEntity.getId(), author.getId()))
                .toList();

        jpaUserPostQueueRepository.saveAll(userPostQueueEntities);
    }

    @Transactional
    @Override
    public void saveFollowPost(Long userId, Long targetId) {
        List<Long> postIds = jpaPostRepository.findAllPostIdsByAuthorId(targetId);

        List<UserPostQueueEntity> userPostQueueEntities = postIds.stream()
                .map(postId -> new UserPostQueueEntity(userId, postId, targetId))
                .toList();

        jpaUserPostQueueRepository.saveAll(userPostQueueEntities);
    }

    @Transactional
    @Override
    public void deleteUnfollowPost(Long userId, Long targetId) {
        jpaUserPostQueueRepository.deleteAllByUserIdAndAuthorId(userId, targetId);
    }
}
