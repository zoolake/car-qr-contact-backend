package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.CarRepository;
import com.chacall.chacall.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CarServiceTest {

    @Autowired
    private CarService carService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void 차량_정보를_수정한다() {
        User user = createTestUser();
        Car car = createTestCar(user);

        String newNickname = "newNickname";
        String newMessage = "newMessage";
        carService.updateCarInfo(car.getId(), newNickname, newMessage);

        assertThat(newNickname).isEqualTo(car.getNickname());
        assertThat(newMessage).isEqualTo(car.getMessage());
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
