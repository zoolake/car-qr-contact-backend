package com.chacall.chacall.repository;

import com.chacall.chacall.domain.QR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRJpaRepository extends JpaRepository<QR, Long> {
    QR findBySerialNo(String serialNo);
}
