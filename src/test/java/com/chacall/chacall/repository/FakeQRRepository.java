package com.chacall.chacall.repository;

import com.chacall.chacall.domain.FakeQR;
import com.chacall.chacall.domain.QR;
import com.chacall.chacall.repository.qr.QRRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeQRRepository implements QRRepository {

    private final AtomicLong idGenerator;
    private final Map<Long, QR> database;

    public FakeQRRepository() {
        this.idGenerator = new AtomicLong(0L);
        this.database = new ConcurrentHashMap<>();
    }

    @Override
    public QR save(QR qr) {
        if (qr.getId() != null) {
            return qr;
        }

        long id = idGenerator.getAndIncrement();
        database.put(id, new FakeQR(id, qr));

        return database.get(id);
    }

    @Override
    public QR findBySerialNo(String serialNo) {
        return database.values().stream()
                .filter(qr -> qr.getSerialNo().equals(serialNo))
                .findFirst()
                .get();
    }

    @Override
    public Optional<QR> findById(Long qrId) {
        return Optional.ofNullable(database.get(qrId));
    }
}
