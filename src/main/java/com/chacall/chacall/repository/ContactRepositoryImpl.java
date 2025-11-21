package com.chacall.chacall.repository;

import com.chacall.chacall.domain.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContactRepositoryImpl implements ContactRepository {

    private final ContactJpaRepository contactJpaRepository;

    @Override
    public Contact save(Contact contact) {
        return contactJpaRepository.save(contact);
    }

    @Override
    public Optional<Contact> findById(Long contactId) {
        return contactJpaRepository.findById(contactId);
    }

    @Override
    public List<Contact> findContactsByCarId(Long carId) {
        return contactJpaRepository.findContactsByCarId(carId);
    }
}
