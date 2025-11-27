package com.chacall.chacall.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarUpdateRequest {
    @NotBlank
    @Size(min = 3, max = 12)
    @Pattern(regexp = "^[A-Za-z0-9가-힣]+$")
    private final String nickname;

    @Size(max = 30)
    private final String message;
}
