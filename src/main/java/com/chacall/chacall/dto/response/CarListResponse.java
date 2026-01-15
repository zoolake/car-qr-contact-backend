package com.chacall.chacall.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CarListResponse {

    private final String phoneNumber;
    private final List<CarResponse> cars;

    public static CarListResponse of(String phoneNumber, List<CarResponse> cars) {
        return new CarListResponse(phoneNumber, cars);
    }
}
