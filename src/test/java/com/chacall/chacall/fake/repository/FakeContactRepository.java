package com.chacall.chacall.fake.repository;

import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.fake.domain.FakeContact;
import com.chacall.chacall.repository.contact.ContactRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeContactRepository implements ContactRepository {

    private final AtomicLong idGenerator;
    private final Map<Long, Contact> database;

    public FakeContactRepository() {
        this.idGenerator = new AtomicLong(0L);
        this.database = new ConcurrentHashMap<>();
    }

    @Override
    public Contact save(Contact contact) {
        if (contact.getId() != null) {
            return contact;
        }

        long id = idGenerator.getAndIncrement();
        database.put(id, new FakeContact(id, contact));

        return database.get(id);
    }

    @Override
    public Optional<Contact> findById(Long contactId) {
        return Optional.ofNullable(database.get(contactId));
    }

    @Override
    public List<Contact> findContactsByCarId(Long carId) {
        return database.values().stream()
                .filter(contact -> contact.getCar().getId().equals(carId))
                .toList();
    }

    @Override
    public Optional<Contact> findContactByCarIdAndPhoneNumber(Long carId, String phoneNumber) {
        return database.values().stream()
                .filter(contact ->
                        contact.getCar().getId().equals(carId) && contact.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }
}
