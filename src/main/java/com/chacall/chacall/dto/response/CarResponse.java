package com.chacall.chacall.dto.response;

import com.chacall.chacall.domain.Car;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CarResponse {
    private final Long carId;
    private final String nickname;
    private final String message;
    private final String imageUrl;

    public static CarResponse from(Car car) {
        String imagePath = car.getQr().getImagePath();
        return new CarResponse(car.getId(), car.getNickname(), car.getMessage(), imagePath);
    }
}
