package com.chacall.chacall.domain;

public class FakeContact extends Contact {
    public FakeContact(Long contactId, Contact contact) {
        super(contactId, contact.getCar(), contact.getName(), contact.getPhoneNumber());
    }
}
