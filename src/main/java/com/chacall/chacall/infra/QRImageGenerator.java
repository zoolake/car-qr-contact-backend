package com.chacall.chacall.infra;

public interface QRImageGenerator {
    String generateQRImage(String targetBaseUrl, String serialNo);
}
