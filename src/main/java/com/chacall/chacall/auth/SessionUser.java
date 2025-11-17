package com.chacall.chacall.auth;

import com.chacall.chacall.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@ToString
public class SessionUser implements Serializable {
    private final Long userId;
    private final String phoneNumber;

    public static Object from(User user) {
        return new SessionUser(user.getId(), user.getPhoneNumber());
    }
}
