package com.chacall.chacall.fake.domain;

import com.chacall.chacall.domain.Contact;

public class FakeContact extends Contact {
    public FakeContact(Long contactId, Contact contact) {
        super(contactId, contact.getCar(), contact.getName(), contact.getPhoneNumber());
    }
}
