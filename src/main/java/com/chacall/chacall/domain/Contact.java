package com.chacall.chacall.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    private String name;
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private ContactStatus status;

    protected Contact() {
    }

    /* 단위테스트를 위한 생성자 */
    protected Contact(Long contactId, Car car, String name, String phoneNumber) {
        this(car, name, phoneNumber);
        this.id = contactId;
    }

    public Contact(Car car, String name, String phoneNumber) {
        validateName(name);
        validatePhoneNumber(phoneNumber);
        this.car = car;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = ContactStatus.AVAILABLE;
    }

    private void validateName(String name) {
        if (name.length() < 3 || name.length() > 12) {
            throw new IllegalArgumentException("닉네임의 길이는 3~12 자 입니다.");
        }

        String nameRegex = "^[A-Za-z0-9가-힣]+$";
        if (!name.matches(nameRegex)) {
            throw new IllegalArgumentException("닉네임에 특수문자 사용은 불가합니다.");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        String phoneNumberRegex = "^01[016789]\\d{7,8}$";
        if (!phoneNumber.matches(phoneNumberRegex)) {
            throw new IllegalArgumentException("유효하지 않은 연락처 형식입니다.");
        }
    }

    public void changePhoneNumber(String newPhoneNumber) {
        if (!phoneNumber.equals(newPhoneNumber)) {
            validatePhoneNumber(newPhoneNumber);
            this.phoneNumber = newPhoneNumber;
        }
    }

    public void changeName(String newName) {
        if (!name.equals(newName)) {
            validateName(newName);
            this.name = newName;
        }
    }

    public void changeStatus(ContactStatus newStatus) {
        if (this.status != newStatus) {
            this.status = newStatus;
        }
    }


}
