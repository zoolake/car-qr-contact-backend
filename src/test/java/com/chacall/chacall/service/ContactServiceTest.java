package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.CarJpaRepository;
import com.chacall.chacall.repository.ContactJpaRepository;
import com.chacall.chacall.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactJpaRepository contactRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private CarJpaRepository carRepository;

    @Test
    public void 연락처_정상_등록() {
        User user = createTestUser();
        Car car = createTestCar(user);

        String phoneNumber = "01012345678";
        String name = "myName";
        Long contactId = contactService.registerContact(car.getId(), phoneNumber, name);

        Contact contact = contactRepository.findById(contactId).get();

        assertThat(phoneNumber).isEqualTo(contact.getPhoneNumber());
        assertThat(name).isEqualTo(contact.getName());
    }

    @Test
    public void 입력받은_연락처에_숫자가_아닌_문자가_있는_경우_예외_발생() {
    }

    private User createTestUser() {
        User user = new User("01012123434", "test1");
        userJpaRepository.save(user);

        return user;
    }

    private Car createTestCar(User user) {
        Car car = new Car(user, "carNickName", "carMessage");
        carRepository.save(car);

        return car;
    }

}
