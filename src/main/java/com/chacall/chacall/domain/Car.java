package com.chacall.chacall.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String nickname;
    private String message;

    @OneToOne(mappedBy = "car")
    private QR qr;

    protected Car() {
    }

    public Car(User user, String nickname, String message) {
        this.user = user;
        this.nickname = nickname;
        this.message = message;
    }

    public void changeNickname(String newNickname) {
//        validateNickname(newNickname);
        nickname = newNickname;
    }

    public void changeMessage(String newMessage) {
//        validateMessage(newMessage);
        message = newMessage;
    }

}
