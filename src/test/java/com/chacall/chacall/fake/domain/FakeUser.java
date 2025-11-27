package com.chacall.chacall.fake.domain;

import com.chacall.chacall.domain.User;

/**
 * [FakeUser 클래스 만든 이유]
 * 테스트를 위해서 User 객체에 ID 값을 직접 set 하는 경우가 필요함.
 * User 에서 ID 를 받는 생성자를 public 보다는 protected 로 하여 무분별한 생성을 최대한 막고자 함.
 * User 를 상속받는 FakeUser 클래스를 만들어서 ID 를 받는 protected 생성자를 간접적으로 호출하게끔 함.
 */
public class FakeUser extends User {
    public FakeUser(Long id, User user) {
        super(id, user.getPhoneNumber(), user.getPassword());
    }
}
