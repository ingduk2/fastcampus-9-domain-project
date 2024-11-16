package org.fastcampus.auth.repository;

import lombok.RequiredArgsConstructor;
import org.fastcampus.auth.application.interfaces.EmailVerificationRepository;
import org.fastcampus.auth.domain.Email;
import org.fastcampus.auth.repository.entity.EmailVerificationEntity;
import org.fastcampus.auth.repository.jpa.JpaEmailVerificationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {

    private final JpaEmailVerificationRepository jpaEmailVerificationRepository;

    @Transactional
    @Override
    public void createEmailVerification(Email email, String token) {
        String emailAddress = email.getEmailText();
        Optional<EmailVerificationEntity> entity = jpaEmailVerificationRepository.findByEmail(emailAddress);

        if (entity.isPresent()) {
            EmailVerificationEntity emailVerificationEntity = entity.get();

            if (emailVerificationEntity.isVerified()) {
                throw new IllegalArgumentException("이미 인증된 이메일 입니다");
            }

            emailVerificationEntity.updateToken(token);
        }

        EmailVerificationEntity emailVerificationEntity = new EmailVerificationEntity(emailAddress, token);
        jpaEmailVerificationRepository.save(emailVerificationEntity);
    }

    @Transactional
    @Override
    public void verifyEmail(Email email, String token) {
        String emailAddress = email.getEmailText();

        EmailVerificationEntity entity = jpaEmailVerificationRepository.findByEmail(emailAddress)
                .orElseThrow(() -> new IllegalArgumentException("인증 요청하지 않은 이메일 입니다"));

        if (entity.isVerified()) {
            throw new IllegalArgumentException("이미 인증된 이메일 입니다");
        }

        if (!entity.hasSameToken(token)) {
            throw new IllegalArgumentException("토큰 값이 유효하지 않습니다");
        }

        entity.verify();
    }

    @Override
    public boolean isEmailVerified(Email email) {
        EmailVerificationEntity entity = jpaEmailVerificationRepository.findByEmail(email.getEmailText())
                .orElseThrow(() -> new IllegalArgumentException("인증 요청하지 않은 이메일 입니다"));

        return entity.isVerified();
    }
}
