package org.fastcampus.post.repository;

import org.fastcampus.post.ui.dto.GetContentResponseDto;

import java.util.List;

public interface CommentQueryRepository {
    List<GetContentResponseDto> getComments(Long postId, Long userId, Long lastCommentId);
}
