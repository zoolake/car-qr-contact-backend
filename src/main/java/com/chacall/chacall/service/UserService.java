package com.chacall.chacall.service;

import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* 회원가입 */
    public Long join(String phoneNumber, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("입력한 비밀번호가 서로 일치하지 않습니다.");
        }

        if (isDuplicatedPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("이미 가입된 전화번호 입니다.");
        }

        User user = new User(phoneNumber, passwordEncoder.encode(password));

        return userRepository.save(user).getId();
    }

    /* 로그인 */
    public User login(String phoneNumber, String password) {
        // phoneNumber로 먼저 사용자 조회
        User user = userRepository.findUserByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다."));

        // 입력받은 password 를 encode 했을 때, 조회한 사용자의 비밀번호와 동일한지 비교
        if (isPasswordWrong(password, user.getPassword())) {
            throw new IllegalArgumentException("존재하지 않는 사용자 입니다.");
        }

        return user;
    }

    /* 중복 회원 여부 (전화번호 기준) */
    private boolean isDuplicatedPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber).isPresent();
    }

    /* 패스워드 비교 메서드 */
    private boolean isPasswordWrong(String inputPassword, String storedPassword) {
        return !passwordEncoder.matches(inputPassword, storedPassword);
    }
}
