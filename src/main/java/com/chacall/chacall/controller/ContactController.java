package com.chacall.chacall.controller;

import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.dto.request.ContactUpdateRequest;
import com.chacall.chacall.dto.response.ContactUpdateResponse;
import com.chacall.chacall.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/contacts")
public class ContactController {
    private final ContactService contactService;

    @PatchMapping("/{contactId}")
    public ResponseEntity<ContactUpdateResponse> updateContact(@PathVariable Long contactId, @RequestBody @Valid ContactUpdateRequest request) {
        Contact contact = contactService.updateContactInfo(contactId, request.getPhoneNumber(), request.getName(), request.getStatus());
        return ResponseEntity.ok(ContactUpdateResponse.from(contact));
    }
}
