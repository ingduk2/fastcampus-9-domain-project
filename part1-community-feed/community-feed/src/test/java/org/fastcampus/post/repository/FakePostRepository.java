package org.fastcampus.post.repository;

import org.fastcampus.post.application.interfaces.PostRepository;
import org.fastcampus.post.domain.Post;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakePostRepository implements PostRepository {

    private final Map<Long, Post> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Post save(Post post) {
        if (post.getId() != null) {
            store.put(post.getId(), post);
            return post;
        }

        long id = idGenerator.incrementAndGet();
        Post newPost = Post.createDefaultPost(id, post.getAuthor(), post.getContent());
        store.put(id, newPost);
        return newPost;
    }

    @Override
    public Post findById(Long id) {
        return store.get(id);
    }
}