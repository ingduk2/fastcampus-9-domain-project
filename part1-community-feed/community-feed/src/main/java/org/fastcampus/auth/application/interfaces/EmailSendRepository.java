package org.fastcampus.auth.application.interfaces;

import org.fastcampus.auth.domain.Email;

public interface EmailSendRepository {
    void sendEmail(Email email1, String email);
}
