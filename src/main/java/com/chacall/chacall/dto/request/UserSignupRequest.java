package com.chacall.chacall.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignupRequest {
    @NotBlank
    @Pattern(regexp = "^01[016789]\\d{7,8}$")
    private final String phoneNumber;

    @NotBlank
    @Size(min = 8, max = 16)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).+$")
    private final String password;

    @NotBlank
    private final String passwordConfirm;
}
