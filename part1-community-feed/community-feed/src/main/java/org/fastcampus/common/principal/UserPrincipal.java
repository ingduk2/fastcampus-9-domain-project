package org.fastcampus.common.principal;

import org.fastcampus.auth.domain.UserRole;

public record UserPrincipal(
        Long userId,
        UserRole userRole
) {
    public UserPrincipal(Long userId, String role) {
        this(userId, UserRole.valueOf(role));
    }
}
