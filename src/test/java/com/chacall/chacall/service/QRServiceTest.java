package com.chacall.chacall.service;

import com.chacall.chacall.domain.*;
import com.chacall.chacall.repository.FakeCarRepository;
import com.chacall.chacall.repository.FakeContactRepository;
import com.chacall.chacall.repository.FakeQRRepository;
import com.chacall.chacall.repository.FakeUserRepository;
import com.chacall.chacall.repository.car.CarJpaRepository;
import com.chacall.chacall.repository.user.UserJpaRepository;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QRServiceTest {

    private final FakeContactRepository contactRepository = new FakeContactRepository();
    private final FakeCarRepository carRepository = new FakeCarRepository();
    private final FakeQRRepository qrRepository = new FakeQRRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();

    private final ContactService contactService = new ContactService(contactRepository, carRepository);
    private final QRService qrService = new QRService(qrRepository, contactService);


    // 연락처 상태가 unavailable 일 때, 사용자 계정이 아닌 다른 곳에서는 못보게끔 하는 테스트도 필요할듯.

    @Test
    @DisplayName("QR 코드의 시리얼 번호를 통해 차량에 등록된 연락 가능한 연락처 목록을 조회한다.")
    void getAvailableContactsByQRSerialNo() {
        User user = createTestUser();
        Car car = createTestCar(user);

        int count = 5;
        for (int i = 0; i < count; i++) {
            String phoneNumber = "0101234567" + i;
            String name = "testName" + i;
            contactService.registerContact(car.getId(), phoneNumber, name);
        }

        String serialNo = car.getQr().getSerialNo();
        List<Contact> contacts = qrService.findContactsByQRSerialNo(serialNo);

        assertThat(contacts.size()).isEqualTo(count + 1);
        for (Contact contact : contacts) {
            assertThat(contact.getStatus()).isEqualTo(ContactStatus.AVAILABLE);
        }
    }

    // 이 테스트가 QR 테스트에 있어야할까? => 다르게 생각하면 ContactService에 있어야할지 CarService 에 있어야 할지도 고민된다.
    @Test
    @DisplayName("QR 코드를 생성한다.")
    void createQR() {
        User user = createTestUser();
        String nickname = "차량 닉네임";
        String message = "차량 메세지";
        Car car = new Car(user, nickname, message);

        Long qrId = qrService.createQR(car);

        QR qr = qrRepository.findById(qrId).get();
        assertThat(qr).isNotNull();
        assertThat(qr.getCar().getNickname()).isEqualTo(nickname);
        assertThat(qr.getCar().getMessage()).isEqualTo(message);
        assertThat(qr.getSerialNo()).isNotBlank();
    }

    private User createTestUser() {
        User user = new User("01012121212", "test1");
        return userRepository.save(user);
    }

    private Car createTestCar(User user) {
        CarService carService = new CarService(carRepository, userRepository, qrService, contactService);
        Long carId = carService.registerCar(user.getId(), "carNickname", "carMessage");
        return carService.findCar(carId);
    }
}
