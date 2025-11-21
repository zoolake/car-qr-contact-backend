package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.FakeUser;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CarServiceTest {
    private final FakeUserRepository userRepository = new FakeUserRepository();
    private final FakeCarRepository carRepository = new FakeCarRepository();
    private final FakeContactRepository contactRepository = new FakeContactRepository();
    private final FakeQRRepository qrRepository = new FakeQRRepository();
    private final ContactService contactService = new ContactService(contactRepository, carRepository);
    private final QRService qrService = new QRService(qrRepository, contactService);
    private final CarService carService = new CarService(carRepository, userRepository, qrService, contactService);

    @Test
    @DisplayName("차량을 등록한다.")
    public void registerCar() {
        User user = userRepository.save(createTestUser());
        String nickname = "차량 닉네임";
        String message = "잠시 주차하겠습니다. 연락주세요.";

        Long carId = carService.registerCar(user.getId(), nickname, message);

        Car car = carRepository.findById(carId).get();
        assertThat(car).isNotNull();
        assertThat(car.getUser().getId()).isEqualTo(user.getId());
        assertThat(car.getNickname()).isEqualTo(nickname);
        assertThat(car.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("차량 정보를 수정한다.")
    public void updateCarInfo() {
        User user = userRepository.save(createTestUser());
        String nickname = "차량 닉네임";
        String message = "잠시 주차하겠습니다. 연락주세요.";

        Long carId = carService.registerCar(user.getId(), nickname, message);
        String newNickname = "[New] 차량 닉네임";
        String newMessage = "[New] 잠시 주차하겠습니다. 연락주세요.";
        Car car = carService.updateCarInfo(user.getId(), carId, newNickname, newMessage);

        assertThat(car.getId()).isEqualTo(carId);
        assertThat(car.getNickname()).isEqualTo(newNickname);
        assertThat(car.getMessage()).isEqualTo(newMessage);
    }

    @Test
    @DisplayName("차량 ID 를 통해 차량 정보를 조회한다.")
    public void getCarByUserId() {
        User user = userRepository.save(createTestUser());
        String nickname = "차량 닉네임";
        String message = "잠시 주차하겠습니다. 연락주세요.";

        Long carId = carService.registerCar(user.getId(), nickname, message);

        Car car = carService.findCar(carId);
        assertThat(car).isNotNull();
        assertThat(car.getId()).isEqualTo(carId);
        assertThat(car.getUser().getId()).isEqualTo(user.getId());
        assertThat(car.getNickname()).isEqualTo(nickname);
        assertThat(car.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("차량 등록 시, 현재 계정으로 등록된 차량과 닉네임이 중복되는 경우 등록에 실패한다.")
    public void failWhenNicknameIsDuplicated() {
        User user = userRepository.save(createTestUser());
        String nickname = "차량 닉네임";
        String message = "잠시 주차하겠습니다. 연락주세요.";
        carService.registerCar(user.getId(), nickname, message);

        String duplicatedNickname = nickname;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> carService.registerCar(user.getId(), duplicatedNickname, message));
    }

    @Test
    @DisplayName("사용자가 등록한 차량 목록들을 조회한다.")
    public void getCarsRegisteredByUser() {
        User user = userRepository.save(createTestUser());

        int carCount = 3;
        List<String> nicknames = List.of("차량 닉네임1", "차량 닉네임2", "차량 닉네임3");
        List<String> messages = List.of("메세지1", "메세지2", "메세지3");

        for (int i = 0; i < carCount; i++) {
            carService.registerCar(user.getId(), nicknames.get(i), messages.get(i));
        }

        List<Car> cars = carService.findCarsByUserId(user.getId());
        assertThat(cars.size()).isEqualTo(carCount);
        for (Car car : cars) {
            assertThat(nicknames.stream().anyMatch(nickname -> nickname.equals(car.getNickname()))).isTrue();
            assertThat(messages.stream().anyMatch(message -> message.equals(car.getMessage()))).isTrue();
        }
    }

    @Test
    @DisplayName("존재하지 않는 차량을 조회하는 경우 예외가 발생한다.")
    public void failToReadCarWhenCarDoesNotExist() {
        User user = userRepository.save(createTestUser());
        String nickname = "차량 닉네임";
        String message = "잠시 주차하겠습니다. 연락주세요.";
        carService.registerCar(user.getId(), nickname, message);

        Long invalidCarId = 27L;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> carService.findCar(invalidCarId));
    }

    @Test
    @DisplayName("존재하지 않는 차량을 수정하는 경우 예외가 발생한다.")
    public void failToUpdateCarWhenCarDoesNotExist() {
        User user = userRepository.save(createTestUser());
        String nickname = "차량 닉네임";
        String message = "잠시 주차하겠습니다. 연락주세요.";
        carService.registerCar(user.getId(), nickname, message);

        Long invalidCarId = 27L;
        String newNickname = "[New] 차량 닉네임";
        String newMessage = "[New] 잠시 주차하겠습니다. 연락주세요.";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> carService.updateCarInfo(user.getId(), invalidCarId, newNickname, newMessage));
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 차량을 등록하는 경우 예외가 발생한다.")
    public void failToRegisterCarWhenUserDoesNotExist() {
        Long invalidUserId = 13L;
        String nickname = "차량 닉네임";
        String message = "잠시 주차하겠습니다. 연락주세요.";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> carService.registerCar(invalidUserId, nickname, message));
    }

    @Test
    @DisplayName("사용자가 등록한 차량이 아닌 차량을 수정하는 경우 예외가 발생한다.")
    public void failToUpdateCarWhenCarIsNotOwnedByUser() {
        User user = userRepository.save(createTestUser());
        String nickname = "차량 닉네임";
        String message = "잠시 주차하겠습니다. 연락주세요.";
        Long carId = carService.registerCar(user.getId(), nickname, message);

        Long invalidUserId = 28L;
        String newNickname = "[New] 차량 닉네임";
        String newMessage = "[New] 잠시 주차하겠습니다. 연락주세요.";

        assertThatIllegalArgumentException()
                .isThrownBy(() -> carService.updateCarInfo(invalidUserId, carId, newNickname, newMessage));
    }


    private User createTestUser() {
        return new User("01012123434", "test1");
    }

}
