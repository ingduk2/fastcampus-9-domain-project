package org.fastcampus.acceptance.utils;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.fastcampus.auth.application.dto.CreateUserAuthRequestDto;
import org.fastcampus.auth.application.dto.SendEmailRequestDto;
import org.springframework.stereotype.Component;

import static org.fastcampus.acceptance.steps.SignUpAcceptanceSteps.*;

@Component
public class AcceptanceDataLoader {

    @PersistenceContext
    private EntityManager entityManager;

    public String getEmailToken(String email) {
        return entityManager.createNativeQuery(
                "SELECT token FROM community_email_verification WHERE email = ?", String.class)
                .setParameter(1, email)
                .getSingleResult()
                .toString();
    }

    public boolean isEmailVerified(String email) {
        return entityManager.createQuery(
                "SELECT isVerified FROM EmailVerificationEntity WHERE email = :email", Boolean.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Long getUserId(String email) {
        return entityManager.createQuery(
                "SELECT userId FROM UserAuthEntity WHERE email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Long createUser(String email) {
        requestSendEmail(new SendEmailRequestDto(email));
        String token = getEmailToken(email);
        requestVerifyEmail(email, token);
        ExtractableResponse<Response> response = requestRegisterUser(new CreateUserAuthRequestDto(email, "password", "USER", "name", "profileImageUrl"));
        return AcceptanceResponse.getId(response);
    }
}
