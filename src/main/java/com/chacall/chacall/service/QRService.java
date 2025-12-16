package com.chacall.chacall.service;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.domain.QR;
import com.chacall.chacall.repository.qr.QRRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QRService {
    public static final String QR_TARGET_BASE_URL = "www.google.com/search?q=";
    public static final String QR_IMAGE_SAVE_PATH = "images/";
    public static final String QR_IMAGE_FORMAT = "png";

    private final QRRepository qrRepository;

    /* 시리얼 번호를 통한 QR 조회 */
    public QR findQR(String serialNo) {
        return qrRepository.findBySerialNo(serialNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 QR 코드 입니다."));
    }

    /* QR 생성 */
    public Long createQR(Car car) {
        try {
            String serialNo = UUID.randomUUID().toString().replace("-", "");
            String imagePath = generateQRImage(serialNo);

            return qrRepository.save(new QR(car, serialNo, imagePath)).getId();
        } catch (IOException e) {
            throw new UncheckedIOException("QR 코드 이미지 생성 실패", e);
        } catch (WriterException e) {
            throw new IllegalStateException("QR 코드 생성중 인코딩 실패", e);
        }
    }

    /* QR 이미지 생성 */
    private String generateQRImage(String serialNo) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(QR_TARGET_BASE_URL + serialNo, BarcodeFormat.QR_CODE, QR.WIDTH, QR.HEIGHT);

        Path qrImagePath = Path.of(QR_IMAGE_SAVE_PATH, serialNo + "." + QR_IMAGE_FORMAT);
        MatrixToImageWriter.writeToPath(matrix, QR_IMAGE_FORMAT, qrImagePath);

        return qrImagePath.toString();
    }
}
