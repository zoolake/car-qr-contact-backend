package com.chacall.chacall.dto.response;

import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.domain.ContactStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactResponse {
    private final Long contactId;
    private final String name;
    private final String phoneNumber;
    private final ContactStatus status;

    public static ContactResponse from(Contact contact) {
        return new ContactResponse(contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getStatus());
    }
}
