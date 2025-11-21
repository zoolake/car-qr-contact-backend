package com.chacall.chacall.repository.qr;

import com.chacall.chacall.domain.QR;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QRRepositoryImpl implements QRRepository {

    private final QRJpaRepository qrJpaRepository;

    @Override
    public QR save(QR qr) {
        return qrJpaRepository.save(qr);
    }

    @Override
    public QR findBySerialNo(String serialNo) {
        return qrJpaRepository.findBySerialNo(serialNo);
    }
}
