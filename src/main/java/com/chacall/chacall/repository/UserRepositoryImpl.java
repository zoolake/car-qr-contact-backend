package com.chacall.chacall.repository;

import com.chacall.chacall.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return repository.findUserByPhoneNumber(phoneNumber);
    }
}
