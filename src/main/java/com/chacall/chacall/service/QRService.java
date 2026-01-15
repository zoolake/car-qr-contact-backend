package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.QR;
import com.chacall.chacall.infra.QRImageGenerator;
import com.chacall.chacall.repository.qr.QRRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QRService {
    @Value("${qr.target-base-url}")
    private String qrTargetBaseUrl;

    private final QRRepository qrRepository;

    private final QRImageGenerator qrImageGenerator;

    /* 시리얼 번호를 통한 QR 조회 */
    public QR findQR(String serialNo) {
        return qrRepository.findBySerialNo(serialNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 QR 코드 입니다."));
    }

    /* QR 생성 */
    public Long createQR(Car car) {
        String serialNo = UUID.randomUUID().toString().replace("-", "");

        String imagePath = qrImageGenerator.generateQRImage(qrTargetBaseUrl, serialNo);

        return qrRepository.save(new QR(car, serialNo, imagePath)).getId();
    }

}
