package com.chacall.chacall.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarUpdateRequest {
    private final String nickname;
    private final String message;
}
