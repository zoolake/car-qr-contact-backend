package com.chacall.chacall.infra;

import com.chacall.chacall.domain.QR;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileSystemQRImageGenerator implements QRImageGenerator {
    @Value("${qr.storage-dir}")
    private String qrStorageDirectory;

    @Override
    public String generateQRImage(String targetBaseUrl, String serialNo) {
        try {
            Path imageStorageDirectory = Path.of(qrStorageDirectory);
            Files.createDirectories(imageStorageDirectory);

            BitMatrix matrix = new MultiFormatWriter()
                    .encode(targetBaseUrl + serialNo, BarcodeFormat.QR_CODE, QR.WIDTH, QR.HEIGHT);

            String fileName = String.format("%s.%s", serialNo, QR.IMAGE_FORMAT);
            Path imagePath = Path.of(String.valueOf(imageStorageDirectory), fileName);
            MatrixToImageWriter.writeToPath(matrix, QR.IMAGE_FORMAT, imagePath);

            return String.valueOf(imagePath);
        } catch (IOException e) {
            throw new UncheckedIOException("QR 코드 이미지 저장 실패", e);
        } catch (WriterException e) {
            throw new IllegalStateException("QR 코드 인코딩 실패", e);
        }

    }

}
