package com.chacall.chacall.dto.response;

import com.chacall.chacall.domain.Car;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarInfoResponse {
    private final Long carId;
    private final String nickname;
    private final String message;

    public static CarInfoResponse from(Car car) {
        return new CarInfoResponse(car.getId(), car.getNickname(), car.getMessage());
    }
}
