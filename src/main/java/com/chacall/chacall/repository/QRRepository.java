package com.chacall.chacall.repository;

import com.chacall.chacall.domain.QR;

public interface QRRepository {
    QR save(QR qr);

    QR findBySerialNo(String serialNo);
}
