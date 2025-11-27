package com.chacall.chacall.service;

import com.chacall.chacall.domain.User;
import com.chacall.chacall.fake.repository.FakeUserRepository;
import com.chacall.chacall.fake.service.FakePasswordEncoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class UserServiceTest {
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakePasswordEncoder passwordEncoder = new FakePasswordEncoder();

    private final UserService userService = new UserService(userRepository, passwordEncoder);

    @AfterEach
    public void setUp() {
        userRepository.clear();
    }

    @Test
    @DisplayName("회원가입을 한다.")
    void signUp() {
        String phoneNumber = "01012345678";
        String password = "pwd1234!";
        String confirmPassword = password;

        Long id = userService.join(phoneNumber, password, confirmPassword);

        User user = userRepository.findUserByPhoneNumber(phoneNumber).get();
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    @DisplayName("이미 가입된 휴대폰 번호인 경우 회원가입 시 예외가 발생한다.")
    void failWhenEnteredPhoneNumberIsDuplicated() {
        String phoneNumber = "01012345678";
        String password = "pwd1234!";
        userRepository.save(new User(phoneNumber, password));

        String newPassword = "newPwd123!";
        String newConfirmPassword = newPassword;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> userService.join(phoneNumber, newPassword, newConfirmPassword));
    }

    @Test
    @DisplayName("회원가입 시, 입력한 비밀번호와 확인용 비밀번호가 일치하지 않으면 예외가 발생한다.")
    void failWhenPasswordsDoNotMatch() {
        String phoneNumber = "01012345678";
        String password = "pwd1234!";
        String confirmPassword = "qwer1234!";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> userService.join(phoneNumber, password, confirmPassword));
    }

}
