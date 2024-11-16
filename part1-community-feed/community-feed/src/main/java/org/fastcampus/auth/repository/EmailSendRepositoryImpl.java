package org.fastcampus.auth.repository;

import org.fastcampus.auth.application.interfaces.EmailSendRepository;
import org.fastcampus.auth.domain.Email;
import org.springframework.stereotype.Repository;

@Repository
public class EmailSendRepositoryImpl implements EmailSendRepository {
    @Override
    public void sendEmail(Email email1, String email) {
        // TODO
    }
}
