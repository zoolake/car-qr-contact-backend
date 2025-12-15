package com.chacall.chacall.service;

import com.chacall.chacall.domain.*;
import com.chacall.chacall.fake.repository.FakeCarRepository;
import com.chacall.chacall.fake.repository.FakeContactRepository;
import com.chacall.chacall.fake.repository.FakeUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class ContactServiceTest {

    private final FakeContactRepository contactRepository = new FakeContactRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeCarRepository carRepository = new FakeCarRepository();
    private final ContactService contactService = new ContactService(contactRepository, carRepository);

    @Test
    @DisplayName("본인 소유의 차량 ID 를 통해 해당 차량에 등록된 연락처를 조회한다.")
    void getContactsByCarId() {
        User user = createTestUser("01011112222", "pwd1234!");
        Car car1 = createTestCar(user, "car1", "car1 message");
        contactService.registerSubContact(user.getId(), car1.getId(), "01011112222", "첫번째연락처");
        contactService.registerSubContact(user.getId(), car1.getId(), "01022223333", "두번째연락처");

        List<Contact> car1Contacts = contactService.findContactsByUserOwnCar(user.getId(), car1.getId());

        assertThat(car1Contacts.size()).isEqualTo(2);
        car1Contacts.forEach(contact -> assertThat(contact.getCar().getId()).isEqualTo(car1.getId()));
    }

    @Test
    @DisplayName("사용자가 보유하지 않은 차량의 연락처 목록을 조회하려는 경우 예외가 발생한다.")
    void failToReadContactsWhenCarDoesNotExist() {
        User user = createTestUser("01011112222", "pwd1234!");
        Car car = createTestCar(user, "car", "car message");
        contactService.registerSubContact(user.getId(), car.getId(), "01011112222", "contactName");

        User anotherUser = createTestUser("01033334444", "pwd1234!");
        Car anotherCar = createTestCar(anotherUser, "anotherCar", "anotherCar message");
        contactService.registerSubContact(anotherUser.getId(), anotherCar.getId(), "01033334444", "contactName");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.findContactsByUserOwnCar(user.getId(), anotherCar.getId()));
    }


    @Test
    @DisplayName("차량에 연락처를 등록한다.")
    void registerContact() {
        User user = createTestUser();
        Car car = createTestCar(user);

        String phoneNumber = "01034561234";
        String name = "홍길동";
        Long contactId = contactService.registerSubContact(user.getId(), car.getId(), phoneNumber, name);

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
        User user = createTestUser();

        Long invalidCarId = 13L;
        String phoneNumber = "01034561234";
        String name = "홍길동";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.registerSubContact(user.getId(), invalidCarId, phoneNumber, name));
    }

    @Test
    @DisplayName("사용자가 보유하지 않은 차량에 연락처를 등록하려는 경우 예외가 발생한다.")
    void failToRegisterContactWhenCarNotOwnedByUser() {
        User user = createTestUser();

        User anotherUser = createTestUser("01035983598", "pwd1234!");
        Car car = createTestCar(anotherUser);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.registerSubContact(user.getId(), car.getId(), "01011112222", "contactName"));
    }

    @Test
    @DisplayName("연락처 등록 시, 대상 차량에 이미 동일한 연락처가 등록되어 있다면 예외가 발생한다.")
    void failToRegisterContactWhenContactIsDuplicated() {
        User user = createTestUser();
        Car car = createTestCar(user);

        String phoneNumber = "01034561234";
        String name = "홍길동";
        contactService.registerSubContact(user.getId(), car.getId(), phoneNumber, name);

        String duplicatedPhoneNumber = phoneNumber;
        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.registerSubContact(user.getId(), car.getId(), duplicatedPhoneNumber, name));
    }

    @Test
    @DisplayName("등록되지 않은 연락처를 수정 시 예외가 발생한다.")
    void failToUpdateContactWhenContactDoesNotExist() {
        User user = createTestUser();
        Car car = createTestCar(user);

        String phoneNumber = "01034561234";
        String name = "홍길동";
        contactService.registerSubContact(user.getId(), car.getId(), phoneNumber, name);

        Long invalidContactId = 13L;
        String newPhoneNumber = "01012345678";
        String newName = "임꺽정";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.updateContactInfo(invalidContactId, newPhoneNumber, newName, ContactStatus.AVAILABLE));
    }

    @Test
    @DisplayName("연락처 정보를 수정한다.")
    void updateContact() {
        User user = createTestUser();
        Car car = createTestCar(user);

        String phoneNumber = "01034561234";
        String name = "홍길동";
        Long contactId = contactService.registerSubContact(user.getId(), car.getId(), phoneNumber, name);

        String newPhoneNumber = "01012345678";
        String newName = "임꺽정";
        ContactStatus newStatus = ContactStatus.AVAILABLE;
        Contact updatedContact = contactService.updateContactInfo(contactId, newPhoneNumber, newName, newStatus);

        assertThat(updatedContact.getId()).isEqualTo(contactId);
        assertThat(updatedContact.getPhoneNumber()).isEqualTo(newPhoneNumber);
        assertThat(updatedContact.getName()).isEqualTo(newName);
        assertThat(updatedContact.getStatus()).isEqualTo(newStatus);
    }

    @Test
    @DisplayName("연락처를 삭제한다.")
    void deleteContact() {
        User user = createTestUser("01012345678", "pwd1234!");
        Car car = createTestCar(user, "carNickname", "carMessage");

        String phoneNumber = "01002240224";
        String name = "contactName";
        Long contactId = contactService.registerSubContact(user.getId(), car.getId(), phoneNumber, name);

        contactService.deleteContact(user.getId(), car.getId(), contactId);

        Optional<Contact> deletedContact = contactRepository.findById(contactId);
        assertThat(deletedContact.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("없는 연락처를 삭제하려는 경우 예외가 발생한다.")
    void failToDeleteContactWhenContactNotExist() {
        User user = createTestUser("01012345678", "pwd1234!");
        Car car = createTestCar(user, "carNickname", "carMessage");

        String phoneNumber = "01002240224";
        String name = "contactName";
        contactService.registerSubContact(user.getId(), car.getId(), phoneNumber, name);

        Long invalidContactId = 23L;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.deleteContact(user.getId(), car.getId(), invalidContactId));
    }

    @Test
    @DisplayName("삭제하는 연락처가 현재 차량에 등록된 연락처가 아닌 경우 예외가 발생한다.")
    void failToDeleteContactWhenContactNotRegisteredToCar() {
        User user = createTestUser("01012345678", "pwd1234!");
        Car car = createTestCar(user, "carNickname1", "carMessage1");

        String phoneNumber = "01002240224";
        String name = "contactName";
        Long contactId = contactService.registerSubContact(user.getId(), car.getId(), phoneNumber, name);

        Car anotherCar = createTestCar(user, "carNickname2", "carMessage2");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.deleteContact(user.getId(), anotherCar.getId(), contactId));
    }

    @Test
    @DisplayName("삭제하는 연락처의 차량이 현재 사용자의 차량이 아닌 경우 예외가 발생한다.")
    void failToDeleteContactWhenCarNotOwnedByUser() {
        User user = createTestUser("01012345678", "pwd1234!");
        Car car = createTestCar(user, "carNickname1", "carMessage1");

        String phoneNumber = "01002240224";
        String name = "contactName";
        Long contactId = contactService.registerSubContact(user.getId(), car.getId(), phoneNumber, name);

        User anotherUser = createTestUser("01023456789", "pwd1234!");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> contactService.deleteContact(anotherUser.getId(), car.getId(), contactId));
    }

    @Test
    @DisplayName("메인 연락처의 경우 연락처 수정이 불가능하다.")
    void failToUpdateWhenContactTypeIsMain() {
        String phoneNumber = "01012345678";
        User user = createTestUser(phoneNumber, "pwd1234!");
        Car car = createTestCar(user, "carNickname", "carMessage");

        Long mainContactId = contactService.registerMainContact(user.getId(), car.getId(), phoneNumber, "contactName");

        String newPhoneNumber = "01034567890";
        assertThatIllegalStateException()
                .isThrownBy(() -> contactService.updateContactInfo(mainContactId, newPhoneNumber, "newName", ContactStatus.AVAILABLE));
    }

    @Test
    @DisplayName("메인 연락처의 경우 삭제가 불가능하다.")
    void failToDeleteWhenContactTypeIsMain() {
        String phoneNumber = "01012345678";
        User user = createTestUser(phoneNumber, "pwd1234!");
        Car car = createTestCar(user, "carNickname", "carMessage");

        Long mainContactId = contactService.registerMainContact(user.getId(), car.getId(), phoneNumber, "contactName");

        assertThatIllegalStateException()
                .isThrownBy(() -> contactService.deleteContact(user.getId(), car.getId(), mainContactId));
    }

    @Test
    @DisplayName("이미 메인 연락처가 있는데 또 메인 연락처를 등록하는 경우 예외가 발생한다.")
    void failToRegisterMainContactWhenMainContactIsAlreadyExist() {
        String userPhoneNumber = "01012345678";
        User user = createTestUser(userPhoneNumber, "pwd1234!");
        Car car = createTestCar(user, "carNickname", "carMessage");
        contactService.registerMainContact(user.getId(), car.getId(), userPhoneNumber, "firstMain");

        assertThatIllegalStateException()
                .isThrownBy(() -> contactService.registerMainContact(user.getId(), car.getId(), "01002240224", "secondMain"));
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
        return createTestUser("01012123434", "pwd1234!");
    }

    private Car createTestCar(User user) {
        return createTestCar(user, "carNickname", "carMessage");
    }

}
