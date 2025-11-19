package com.chacall.chacall.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String phoneNumber;
    private String password;

    protected User() {
    }

    /* 단위테스트를 위한 생성자 */
    protected User(Long id, String phoneNumber, String password) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}
