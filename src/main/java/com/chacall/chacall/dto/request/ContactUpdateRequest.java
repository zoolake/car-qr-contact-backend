package com.chacall.chacall.dto.request;

import com.chacall.chacall.domain.ContactStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ContactUpdateRequest {
    private final String phoneNumber;
    private final String name;
    private final ContactStatus status;
}
