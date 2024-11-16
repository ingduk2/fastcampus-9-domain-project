package org.fastcampus.auth.domain;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Email {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private final String emailText;

    private Email(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is not valid");
        }

        if (isNotValidEmailString(email)) {
            throw new IllegalArgumentException("email is not valid");
        }

        this.emailText = email;
    }

    public static Email createEmail(String email) {
        return new Email(email);
    }

    private boolean isNotValidEmailString(String email) {
        return !pattern.matcher(email).matches();
    }
}
