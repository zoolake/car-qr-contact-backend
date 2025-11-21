package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.CarJpaRepository;
import com.chacall.chacall.repository.UserJpaRepository;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@SpringBootTest
@Transactional
class QRServiceTest {

    @Autowired
    public QRService qrService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private CarJpaRepository carRepository;

    @Test
    public void QR_코드_생성() throws IOException, WriterException {
        User user = createTestUser();
        Car car = createTestCar(user);

        qrService.createQR(car);
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
