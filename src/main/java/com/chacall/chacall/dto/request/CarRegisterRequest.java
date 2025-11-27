package com.chacall.chacall.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarRegisterRequest {
    @NotBlank(message = "차량 닉네임은 공백일 수 없습니다.")
    private final String nickname;

    @NotNull
    private final String message;

}
