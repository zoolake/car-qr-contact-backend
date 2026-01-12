package com.chacall.chacall.fake.service;

import com.chacall.chacall.infra.QRImageGenerator;

public class FakeQRImageGenerator implements QRImageGenerator {
    @Override
    public String generateQRImage(String targetBaseUrl, String serialNo) {
        return targetBaseUrl + "/" + serialNo;
    }
}
