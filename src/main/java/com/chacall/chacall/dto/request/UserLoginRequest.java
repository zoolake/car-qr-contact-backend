package com.chacall.chacall.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginRequest {
    @NotBlank
    @Pattern(regexp = "^01[016789]\\d{7,8}$")
    private final String phoneNumber;

    @NotBlank
    @Size(min = 8, max = 16)
    private final String password;
}
