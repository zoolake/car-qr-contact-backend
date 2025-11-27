package com.chacall.chacall.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ContactRegisterRequest {
    @NotBlank(message = "연락처는 공백일 수 없습니다.")
    private final String phoneNumber;

    @NotBlank(message = "연락처명은 공백일 수 없습니다.")
    private final String name;
}
