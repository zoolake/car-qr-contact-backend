package com.chacall.chacall.domain;

public class FakeCar extends Car {
    public FakeCar(Long carId, Car car) {
        super(carId, car.getUser(), car.getNickname(), car.getMessage());
    }
}
