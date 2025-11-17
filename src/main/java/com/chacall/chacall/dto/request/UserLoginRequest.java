package com.chacall.chacall.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginRequest {
    private final String phoneNumber;
    private final String password;
}
