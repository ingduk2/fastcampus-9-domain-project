package org.fastcampus.user.domain;

import lombok.Getter;

@Getter
public class UserInfo {

    @Getter
    private final String name;
    private final String profileImageUrl;

    public UserInfo(String name, String profileImageUrl) {
        if (name == null || name.isEmpty() || name.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
}
