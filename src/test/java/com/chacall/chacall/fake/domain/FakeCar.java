package com.chacall.chacall.fake.domain;

import com.chacall.chacall.domain.Car;

public class FakeCar extends Car {
    public FakeCar(Long carId, Car car) {
        super(carId, car.getUser(), car.getNickname(), car.getMessage());
    }
}
