package com.chacall.chacall.repository;

import com.chacall.chacall.domain.User;

public class FakeUser extends User {
    public FakeUser(Long id, User user) {
        super(id, user.getPhoneNumber(), user.getPassword());
    }
}
