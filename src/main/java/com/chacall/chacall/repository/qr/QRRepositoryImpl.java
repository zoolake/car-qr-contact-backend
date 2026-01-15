package com.chacall.chacall.repository.qr;

import com.chacall.chacall.domain.QR;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QRRepositoryImpl implements QRRepository {

    private final QRJpaRepository qrJpaRepository;

    @Override
    public QR save(QR qr) {
        return qrJpaRepository.save(qr);
    }

    @Override
    public Optional<QR> findBySerialNo(String serialNo) {
        return qrJpaRepository.findBySerialNo(serialNo);
    }

    @Override
    public Optional<QR> findById(Long qrId) {
        return qrJpaRepository.findById(qrId);
    }

    @Override
    public void deleteQR(QR qr) {
        qrJpaRepository.delete(qr);
    }
}
