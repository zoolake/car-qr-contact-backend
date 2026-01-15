package com.chacall.chacall.dto.response;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.Contact;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class QRContactsResponse {
    private final String carNickname;
    private final String carMessage;
    private final List<ContactResponse> contacts;

    public static QRContactsResponse from(Car car, List<Contact> contacts) {
        List<ContactResponse> contactsResponse = contacts.stream()
                .map(ContactResponse::from)
                .toList();

        return new QRContactsResponse(car.getNickname(), car.getMessage(), contactsResponse);
    }
}
