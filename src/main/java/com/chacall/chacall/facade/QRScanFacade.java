package com.chacall.chacall.facade;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.domain.QR;
import com.chacall.chacall.dto.response.QRContactsResponse;
import com.chacall.chacall.service.ContactService;
import com.chacall.chacall.service.QRService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QRScanFacade {
    private final QRService qrService;
    private final ContactService contactService;

    @Transactional
    public QRContactsResponse findAvailableContactsBySerialNo(String serialNo) {
        QR qr = qrService.findQR(serialNo);
        Car car = qr.getCar();
        List<Contact> contacts = contactService.findContactsByCarId(car.getId())
                .stream()
                .filter(Contact::isAvailable)
                .toList();


        return QRContactsResponse.from(car, contacts);
    }
}
