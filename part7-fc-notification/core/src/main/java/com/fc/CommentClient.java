package com.fc;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class CommentClient {

    private final Map<Long, Comment> comments = new HashMap<>();

    public CommentClient() {
        comments.put(1L, new Comment(1L, 11L, "content1", Instant.now()));
        comments.put(2L, new Comment(2L, 22L, "content2", Instant.now()));
        comments.put(3L, new Comment(3L, 33L, "content3", Instant.now()));
    }

    public Comment getComment(Long id) {
        return comments.get(id);
    }
}
