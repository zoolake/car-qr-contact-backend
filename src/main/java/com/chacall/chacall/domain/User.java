package com.chacall.chacall.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String phoneNumber;
    @Embedded
    private Password password;

    /* 단위테스트를 위한 생성자 */
    protected User(Long id, String phoneNumber, Password password) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String phoneNumber, Password password) {
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    private void validatePhoneNumber(String phoneNumber) {
        String phoneNumberRegex = "^01[016789]\\d{7,8}$";
        if (!phoneNumber.matches(phoneNumberRegex)) {
            throw new IllegalArgumentException("유효하지 않은 연락처 형식입니다.");
        }
    }

}
