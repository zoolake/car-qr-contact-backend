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

    protected Contact(Long contactId, Car car, String name, String phoneNumber) {
        this(car, name, phoneNumber);
        this.id = contactId;
    }

    public Contact(Car car, String name, String phoneNumber) {
        this.car = car;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = ContactStatus.AVAILABLE;
    }

    public void changePhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void changeStatus(ContactStatus newStatus) {
        if (this.status != newStatus) {
            this.status = newStatus;
        }
    }
}
