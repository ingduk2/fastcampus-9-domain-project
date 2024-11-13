package org.fastcampus.post.application;

import org.fastcampus.fake.FakeObjectFactory;
import org.fastcampus.user.application.CreateUserRequestDto;
import org.fastcampus.user.application.UserService;
import org.fastcampus.user.domain.User;

public class PostApplicationTestTemplate {
     final PostService postService = FakeObjectFactory.getPostService();
     final UserService userService = FakeObjectFactory.getUserService();
     final CommentService commentService = FakeObjectFactory.getCommentService();

     final User user = userService.createUser(new CreateUserRequestDto("name1", "url1"));
     final User anotherUser = userService.createUser(new CreateUserRequestDto("name2", "url2"));
}
