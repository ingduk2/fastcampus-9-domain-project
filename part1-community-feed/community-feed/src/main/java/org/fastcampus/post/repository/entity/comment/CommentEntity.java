package org.fastcampus.post.repository.entity.comment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fastcampus.common.domain.PositiveIntegerCounter;
import org.fastcampus.common.repository.TimeBaseEntity;
import org.fastcampus.post.domain.comment.Comment;
import org.fastcampus.post.domain.content.CommentContent;
import org.fastcampus.post.repository.entity.post.PostEntity;
import org.fastcampus.user.repository.entity.UserEntity;

@Entity
@Table(name = "community_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CommentEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "authorId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "postId", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PostEntity post;

    private String content;
    private Integer likeCount;

    public CommentEntity(Comment comment) {
        this.id = comment.getId();
        this.author = new UserEntity(comment.getAuthor());
        this.post = new PostEntity(comment.getPost());
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
    }

    public Comment toComment() {
        return Comment.builder()
                .id(id)
                .post(post.toPost())
                .author(author.toUser())
                .content(new CommentContent(content))
                .likeCount(new PositiveIntegerCounter(likeCount))
                .build();
    }
}
