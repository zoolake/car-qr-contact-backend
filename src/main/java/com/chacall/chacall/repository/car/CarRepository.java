package com.chacall.chacall.repository.car;

import com.chacall.chacall.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    List<Car> findCarsByUserId(Long userId);

    Optional<Car> findById(Long carId);

    Car save(Car car);

    Optional<Car> findCarByUserIdAndNickname(Long userId, String nickname);
}
