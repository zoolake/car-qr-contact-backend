package com.chacall.chacall.controller;

import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.dto.response.ContactResponse;
import com.chacall.chacall.dto.response.QRContactsResponse;
import com.chacall.chacall.facade.QRScanFacade;
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

    private final QRScanFacade qrScanFacade;

    @GetMapping("/{serialNo}")
    public ResponseEntity<QRContactsResponse> readContactsByScanningQR(@PathVariable String serialNo) {
        return ResponseEntity.ok(qrScanFacade.findContactsBySerialNo(serialNo));
    }
}
