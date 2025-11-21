package com.chacall.chacall.repository.contact;

import com.chacall.chacall.domain.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {
    Contact save(Contact contact);

    Optional<Contact> findById(Long contactId);

    List<Contact> findContactsByCarId(Long carId);

    Optional<Contact> findContactByPhoneNumber(String phoneNumber);
}
