package org.fastcampus.admin.ui.dto;

import java.time.LocalDate;

public record GetDailyRegisterUserResponseDto(
        Long count,
        LocalDate date
) {
}
