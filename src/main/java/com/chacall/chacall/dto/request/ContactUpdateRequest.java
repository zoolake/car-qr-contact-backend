package com.chacall.chacall.dto.request;

import com.chacall.chacall.domain.ContactStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ContactUpdateRequest {
    @NotBlank
    @Pattern(regexp = "^01[016789]\\d{7,8}$")
    private final String phoneNumber;

    @NotBlank
    @Size(min = 3, max = 12)
    private final String name;

    @NotNull
    private final ContactStatus status;
}
