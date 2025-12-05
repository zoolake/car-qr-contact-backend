package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.domain.ContactStatus;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ContactServiceTest {

    private final FakeContactRepository contactRepository = new FakeContactRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeCarRepository carRepository = new FakeCarRepository();
    private final ContactService contactService = new ContactService(contactRepository, carRepository);

    @Test
    @DisplayName("본인 소유의 차량 ID 를 통해 해당 차량에 등록된 연락처를 조회한다.")
    void getContactsByCarId() {
        User user = createTestUser("01011112222", "pwd1234");
        Car car1 = createTestCar(user, "car1", "car1 message");
        contactService.registerContact(car1.getId(), "01011112222", "첫번째연락처");
        contactService.registerContact(car1.getId(), "01022223333", "두번째연락처");

        List<Contact> car1Contacts = contactService.findContactsByUserOwnCar(user.getId(), car1.getId());

        assertThat(car1Contacts.size()).isEqualTo(2);
        car1Contacts.forEach(contact -> assertThat(contact.getCar().getId()).isEqualTo(car1.getId()));
    }

    @Test
    @DisplayName("사용자가 보유하지 않은 차량의 연락처 목록을 조회하려는 경우 예외가 발생한다.")
    void failToReadContactsWhenCarDoesNotExist() {
        User user = createTestUser("01011112222", "pwd1234");
        Car car = createTestCar(user, "car", "car message");
        contactService.registerContact(car.getId(), "01011112222", "contactName");

        User anotherUser = createTestUser("01033334444", "pwd5678");
        Car anotherCar = createTestCar(anotherUser, "anotherCar", "anotherCar message");
        contactService.registerContact(anotherCar.getId(), "01033334444", "contactName");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.findContactsByUserOwnCar(user.getId(), anotherCar.getId()));
    }


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

    private User createTestUser(String phoneNumber, String password) {
        User user = new User(phoneNumber, password);
        return userRepository.save(user);
    }

    private Car createTestCar(User user, String carNickname, String carMessage) {
        Car car = new Car(user, carNickname, carMessage);
        return carRepository.save(car);
    }

    private User createTestUser() {
        return createTestUser("01012123434", "test1");
    }

    private Car createTestCar(User user) {
        return createTestCar(user, "carNickname", "carMessage");
    }

}
