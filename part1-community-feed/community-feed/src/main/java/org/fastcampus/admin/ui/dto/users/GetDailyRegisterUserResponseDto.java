package org.fastcampus.admin.ui.dto.users;

import java.time.LocalDate;

public record GetDailyRegisterUserResponseDto(
        Long count,
        LocalDate date
) {
}
