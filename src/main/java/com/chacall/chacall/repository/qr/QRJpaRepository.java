package com.chacall.chacall.repository.qr;

import com.chacall.chacall.domain.QR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QRJpaRepository extends JpaRepository<QR, Long> {
    Optional<QR> findBySerialNo(String serialNo);
}
