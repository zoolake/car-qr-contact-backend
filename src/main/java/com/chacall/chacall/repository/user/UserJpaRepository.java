package com.chacall.chacall.repository.user;

import com.chacall.chacall.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByPhoneNumber(String phoneNumber);
}
