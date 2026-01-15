package com.chacall.chacall.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginRequest {
    @NotBlank(message = "연락처는 공백일 수 없습니다.")
    private final String phoneNumber;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private final String password;
}
