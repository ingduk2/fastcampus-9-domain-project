package org.fastcampus.auth.repository;

import lombok.RequiredArgsConstructor;
import org.fastcampus.auth.application.interfaces.UserAuthRepository;
import org.fastcampus.auth.domain.UserAuth;
import org.fastcampus.auth.repository.entity.UserAuthEntity;
import org.fastcampus.auth.repository.jpa.JpaUserAuthRepository;
import org.fastcampus.user.application.interfaces.UserRepository;
import org.fastcampus.user.domain.User;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepository {

    private final JpaUserAuthRepository jpaUserAuthRepository;
    private final UserRepository userRepository;

    @Override
    public UserAuth registerUser(UserAuth userAuth, User user) {
        User savedUser = userRepository.save(user);
        UserAuthEntity userAuthEntity = new UserAuthEntity(userAuth, savedUser.getId());
        userAuthEntity = jpaUserAuthRepository.save(userAuthEntity);
        return userAuthEntity.toUserAuth();
    }

    @Override
    public UserAuth loginUser(String email, String password) {
        UserAuthEntity userAuthEntity = jpaUserAuthRepository.findById(email).orElseThrow();
        UserAuth userAuth = userAuthEntity.toUserAuth();

        if (userAuth.notMatchPassword(password)) {
            throw new IllegalArgumentException("비밀번호가 다릅니다");
        }

        return userAuth;
    }
}
