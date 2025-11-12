package com.chacall.chacall.repository;

import com.chacall.chacall.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
