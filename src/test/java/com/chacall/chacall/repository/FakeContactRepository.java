package com.chacall.chacall.repository;

import com.chacall.chacall.domain.Contact;

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
        database.put(id, contact);

        return database.get(id);
    }

    @Override
    public Optional<Contact> findById(Long contactId) {
        return Optional.empty();
    }

    @Override
    public List<Contact> findContactsByCarId(Long carId) {
        return null;
    }
}
