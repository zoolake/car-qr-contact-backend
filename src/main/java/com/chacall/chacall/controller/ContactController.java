package com.chacall.chacall.controller;

import com.chacall.chacall.auth.SessionUser;
import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.dto.request.ContactUpdateRequest;
import com.chacall.chacall.dto.response.ContactUpdateResponse;
import com.chacall.chacall.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @DeleteMapping("/{contactId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@AuthenticationPrincipal SessionUser sessionUser, @PathVariable Long contactId, @RequestParam Long carId) {
        contactService.deleteContact(sessionUser.getUserId(), carId, contactId);
    }
}
