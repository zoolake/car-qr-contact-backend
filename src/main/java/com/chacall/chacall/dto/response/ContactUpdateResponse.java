package com.chacall.chacall.dto.response;

import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.domain.ContactStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactUpdateResponse {
    private final String phoneNumber;
    private final String name;
    private final ContactStatus status;

    public static ContactUpdateResponse from(Contact contact) {
        return new ContactUpdateResponse(contact.getPhoneNumber(), contact.getName(), contact.getStatus());
    }
}
