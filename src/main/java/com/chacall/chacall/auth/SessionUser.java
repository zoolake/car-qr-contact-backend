package com.chacall.chacall.auth;

import com.chacall.chacall.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
@ToString
public class SessionUser implements UserDetails {
    private final Long userId;
    private final String phoneNumber;
    private final String password;

    public static SessionUser from(User user) {
        return new SessionUser(user.getId(), user.getPhoneNumber(), user.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }
}
