package com.chacall.chacall.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 16;
    private static final String PASSWORD_FORMAT_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).+$";
    @Column(name = "password", nullable = false)
    private String value;

    private Password(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isBlank()) {
            throw new IllegalArgumentException("암호화된 비밀번호에 빈 값을 넣을 수 없습니다.");
        }

        value = encodedPassword;
    }

    public static Password fromRaw(String rawPassword, PasswordEncoder encoder) {
        return new Password(encoder.encode(rawPassword));
    }

    private void validateRawPassword(String rawPassword) {
        if (rawPassword.length() < PASSWORD_MIN_LENGTH || rawPassword.length() > PASSWORD_MAX_LENGTH) {
            throw new IllegalArgumentException("비밀번호는 8~16자 입니다.");
        }

        if (!rawPassword.matches(PASSWORD_FORMAT_REGEX)) {
            throw new IllegalArgumentException("비밀번호는 문자, 숫자, 특수문자로 구성되어 있습니다.");
        }
    }

    public String value() {
        return value;
    }
}
