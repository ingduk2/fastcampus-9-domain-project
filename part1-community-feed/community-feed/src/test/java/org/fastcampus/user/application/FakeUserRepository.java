package org.fastcampus.user.application;

import org.fastcampus.user.application.interfaces.UserRepository;
import org.fastcampus.user.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {

    private final Map<Long, User> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public User save(User user) {
        if (user.getId() != null) {
            store.put(user.getId(), user);
            return user;
        }

        long id = idGenerator.incrementAndGet();
        User newUser = new User(id, user.getUserInfo());
        store.put(id, newUser);
        return newUser;
    }

    @Override
    public User findById(Long id) {
        return store.get(id);
    }
}