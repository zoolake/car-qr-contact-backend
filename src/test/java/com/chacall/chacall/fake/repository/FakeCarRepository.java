package com.chacall.chacall.fake.repository;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.fake.domain.FakeCar;
import com.chacall.chacall.repository.car.CarRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeCarRepository implements CarRepository {

    private final AtomicLong idGenerator;
    private final Map<Long, Car> database;

    public FakeCarRepository() {
        this.idGenerator = new AtomicLong(0L);
        this.database = new ConcurrentHashMap<>();
    }

    @Override
    public Car save(Car car) {
        if (car.getId() != null) {
            return car;
        }

        long id = idGenerator.getAndIncrement();
        database.put(id, new FakeCar(id, car));

        return database.get(id);
    }

    @Override
    public Optional<Car> findCarByUserIdAndNickname(Long userId, String nickname) {
        return database.values().stream()
                .filter(car -> car.getNickname().equals(nickname) && car.getUser().getId().equals(userId))
                .findFirst();
    }

    @Override
    public void deleteCar(Car car) {
        database.remove(car.getId());
    }

    @Override
    public Optional<Car> findById(Long carId) {
        return Optional.ofNullable(database.get(carId));
    }

    @Override
    public List<Car> findCarsByUserId(Long userId) {
        return database.values().stream()
                .filter(car -> car.getUser().getId().equals(userId))
                .toList();
    }
}
