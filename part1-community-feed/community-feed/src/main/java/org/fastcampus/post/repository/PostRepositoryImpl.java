package org.fastcampus.post.repository;

import lombok.RequiredArgsConstructor;
import org.fastcampus.post.application.interfaces.PostRepository;
import org.fastcampus.post.domain.Post;
import org.fastcampus.post.repository.entity.post.PostEntity;
import org.fastcampus.post.repository.jpa.JpaPostRepository;
import org.fastcampus.post.repository.post_queue.UserPostQueueCommandRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final JpaPostRepository jpaPostRepository;
    private final UserPostQueueCommandRepository userPostQueueCommandRepository;

    @Transactional
    @Override
    public Post save(Post post) {
        PostEntity postEntity = new PostEntity(post);
        if (postEntity.getId() != null) {
            jpaPostRepository.updatePostEntity(postEntity);
            return postEntity.toPost();
        }
        postEntity = jpaPostRepository.save(postEntity);
        userPostQueueCommandRepository.publishPost(postEntity);
        return postEntity.toPost();
    }

    @Override
    public Post findById(Long id) {
        PostEntity entity = jpaPostRepository.findById(id).orElseThrow();
        return entity.toPost();
    }
}
