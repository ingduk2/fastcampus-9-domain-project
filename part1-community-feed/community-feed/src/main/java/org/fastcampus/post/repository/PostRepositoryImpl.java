package org.fastcampus.post.repository;

import lombok.RequiredArgsConstructor;
import org.fastcampus.post.application.interfaces.PostRepository;
import org.fastcampus.post.domain.Post;
import org.fastcampus.post.repository.entity.post.PostEntity;
import org.fastcampus.post.repository.jpa.JpaPostRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final JpaPostRepository jpaPostRepository;

    @Transactional
    @Override
    public Post save(Post post) {
        PostEntity entity = new PostEntity(post);
        if (entity.getId() != null) {
            jpaPostRepository.updatePostEntity(entity);
            return entity.toPost();
        }
        entity = jpaPostRepository.save(entity);
        return entity.toPost();
    }

    @Override
    public Post findById(Long id) {
        PostEntity entity = jpaPostRepository.findById(id).orElseThrow();
        return entity.toPost();
    }
}
