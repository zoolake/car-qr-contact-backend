package com.chacall.chacall.service;

import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.FakeUserRepository;
import com.chacall.chacall.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new FakeUserRepository();
        userService = new UserService(userRepository, new BCryptPasswordEncoder());
    }

    @Test
    @DisplayName("휴대폰번호와 비밀번호를 입력하여 회원가입을 한다.")
    public void signUp() {
        //given
        String phoneNumber = "010-1234-5678";
        String password = "pwd123";

        //when
        Long id = userService.join(phoneNumber, password);

        //then
        User user = userRepository.findUserByPhoneNumber(phoneNumber).get();
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
    }

}
