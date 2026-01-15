package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.Password;
import com.chacall.chacall.domain.QR;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.fake.repository.FakeQRRepository;
import com.chacall.chacall.fake.repository.FakeUserRepository;
import com.chacall.chacall.fake.service.FakePasswordEncoder;
import com.chacall.chacall.fake.service.FakeQRImageGenerator;
import com.chacall.chacall.infra.QRImageGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QRServiceTest {

    private final FakeQRRepository qrRepository = new FakeQRRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();

    private final QRImageGenerator qrImageGenerator = new FakeQRImageGenerator();
    private final QRService qrService = new QRService(qrRepository, qrImageGenerator);

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
        User user = new User("01012121212", Password.fromRaw("pwd1234!", new FakePasswordEncoder()));
        return userRepository.save(user);
    }

}
