package com.chacall.chacall.controller;

import com.chacall.chacall.dto.response.ContactResponse;
import com.chacall.chacall.service.QRService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/qrs")
public class QRController {

    private final QRService qrService;

    @GetMapping("/{serialNo}")
    public ResponseEntity<List<ContactResponse>> readContactsByScanningQR(@PathVariable String serialNo) {
        List<ContactResponse> response = qrService.findContactsByQRSerialNo(serialNo).stream()
                .map(ContactResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }
}
