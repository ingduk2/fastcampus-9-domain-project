package org.fastcampus.admin.ui;

import lombok.RequiredArgsConstructor;
import org.fastcampus.admin.repository.UserStatsQueryRepository;
import org.fastcampus.admin.ui.dto.GetDailyRegisterUserResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserStatsQueryRepository userStatsQueryRepository;

    @GetMapping("/index")
    public ModelAndView index() {
        List<GetDailyRegisterUserResponseDto> dailyRegisterUserStats = userStatsQueryRepository.getDailyRegisterUserStats(7);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        modelAndView.addObject("result", dailyRegisterUserStats);
        return modelAndView;
    }
}
