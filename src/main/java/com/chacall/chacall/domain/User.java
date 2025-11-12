package com.chacall.chacall.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String phoneNumber;
    private String password;

    protected User() {}

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
