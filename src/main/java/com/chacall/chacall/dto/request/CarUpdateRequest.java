package com.chacall.chacall.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarUpdateRequest {
    @Size(max = 12)
    private final String nickname;

    @Size(max = 30)
    private final String message;
}
