package com.chacall.chacall.repository;

import com.chacall.chacall.domain.FakeUser;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.user.UserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {
    private final AtomicLong idGenerator;
    private final Map<Long, User> database;

    public FakeUserRepository() {
        this.idGenerator = new AtomicLong(0);
        this.database = new ConcurrentHashMap<>();
    }

    public void clear() {
        database.clear();
    }

    @Override
    public User save(User user) {
        if (user.getId() != null) {
            return user;
        }

        long id = idGenerator.getAndIncrement();
        database.put(id, new FakeUser(id, user));

        return database.get(id);
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return database.values().stream()
                .filter(user -> user.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(database.get(userId));
    }
}
