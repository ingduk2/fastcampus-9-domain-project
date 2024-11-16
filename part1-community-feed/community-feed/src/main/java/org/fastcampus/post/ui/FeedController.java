package org.fastcampus.post.ui;

import lombok.RequiredArgsConstructor;
import org.fastcampus.common.principal.AuthPrincipal;
import org.fastcampus.common.principal.UserPrincipal;
import org.fastcampus.common.ui.Response;
import org.fastcampus.post.repository.post_queue.UserPostQueueQueryRepository;
import org.fastcampus.post.ui.dto.GetPostContentResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final UserPostQueueQueryRepository userPostQueueQueryRepository;

    @GetMapping()
    public Response<List<GetPostContentResponseDto>> getPostFeed(@AuthPrincipal UserPrincipal userPrincipal, Long lastContentId) {
        List<GetPostContentResponseDto> postList = userPostQueueQueryRepository.getPostList(userPrincipal.userId(), lastContentId);
        return Response.ok(postList);
    }
}
