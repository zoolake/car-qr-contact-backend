package com.chacall.chacall.repository;

import com.chacall.chacall.domain.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    Optional<User> findById(Long userId);
}
