package com.chacall.chacall.service;

import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void 회원가입() {
        User user = new User("010-1234-5678", "pwd123");
        User saved = userRepository.save(user);

        User found = userRepository.findById(saved.getId()).get();

        assertThat(saved.getPhoneNumber()).isEqualTo(found.getPhoneNumber());
    }

}
