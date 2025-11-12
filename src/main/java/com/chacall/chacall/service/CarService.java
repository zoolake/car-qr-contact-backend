package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.repository.CarRepository;
import com.chacall.chacall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    private final QRService qrService;
    private final ContactService contactService;

    @Transactional(readOnly = true)
    public Car findCar(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 차량 입니다."));
    }

    @Transactional(readOnly = true)
    public List<Car> findCarsByUserId(Long userId) {
        return carRepository.findCarsByUserId(userId);
    }

    @Transactional
    public Long registerCar(Long userId, String nickname, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다."));

        Car car = carRepository.save(new Car(user, nickname, message));

        // QR 등록
        qrService.createQR(car);

        // 차주 번호 등록
        contactService.registerContact(car.getId(), user.getPhoneNumber(), nickname);

        return car.getId();
    }

    @Transactional
    public Car updateCarInfo(Long carId, String nickname, String message) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 차량 입니다."));

        if (nickname != null) {
            car.changeNickname(nickname);
        }

        if (message != null) {
            car.changeMessage(message);
        }

        return car;
    }
}
