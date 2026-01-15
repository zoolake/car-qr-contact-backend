package com.chacall.chacall.dto.response;

import com.chacall.chacall.domain.Car;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CarUpdateResponse {
    private final String nickname;
    private final String message;

    public static CarUpdateResponse from(Car car) {
        return new CarUpdateResponse(car.getNickname(), car.getMessage());
    }
}
