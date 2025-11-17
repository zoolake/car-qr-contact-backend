package com.chacall.chacall.service;

import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    public Long join(String phoneNumber, String password) {
        User saved = userRepository.save(new User(phoneNumber, password));
        return saved.getId();
    }

    /* 로그인 */
    public User login(String phoneNumber, String password) {
        return userRepository.findUserByPhoneNumberAndPassword(phoneNumber, password);
    }
}
