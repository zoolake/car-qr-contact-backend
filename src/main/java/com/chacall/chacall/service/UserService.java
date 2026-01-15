package com.chacall.chacall.service;

import com.chacall.chacall.domain.Password;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* 회원가입 */
    public Long join(String phoneNumber, String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("입력한 비밀번호가 서로 일치하지 않습니다.");
        }

        if (isDuplicatedPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("이미 가입된 전화번호 입니다.");
        }

        User user = new User(phoneNumber, Password.fromRaw(password, passwordEncoder));

        return userRepository.save(user).getId();
    }

    /* 중복 회원 여부 (전화번호 기준) */
    private boolean isDuplicatedPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber).isPresent();
    }

}
