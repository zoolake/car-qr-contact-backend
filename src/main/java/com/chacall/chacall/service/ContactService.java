package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.domain.ContactStatus;
import com.chacall.chacall.repository.CarRepository;
import com.chacall.chacall.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final CarRepository carRepository;

    @Transactional(readOnly = true)
    public List<Contact> findContactsByCarId(Long carId) {
        return contactRepository.findContactsByCarId(carId);
    }

    @Transactional
    public Long registerContact(Long carId, String phoneNumber, String name) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 carId 입니다."));

        Contact saved = contactRepository.save(new Contact(car, name, phoneNumber));
        return saved.getId();
    }

    @Transactional
    public Contact updateContactInfo(Long contactId, String newPhoneNumber, String newName, ContactStatus newStatus) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new IllegalArgumentException("없는 연락처 입니다."));

        if (newPhoneNumber != null) {
            contact.changePhoneNumber(newPhoneNumber);
        }

        if (newName != null) {
            contact.changeName(newName);
        }

        if(newStatus != null) {
            contact.changeStatus(newStatus);
        }

        return contact;
    }

}
