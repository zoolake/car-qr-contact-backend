package com.chacall.chacall.service;

import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.FakeUser;
import com.chacall.chacall.repository.FakeUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class UserServiceTest {
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserService userService = new UserService(userRepository, passwordEncoder);

    @AfterEach
    public void setUp() {
        userRepository.clear();
    }

    @Test
    @DisplayName("휴대폰번호, 비밀번호, 확인용 비밀번호를 입력하여 회원가입을 한다.")
    public void signUpWithPhoneNumberAndPassword() {
        String phoneNumber = "010-1234-5678";
        String password = "pwd123";
        String confirmPassword = password;

        Long id = userService.join(phoneNumber, password, confirmPassword);

        User user = userRepository.findUserByPhoneNumber(phoneNumber).get();
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    @DisplayName("휴대폰번호, 비밀번호를 입력하여 로그인을 한다.")
    public void loginWithPhoneNumberAndPassword() {
        String phoneNumber = "010-1234-5678";
        String password = "pwd123";
        userService.join(phoneNumber, password, password);

        User loginUser = userService.login(phoneNumber, password);

        assertThat(loginUser).isNotNull();
        assertThat(loginUser.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    @DisplayName("이미 가입된 휴대폰 번호인 경우 회원가입 시 예외가 발생한다.")
    public void failWhenEnteredPhoneNumberIsDuplicated() {
        String phoneNumber = "010-1234-5678";
        String password = "pwd123";
        userRepository.save(new User(phoneNumber, password));

        String newPassword = "newPwd123";
        String newConfirmPassword = newPassword;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> userService.join(phoneNumber, newPassword, newConfirmPassword));
    }

    @Test
    @DisplayName("회원가입 시, 입력한 비밀번호와 확인용 비밀번호가 일치하지 않으면 예외가 발생한다.")
    public void failWhenPasswordsDoNotMatch() {
        String phoneNumber = "010-1234-5678";
        String password = "pwd123";
        String confirmPassword = "qwer1234";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> userService.join(phoneNumber, password, confirmPassword));
    }

}
