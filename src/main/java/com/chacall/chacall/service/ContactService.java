package com.chacall.chacall.service;

import com.chacall.chacall.domain.*;
import com.chacall.chacall.repository.car.CarRepository;
import com.chacall.chacall.repository.contact.ContactRepository;
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
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 carId 입니다."));

        return contactRepository.findContactsByCarId(car.getId());
    }

    @Transactional(readOnly = true)
    public List<Contact> findContactsByUserOwnCar(Long userId, Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 carId 입니다."));

        if (!userId.equals(car.getUser().getId())) {
            throw new IllegalArgumentException("본인 소유의 차량이 아닙니다.");
        }

        return contactRepository.findContactsByCarId(carId);
    }

    @Transactional
    public Long registerMainContact(Long carId, String phoneNumber, String name) {
        return registerContact(carId, phoneNumber, name, ContactType.MAIN);
    }

    @Transactional
    public Long registerSubContact(Long carId, String phoneNumber, String name) {
        return registerContact(carId, phoneNumber, name, ContactType.SUB);
    }

    private Long registerContact(Long carId, String phoneNumber, String name, ContactType contactType) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 carId 입니다."));

        // TODO: 사용자의 차량이 맞는지 확인하는 부분 필요할 듯

        if (contactType == ContactType.MAIN) {
            List<Contact> contacts = contactRepository.findContactsByCarId(carId);

            boolean isMainContactExist = contacts.stream().anyMatch(Contact::isMain);
            if (isMainContactExist) {
                throw new IllegalStateException("메인 연락처가 이미 등록되어 있습니다.");
            }
        }

        if (contactRepository.findContactByCarIdAndPhoneNumber(carId, phoneNumber).isPresent()) {
            throw new IllegalArgumentException("차량에 이미 등록된 연락처 입니다.");
        }

        Contact saved = contactRepository.save(new Contact(car, name, phoneNumber, contactType));
        return saved.getId();
    }

    @Transactional
    public Contact updateContactInfo(Long contactId, String newPhoneNumber, String newName, ContactStatus newStatus) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new IllegalArgumentException("없는 연락처 입니다."));

        contact.changePhoneNumber(newPhoneNumber);
        contact.changeName(newName);
        contact.changeStatus(newStatus);

        return contact;
    }

    @Transactional
    public void deleteContact(Long userId, Long carId, Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new IllegalArgumentException("없는 연락처 입니다."));

        if (contact.isMain()) {
            throw new IllegalStateException("메인 연락처는 삭제가 불가합니다.");
        }

        Car car = contact.getCar();
        if (!car.getId().equals(carId)) {
            throw new IllegalArgumentException("차량에 등록된 연락처가 아닙니다.");
        }

        User user = car.getUser();
        if (!user.getId().equals(userId)) {
            throw new IllegalArgumentException("사용자가 등록한 연락처가 아닙니다.");
        }

        contactRepository.delete(contact);
    }
}
