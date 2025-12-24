package com.chacall.chacall.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String nickname;
    private String message;

    @OneToOne(mappedBy = "car")
    private QR qr;

    /* 단위테스트를 위한 생성자 */
    protected Car(Long carId, User user, String nickname, String message) {
        this.id = carId;
        this.user = user;
        this.nickname = nickname;
        this.message = message;
    }

    public Car(User user, String nickname, String message) {
        validateNickname(nickname);
        validateMessage(message);
        this.user = user;
        this.nickname = nickname;
        this.message = message;
    }

    public void changeNickname(String newNickname) {
        validateNickname(newNickname);
        nickname = newNickname;
    }

    public void changeMessage(String newMessage) {
        validateMessage(newMessage);
        message = newMessage;
    }

    private void validateNickname(String nickname) {
        if (nickname.length() < 3 || nickname.length() > 12) {
            throw new IllegalArgumentException("닉네임의 길이는 3~12 자 입니다.");
        }

        String nameRegex = "^[A-Za-z0-9가-힣]+$";
        if (!nickname.matches(nameRegex)) {
            throw new IllegalArgumentException("닉네임에 특수문자 사용은 불가합니다.");
        }
    }

    private void validateMessage(String message) {
        if (message.length() > 30) {
            throw new IllegalArgumentException("메세지는 최대 30자 입니다.");
        }
    }

    public void setQR(QR qr) {
        this.qr = qr;
    }

}
