package com.chacall.chacall.fake.domain;

import com.chacall.chacall.domain.QR;

public class FakeQR extends QR {
    public FakeQR(Long qrId, QR qr) {
        super(qrId, qr.getCar(), qr.getSerialNo(), qr.getImagePath());
    }
}
