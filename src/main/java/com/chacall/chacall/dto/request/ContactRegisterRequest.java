package com.chacall.chacall.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ContactRegisterRequest {
    private final String phoneNumber;
    private final String name;
}
