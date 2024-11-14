package org.fastcampus.post.repository.entity.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fastcampus.common.domain.PositiveIntegerCounter;
import org.fastcampus.common.repository.TimeBaseEntity;
import org.fastcampus.post.domain.Post;
import org.fastcampus.post.domain.content.PostContent;
import org.fastcampus.post.domain.content.PostPublicationState;
import org.fastcampus.user.repository.entity.UserEntity;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "community_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PostEntity extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity author;

    private String content;

    private Integer likeCount;

    @ColumnDefault("0")
    private int commentCount;

    @Convert(converter = PostPublicationStateConverter.class)
    private PostPublicationState state;

    public PostEntity(Post post) {
        this.id = post.getId();
        this.author = new UserEntity(post.getAuthor());
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.state = post.getState();
    }

    public Post toPost() {
        return Post.builder()
                .id(id)
                .author(author.toUser())
                .content(new PostContent(content))
                .likeCount(new PositiveIntegerCounter(likeCount))
                .state(state)
                .build();
    }
}
