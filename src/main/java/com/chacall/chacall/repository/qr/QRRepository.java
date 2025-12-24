package com.chacall.chacall.repository.qr;

import com.chacall.chacall.domain.QR;

import java.util.Optional;

public interface QRRepository {
    QR save(QR qr);

    Optional<QR> findBySerialNo(String serialNo);

    Optional<QR> findById(Long qrId);

    void deleteQR(QR qr);
}
