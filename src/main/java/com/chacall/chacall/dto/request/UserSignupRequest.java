package com.chacall.chacall.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignupRequest {
    private final String phoneNumber;
    private final String password;
    private final String passwordConfirm;
}
