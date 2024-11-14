package org.fastcampus.post.repository;

import lombok.RequiredArgsConstructor;
import org.fastcampus.post.application.interfaces.CommentRepository;
import org.fastcampus.post.domain.Post;
import org.fastcampus.post.domain.comment.Comment;
import org.fastcampus.post.repository.entity.comment.CommentEntity;
import org.fastcampus.post.repository.jpa.JpaCommentRepository;
import org.fastcampus.post.repository.jpa.JpaPostRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;
    private final JpaPostRepository jpaPostRepository;

    @Transactional
    @Override
    public Comment save(Comment comment) {
        CommentEntity commentEntity = new CommentEntity(comment);
        if (comment.getId() != null) {
            jpaCommentRepository.updateCommentEntity(commentEntity);
            return comment;
        }

        commentEntity = jpaCommentRepository.save(commentEntity);
        Post targetPost = comment.getPost();
        jpaPostRepository.increaseCommentCount(targetPost.getId());
        return commentEntity.toComment();
    }

    @Override
    public Comment findById(Long id) {
        CommentEntity commentEntity = jpaCommentRepository.findById(id).orElseThrow();
        return commentEntity.toComment();
    }
}
