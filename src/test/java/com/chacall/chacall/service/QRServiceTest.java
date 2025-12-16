package com.chacall.chacall.service;

import com.chacall.chacall.domain.*;
import com.chacall.chacall.fake.repository.FakeCarRepository;
import com.chacall.chacall.fake.repository.FakeContactRepository;
import com.chacall.chacall.fake.repository.FakeQRRepository;
import com.chacall.chacall.fake.repository.FakeUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QRServiceTest {

    private final FakeContactRepository contactRepository = new FakeContactRepository();
    private final FakeCarRepository carRepository = new FakeCarRepository();
    private final FakeQRRepository qrRepository = new FakeQRRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();

    private final ContactService contactService = new ContactService(contactRepository, carRepository);
    private final QRService qrService = new QRService(qrRepository);

    @Test
    @DisplayName("QR 코드를 생성한다.")
    void createQR() {
        User user = createTestUser();
        String nickname = "차량닉네임";
        String message = "차량메세지";
        Car car = new Car(user, nickname, message);

        Long qrId = qrService.createQR(car);

        QR qr = qrRepository.findById(qrId).get();
        assertThat(qr).isNotNull();
        assertThat(qr.getCar().getNickname()).isEqualTo(nickname);
        assertThat(qr.getCar().getMessage()).isEqualTo(message);
        assertThat(qr.getSerialNo()).isNotBlank();
    }

    private User createTestUser() {
        User user = new User("01012121212", "pwd1234!");
        return userRepository.save(user);
    }

    private Car createTestCar(User user) {
        CarService carService = new CarService(carRepository, userRepository, qrService, contactService);
        Long carId = carService.registerCar(user.getId(), "carNickname", "carMessage");
        return carService.findCar(carId);
    }
}
