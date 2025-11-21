package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.domain.ContactStatus;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ContactServiceTest {

    private final FakeContactRepository contactRepository = new FakeContactRepository();
    private final FakeCarRepository carRepository = new FakeCarRepository();
    private final ContactService contactService = new ContactService(contactRepository, carRepository);

    @Test
    @DisplayName("차량에 연락처를 등록한다.")
    void registerContact() {
        User user = createTestUser();
        Car car = createTestCar(user);

        String phoneNumber = "010-3456-1234";
        String name = "홍길동";
        Long contactId = contactService.registerContact(car.getId(), phoneNumber, name);

        Contact contact = contactRepository.findById(contactId).get();
        assertThat(contact).isNotNull();
        assertThat(contact.getId()).isEqualTo(contactId);
        assertThat(contact.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(contact.getName()).isEqualTo(name);
        assertThat(contact.getStatus()).isEqualTo(ContactStatus.AVAILABLE);
    }

    @Test
    @DisplayName("등록되지 않은 차량에 연락처 등록 시 예외가 발생한다.")
    void failToRegisterContactWhenCarDoesNotExist() {
        Long invalidCarId = 13L;
        String phoneNumber = "010-3456-1234";
        String name = "홍길동";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.registerContact(invalidCarId, phoneNumber, name));
    }

    @Test
    @DisplayName("연락처 등록 시, 대상 차량에 이미 동일한 연락처가 등록되어 있다면 예외가 발생한다.")
    void failToRegisterContactWhenContactIsDuplicated() {
        User user = createTestUser();
        Car car = createTestCar(user);

        String phoneNumber = "010-3456-1234";
        String name = "홍길동";
        contactService.registerContact(car.getId(), phoneNumber, name);

        String duplicatedPhoneNumber = phoneNumber;
        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.registerContact(car.getId(), duplicatedPhoneNumber, name));
    }

    @Test
    @DisplayName("등록되지 않은 연락처를 수정 시 예외가 발생한다.")
    void failToUpdateContactWhenContactDoesNotExist() {
        User user = createTestUser();
        Car car = createTestCar(user);

        String phoneNumber = "010-3456-1234";
        String name = "홍길동";
        contactService.registerContact(car.getId(), phoneNumber, name);

        Long invalidContactId = 13L;
        String newPhoneNumber = "010-1234-5678";
        String newName = "임꺽정";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.updateContactInfo(invalidContactId, newPhoneNumber, newName, ContactStatus.AVAILABLE));
    }

    @Test
    @DisplayName("연락처 정보를 수정한다.")
    void updateContact() {
        User user = createTestUser();
        Car car = createTestCar(user);

        String phoneNumber = "010-3456-1234";
        String name = "홍길동";
        Long contactId = contactService.registerContact(car.getId(), phoneNumber, name);

        String newPhoneNumber = "010-1234-5678";
        String newName = "임꺽정";
        ContactStatus newStatus = ContactStatus.AVAILABLE;
        Contact updatedContact = contactService.updateContactInfo(contactId, newPhoneNumber, newName, newStatus);

        assertThat(updatedContact.getId()).isEqualTo(contactId);
        assertThat(updatedContact.getPhoneNumber()).isEqualTo(newPhoneNumber);
        assertThat(updatedContact.getName()).isEqualTo(newName);
        assertThat(updatedContact.getStatus()).isEqualTo(newStatus);
    }

    private User createTestUser() {
        User user = new User("01012123434", "test1");
        return new FakeUserRepository().save(user);
    }

    private Car createTestCar(User user) {
        Car car = new Car(user, "carNickName", "carMessage");
        return carRepository.save(car);
    }

}
