package com.chacall.chacall.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String phoneNumber;
    private String password;

    protected User() {
    }

    /* 단위테스트를 위한 생성자 */
    protected User(Long id, String phoneNumber, String password) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String phoneNumber, String password) {
        validatePhoneNumber(phoneNumber);
        validatePassword(password);
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    private void validatePhoneNumber(String phoneNumber) {
        String phoneNumberRegex = "^01[016789]\\d{7,8}$";
        if (!phoneNumber.matches(phoneNumberRegex)) {
            throw new IllegalArgumentException("유효하지 않은 연락처 형식입니다.");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < 8 || password.length() > 16) {
            System.out.println("password = " + password);
            throw new IllegalArgumentException("비밀번호는 8~16자 입니다.");
        }

        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).+$";
        if (!password.matches(passwordRegex)) {
            throw new IllegalArgumentException("비밀번호는 문자, 숫자, 특수문자로 구성되어 있습니다.");
        }
    }


}
