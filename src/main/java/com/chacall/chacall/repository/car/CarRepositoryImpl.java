package com.chacall.chacall.repository.car;

import com.chacall.chacall.domain.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarRepositoryImpl implements CarRepository {

    private final CarJpaRepository carJpaRepository;

    @Override
    public Car save(Car car) {
        return carJpaRepository.save(car);
    }

    @Override
    public Optional<Car> findCarByUserIdAndNickname(Long userId, String nickname) {
        return carJpaRepository.findCarByUserIdAndNickname(userId, nickname);
    }

    @Override
    public Optional<Car> findById(Long carId) {
        return carJpaRepository.findById(carId);
    }

    @Override
    public List<Car> findCarsByUserId(Long userId) {
        return carJpaRepository.findCarsByUserId(userId);
    }

    @Override
    public void deleteCar(Car car) {
        carJpaRepository.delete(car);
    }
}
