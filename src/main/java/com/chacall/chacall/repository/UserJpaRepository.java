package com.chacall.chacall.repository;

import com.chacall.chacall.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByPhoneNumber(String phoneNumber);
}
