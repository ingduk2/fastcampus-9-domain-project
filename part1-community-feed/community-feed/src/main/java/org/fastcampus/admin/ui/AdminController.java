package org.fastcampus.admin.ui;

import lombok.RequiredArgsConstructor;
import org.fastcampus.admin.ui.dto.post.GetPostTableRequestDto;
import org.fastcampus.admin.ui.dto.post.GetPostTableResponseDto;
import org.fastcampus.admin.ui.query.AdminTableQueryRepository;
import org.fastcampus.admin.ui.query.UserStatsQueryRepository;
import org.fastcampus.admin.ui.dto.users.GetDailyRegisterUserResponseDto;
import org.fastcampus.admin.ui.dto.GetTableListResponse;
import org.fastcampus.admin.ui.dto.users.GetUserTableRequestDto;
import org.fastcampus.admin.ui.dto.users.GetUserTableResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserStatsQueryRepository userStatsQueryRepository;
    private final AdminTableQueryRepository adminTableQueryRepository;

    @GetMapping("/index")
    public ModelAndView index() {
        List<GetDailyRegisterUserResponseDto> dailyRegisterUserStats = userStatsQueryRepository.getDailyRegisterUserStats(7);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        modelAndView.addObject("result", dailyRegisterUserStats);
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView users(GetUserTableRequestDto dto) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("users");

        GetTableListResponse<List<GetUserTableResponseDto>> result = adminTableQueryRepository.getUserTableData(dto);
        modelAndView.addObject("requestDto", dto);
        modelAndView.addObject("userList", result.getTableData());
        modelAndView.addObject("totalCount", result.getTotalCount());
        return modelAndView;
    }

    @GetMapping("/posts")
    public ModelAndView posts(GetPostTableRequestDto dto) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("posts");

        GetTableListResponse<List<GetPostTableResponseDto>> result = adminTableQueryRepository.getPostTableData(dto);
        modelAndView.addObject("requestDto", dto);
        modelAndView.addObject("postList", result.getTableData());
        modelAndView.addObject("totalCount", result.getTotalCount());
        return modelAndView;
    }
}
