package com.chacall.chacall.domain;

public class FakeQR extends QR {
    public FakeQR(Long qrId, QR qr) {
        super(qrId, qr.getCar(), qr.getSerialNo(), qr.getImagePath());
    }
}
