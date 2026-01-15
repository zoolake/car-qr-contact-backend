package com.chacall.chacall.dto.response;

import com.chacall.chacall.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLoginResponse {
    private final Long userId;
    private final String phoneNumber;

    public static UserLoginResponse from(User user) {
        return new UserLoginResponse(user.getId(), user.getPhoneNumber());
    }
}
